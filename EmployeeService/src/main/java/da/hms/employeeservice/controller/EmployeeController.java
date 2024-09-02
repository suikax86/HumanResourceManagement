package da.hms.employeeservice.controller;

import da.hms.employeeservice.model.dto.EmployeeDto;
import da.hms.employeeservice.model.dto.UpdateEmployeeDto;
import da.hms.employeeservice.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public List<EmployeeDto> getEmployees() {
        return employeeService.getEmployeesDto();
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployee(@PathVariable Long id) {
        return employeeService.getEmployeeDto(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeDto employeeDto) {
        String response = employeeService.updateEmployee(id, employeeDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
