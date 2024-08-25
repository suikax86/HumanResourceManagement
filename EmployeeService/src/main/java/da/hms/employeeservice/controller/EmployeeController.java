package da.hms.employeeservice.controller;

import da.hms.employeeservice.model.Employee;
import da.hms.employeeservice.model.dto.AddEmployeeDto;
import da.hms.employeeservice.model.dto.EmployeeDto;
import da.hms.employeeservice.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
            try {
                employeeDtos.add(new EmployeeDto(employee.getName(), employee.getEmail(), employee.getIdNumber(), employee.getTaxNumber(), employee.getAddress(), employee.getPhoneNumber(), employee.getBankName(), employee.getBankNumber(), 0));
            }
            catch (Exception e) {
                System.err.println("Failed to fetch reward points: " + e.getMessage());
                int points = -1;
                employeeDtos.add(new EmployeeDto(employee.getName(), employee.getEmail(), employee.getIdNumber(), employee.getTaxNumber(), employee.getAddress(), employee.getPhoneNumber(), employee.getBankName(), employee.getBankNumber(), points));
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
            try{
                employeeDto = new EmployeeDto(employee.getName(), employee.getEmail(), employee.getIdNumber(), employee.getTaxNumber(), employee.getAddress(), employee.getPhoneNumber(), employee.getBankName(), employee.getBankNumber(), 0);
            } catch (Exception e) {
                System.err.println("Failed to fetch reward points: " + e.getMessage());
                int points = -1;
                employeeDto = new EmployeeDto(employee.getName(), employee.getEmail(), employee.getIdNumber(), employee.getTaxNumber(), employee.getAddress(), employee.getPhoneNumber(), employee.getBankName(), employee.getBankNumber(), points);
            }
            return employeeDto;
        }
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody AddEmployeeDto employeeDto) {
        Employee employee = this.employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }

        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setIdNumber(employeeDto.getIdNumber());
        employee.setTaxNumber(employeeDto.getTaxNumber());
        employee.setAddress(employeeDto.getAddress());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setBankNumber(employeeDto.getBankNumber());

        return employeeRepository.save(employee);
    }

    @GetMapping("/checkActivated/{id}")
    public boolean checkActivated(@PathVariable Long id) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        return employee.getIsActivated();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteEmployee(@PathVariable Long id) {
        Employee employee = this.employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }

        employee.setIsActivated(false);
        employeeRepository.save(employee);

    }

}
