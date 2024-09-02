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
import da.hms.employeeservice.service.AuthService;
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

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    @Transactional
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDto loginDto) {
        Map<String, Object> response = authService.login(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getRole/{accountId}")
    public ResponseEntity<String> getRole(@PathVariable Long accountId) {
        String role = authService.getRole(accountId);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping("/")
    public List<AccountDto> getAccounts() {
        return authService.getAccounts();
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deactivateAccount(@PathVariable Long accountId) {
        String response = authService.deactivateAccount(accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/reactive/{accountId}")
    public ResponseEntity<String> reactiveAccount(@PathVariable Long accountId) {
        String response = authService.reactiveAccount(accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<String> deleteHardAccount(@PathVariable Long accountId) {
        String response = authService.deleteHardAccount(accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
