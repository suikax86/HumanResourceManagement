package da.hms.employeeservice.controller;

import da.hms.employeeservice.client.EmailInfo;
import da.hms.employeeservice.model.Account;
import da.hms.employeeservice.model.Employee;
import da.hms.employeeservice.model.Role;
import da.hms.employeeservice.model.dto.LoginDto;
import da.hms.employeeservice.model.dto.RegisterDto;
import da.hms.employeeservice.repository.AccountRepository;
import da.hms.employeeservice.repository.EmployeeRepository;
import da.hms.employeeservice.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
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
        if(employeeRepository.existsByIdNumber(registerDto.getIdNumber())) {
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
        rabbitTemplate.convertAndSend("employeeExchange","employee.created",savedEmployee.getId());

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
        Map<String, Object> response = new HashMap<>();
        response.put("accountId", account.getId());
        response.put("employeeId", account.getEmployee().getId());
        response.put("role", account.getRole().getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/isManager/{accountId}")
    public ResponseEntity<Boolean> isManager(@PathVariable Long accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        boolean isManager = "MANAGER".equals(account.getRole().getName());
        return new ResponseEntity<>(isManager, HttpStatus.OK);
    }
}
