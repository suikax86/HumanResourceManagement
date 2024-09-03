package da.hms.employeeservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import da.hms.employeeservice.model.Employee;
import da.hms.employeeservice.model.dto.EmployeeDto;
import da.hms.employeeservice.model.dto.UpdateEmployeeDto;
import da.hms.employeeservice.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDto> getEmployeesDto() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : employees) {
            String role = "";
            double points = -1;
            if (employee.getAccount() != null && employee.getAccount().getRole() != null) {
                role = employee.getAccount().getRole().getName();
            }
            employeeDtos.add(new EmployeeDto(employee.getId(), employee.getName(), employee.getEmail(), role,
                    employee.getIdNumber(), employee.getTaxNumber(), employee.getAddress(), employee.getPhoneNumber(),
                    employee.getBankName(), employee.getBankNumber(), points));
        }
        return employeeDtos;
    }

    public EmployeeDto getEmployeeDto(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        String role = "";
        double points = -1;

        if (employee.getAccount() != null && employee.getAccount().getRole() != null) {
            role = employee.getAccount().getRole().getName();
        }

        return new EmployeeDto(employee.getId(), employee.getName(), employee.getEmail(), role,
                employee.getIdNumber(), employee.getTaxNumber(), employee.getAddress(), employee.getPhoneNumber(),
                employee.getBankName(), employee.getBankNumber(), points);
    }

    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    public String updateEmployee(Long id, UpdateEmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        // check if fields are null or empty and blank to avoid overwriting existing
        // data
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

        employeeRepository.save(employee);

        return "Employee updated";
    }

}
