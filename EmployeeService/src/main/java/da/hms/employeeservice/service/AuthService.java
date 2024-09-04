package da.hms.employeeservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import da.hms.employeeservice.model.Account;
import da.hms.employeeservice.model.Employee;
import da.hms.employeeservice.model.Role;
import da.hms.employeeservice.model.dto.*;
import da.hms.employeeservice.model.enums.AccountStatus;
import da.hms.employeeservice.repository.AccountRepository;
import da.hms.employeeservice.repository.EmployeeRepository;
import da.hms.employeeservice.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AuthService {
    private final EmployeeRepository employeeRepository;

    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final RabbitTemplate rabbitTemplate;

    public AuthService(EmployeeRepository employeeRepository, AccountRepository accountRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, RabbitTemplate rabbitTemplate) {
        this.employeeRepository = employeeRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public String register(RegisterDto registerDto) throws JsonProcessingException {

        // Check if the email is already in use
        if (accountRepository.existsByUsername(registerDto.getEmail())) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email already in use");
        }

        // Check if the employee already exists by idNumber
        if (employeeRepository.existsByIdNumber(registerDto.getIdNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Employee with the same idNumber already exists");
        }

        Employee employee = new Employee(
                registerDto.getName(),
                registerDto.getEmail(),
                registerDto.getIdNumber(),
                registerDto.getTaxNumber(),
                registerDto.getAddress(),
                registerDto.getPhoneNumber(),
                registerDto.getBankName(),
                registerDto.getBankNumber()
        );

        Employee savedEmployee = employeeRepository.save(employee);

        //Default role is employee
        String roleName = registerDto.getRole() != null ? registerDto.getRole() : "EMPLOYEE";
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));

        Account account = new Account(
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword()),
                employee,
                role
        );
        accountRepository.save(account);

        // Publish message to RabbitMQ exchange
        log.info("Sending employeeId to RabbitMQ: {}", savedEmployee.getId());
        String message = new ObjectMapper().writeValueAsString(new CreateRewardPointsRequest(savedEmployee.getId(), savedEmployee.getName()));
        rabbitTemplate.convertAndSend("employeeExchange", "employee.created", message);
        return "Registration successful";
    }

    public Map<String, Object> login(LoginDto loginDto) {

        Account account = accountRepository.findByUsername(loginDto.getEmail());

        if (account == null || !passwordEncoder.matches(loginDto.getPassword(), account.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password");
        }

        if (account.getStatus().equals(AccountStatus.INACTIVE)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Account deactivated");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("accountId", account.getId());
        response.put("employeeId", account.getEmployee().getId());
        response.put("role", account.getRole().getName());
        return response;
    }

    public String getRole(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found")
        );
        return account.getRole().getName();
    }

    public List<AccountDto> getAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDto> accountDtos = new ArrayList<>();
        for(Account account : accounts) {
            AccountDto accountDto = new AccountDto();
            accountDto.setId(account.getId());
            accountDto.setUsername(account.getUsername());
            accountDto.setRole(account.getRole().getName());
            accountDto.setEmployeeId(account.getEmployee().getId());
            accountDtos.add(accountDto);
        }
        return accountDtos;
    }

    public String deactivateAccount( Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (account.getStatus().equals(AccountStatus.INACTIVE)) {
            return "Account already deactivated";
        }
        account.setStatus(AccountStatus.INACTIVE);
        accountRepository.save(account);
        return "Account deactivated";
    }

    public String reactiveAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (account.getStatus().equals(AccountStatus.ACTIVE)) {
            return "Account already active";
        }
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
        return "Account activated";
    }

    public String deleteHardAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        Long employeeId = account.getEmployee().getId();
        accountRepository.delete(account);
        employeeRepository.deleteById(employeeId);

        log.info("Sending employeeId {} to RabbitMQ for deletion", employeeId);
        rabbitTemplate.convertAndSend("employeeExchange", "employee.deleted", employeeId);

        return "Account deleted";
    }

}
