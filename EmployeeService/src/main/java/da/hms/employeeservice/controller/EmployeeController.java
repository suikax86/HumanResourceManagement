package da.hms.employeeservice.controller;

import da.hms.employeeservice.model.dto.EmployeeDto;
import da.hms.employeeservice.model.dto.UpdateEmployeeDto;
import da.hms.employeeservice.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final RabbitTemplate rabbitTemplate;

    public EmployeeController(EmployeeService employeeService, RabbitTemplate rabbitTemplate) {
        this.employeeService = employeeService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("")
    public List<EmployeeDto> getEmployees() {
        List<EmployeeDto> employeeDtos = employeeService.getEmployeesDto();

        for (EmployeeDto employeeDto : employeeDtos) {
            Double points = (Double) rabbitTemplate.convertSendAndReceive("employeeExchange", "employee.reward.request", employeeDto.getId());
            points = points != null ? points : -1;
            employeeDto.setRewardPoints(points);
        }

        return employeeDtos;
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployee(@PathVariable Long id) {

        EmployeeDto employeeDto = employeeService.getEmployeeDto(id);

        Double points = (Double) rabbitTemplate.convertSendAndReceive("employeeExchange", "employee.reward.request", id);
        points = points != null ? points : -1;

        employeeDto.setRewardPoints(points);
        return employeeDto;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeDto employeeDto) {
        String response = employeeService.updateEmployee(id, employeeDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
