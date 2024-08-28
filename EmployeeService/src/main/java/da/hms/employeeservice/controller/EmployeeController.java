package da.hms.employeeservice.controller;

import da.hms.employeeservice.model.Employee;
import da.hms.employeeservice.model.dto.AddEmployeeDto;
import da.hms.employeeservice.model.dto.EmployeeDto;
import da.hms.employeeservice.model.dto.UpdateEmployeeDto;
import da.hms.employeeservice.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/")
    public List<EmployeeDto> getEmployees() {
        List<Employee> employees =  employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for(Employee employee : employees) {
            String role = "";
            double points = -1;
            try {
                if(employee.getAccount() != null && employee.getAccount().getRole() != null) {
                    role = employee.getAccount().getRole().getName();
                }
                employeeDtos.add(new EmployeeDto(employee.getId(),employee.getName(), employee.getEmail(),role, employee.getIdNumber(), employee.getTaxNumber(), employee.getAddress(), employee.getPhoneNumber(), employee.getBankName(), employee.getBankNumber(), points));
            }
            catch (Exception e) {
                System.err.println("Failed to fetch reward points: " + e.getMessage());

                employeeDtos.add(new EmployeeDto(employee.getId(),employee.getName(), employee.getEmail(),role, employee.getIdNumber(), employee.getTaxNumber(), employee.getAddress(), employee.getPhoneNumber(), employee.getBankName(), employee.getBankNumber(), points));
            }
        }
        return employeeDtos;
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployee(@PathVariable Long id) {
        Employee employee = this.employeeRepository.findById(id).orElse(null);
        EmployeeDto employeeDto;
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        } else {
            String role = "";
            double points = -1;
            try{
                if (employee.getAccount() != null && employee.getAccount().getRole() != null) {
                    role = employee.getAccount().getRole().getName();
                }
                employeeDto = new EmployeeDto(employee.getId(),employee.getName(), employee.getEmail(),role, employee.getIdNumber(), employee.getTaxNumber(), employee.getAddress(), employee.getPhoneNumber(), employee.getBankName(), employee.getBankNumber(), points);
            } catch (Exception e) {
                System.err.println("Failed to fetch reward points: " + e.getMessage());

                employeeDto = new EmployeeDto(employee.getId(),employee.getName(), employee.getEmail(),role, employee.getIdNumber(), employee.getTaxNumber(), employee.getAddress(), employee.getPhoneNumber(), employee.getBankName(), employee.getBankNumber(), points);
            }
            return employeeDto;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @Valid @RequestBody UpdateEmployeeDto employeeDto) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        // check if fields are null or empty and blank to avoid overwriting existing data
        if (employeeDto.getName() != null && !employeeDto.getName().isBlank()) {
            employee.setName(employeeDto.getName());
        }
        if (employeeDto.getEmail() != null && !employeeDto.getEmail().isBlank()) {
            employee.setEmail(employeeDto.getEmail());
        }
        if (employeeDto.getIdNumber() != null && !employeeDto.getIdNumber().isBlank()) {
            employee.setIdNumber(employeeDto.getIdNumber());
        }
        if (employeeDto.getTaxNumber() != null && !employeeDto.getTaxNumber().isBlank()) {
            employee.setTaxNumber(employeeDto.getTaxNumber());
        }
        if (employeeDto.getAddress() != null && !employeeDto.getAddress().isBlank()) {
            employee.setAddress(employeeDto.getAddress());
        }
        if (employeeDto.getPhoneNumber() != null && !employeeDto.getPhoneNumber().isBlank()) {
            employee.setPhoneNumber(employeeDto.getPhoneNumber());
        }
        if (employeeDto.getBankName() != null && !employeeDto.getBankName().isBlank()) {
            employee.setBankName(employeeDto.getBankName());
        }
        if (employeeDto.getBankNumber() != null && !employeeDto.getBankNumber().isBlank()) {
            employee.setBankNumber(employeeDto.getBankNumber());
        }

        this.employeeRepository.save(employee);
        return new ResponseEntity<>("Employee updated", HttpStatus.OK);
    }

}
