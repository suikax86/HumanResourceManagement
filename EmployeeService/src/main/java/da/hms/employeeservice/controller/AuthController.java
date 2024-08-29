package da.hms.employeeservice.controller;

import da.hms.employeeservice.client.EmailInfo;
import da.hms.employeeservice.model.Account;
import da.hms.employeeservice.model.Employee;
import da.hms.employeeservice.model.Role;
import da.hms.employeeservice.model.dto.AccountDto;
import da.hms.employeeservice.model.dto.LoginDto;
import da.hms.employeeservice.model.dto.RegisterDto;
import da.hms.employeeservice.model.enums.AccountStatus;
import da.hms.employeeservice.repository.AccountRepository;
import da.hms.employeeservice.repository.EmployeeRepository;
import da.hms.employeeservice.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final EmployeeRepository employeeRepository;

    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final RabbitTemplate rabbitTemplate;


    public AuthController(EmployeeRepository employeeRepository, AccountRepository accountRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, RabbitTemplate rabbitTemplate) {
        this.employeeRepository = employeeRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {

        // Check if the email is already in use
        if (accountRepository.existsByUsername(registerDto.getEmail())) {
            return new ResponseEntity<>("Email already in use", HttpStatus.BAD_REQUEST);
        }

        // Check if the employee already exists by idNumber
        if (employeeRepository.existsByIdNumber(registerDto.getIdNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee with the same idNumber already exists");
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
        rabbitTemplate.convertAndSend("employeeExchange", "employee.created", savedEmployee.getId());

        return new ResponseEntity<>("Registration successful", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDto loginDto) {
        Account account = accountRepository.findByUsername(loginDto.getEmail());

        if (account == null || !passwordEncoder.matches(loginDto.getPassword(), account.getPassword())) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid email or password");
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }

        if (account.getStatus().equals(AccountStatus.INACTIVE)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Account deactivated");
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("accountId", account.getId());
        response.put("employeeId", account.getEmployee().getId());
        response.put("role", account.getRole().getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getRole/{accountId}")
    public ResponseEntity<String> getRole(@PathVariable Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        return new ResponseEntity<>(account.getRole().getName(), HttpStatus.OK);
    }

    @GetMapping("/")
    public List<AccountDto> getAccounts() {
        //Get all account dto
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

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deactivateAccount(@PathVariable Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (account.getStatus().equals(AccountStatus.INACTIVE)) {
            return new ResponseEntity<>("Account already deactivated", HttpStatus.OK);
        }
        account.setStatus(AccountStatus.INACTIVE);
        accountRepository.save(account);
        return new ResponseEntity<>("Account deactivated", HttpStatus.OK);
    }

    @PutMapping("/reactive/{accountId}")
    public ResponseEntity<String> reactiveAccount(@PathVariable Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        //check if account is already active
        if (account.getStatus().equals(AccountStatus.ACTIVE)) {
            return new ResponseEntity<>("Account already active", HttpStatus.OK);
        }
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
        return new ResponseEntity<>("Account activated", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<String> deleteHardAccount(@PathVariable Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        // delete account and employee profile of the account

        Long employeeId = account.getEmployee().getId();
        accountRepository.delete(account);
        employeeRepository.deleteById(employeeId);
        rabbitTemplate.convertAndSend("employeeExchange", "employee.deleted", employeeId);

        return new ResponseEntity<>("Account deleted", HttpStatus.OK);
    }

}
