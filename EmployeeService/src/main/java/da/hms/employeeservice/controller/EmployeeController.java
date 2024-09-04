package da.hms.employeeservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import da.hms.employeeservice.model.dto.EmployeeDto;
import da.hms.employeeservice.model.dto.TransferPointsRequest;
import da.hms.employeeservice.model.dto.UpdateEmployeeDto;
import da.hms.employeeservice.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;
    private final RabbitTemplate rabbitTemplate;

    public EmployeeController(EmployeeService employeeService, RabbitTemplate rabbitTemplate) {
        this.employeeService = employeeService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("")
    public List<EmployeeDto> getEmployees(@RequestParam(defaultValue = "true") boolean withPoints) {
        List<EmployeeDto> employeeDtos = employeeService.getEmployeesDto();

        if(withPoints) {
            for (EmployeeDto employeeDto : employeeDtos) {
                MessageProperties messageProperties = new MessageProperties();
                messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
                Message message = new Message(employeeDto.getId().toString().getBytes(), messageProperties);
                Double points = (Double) rabbitTemplate.convertSendAndReceive("employeeExchange", "employee.reward.request", message);
                log.info("Sending message to employee.reward.request queue message: {}", message);
                points = points != null ? points : -1;
                employeeDto.setRewardPoints(points);
            }
        }

        return employeeDtos;
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployee(@PathVariable Long id,@RequestParam(defaultValue = "true") boolean withPoints) {

        EmployeeDto employeeDto = employeeService.getEmployeeDto(id);

        if(withPoints) {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            Message message = new Message(employeeDto.getId().toString().getBytes(), messageProperties);
            Double points = (Double) rabbitTemplate.convertSendAndReceive("employeeExchange", "employee.reward.request", message);
            log.info("Sending message to employee.reward.request queue message: {}", message);
            points = points != null ? points : -1;
            employeeDto.setRewardPoints(points);
        }

        return employeeDto;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferPoints(@RequestBody TransferPointsRequest transferPointsRequest) throws JsonProcessingException {

        Long fromId = transferPointsRequest.getFromId();
        Long toId = transferPointsRequest.getToId();
        Double amount = transferPointsRequest.getAmount();
        String msg = transferPointsRequest.getMessage();


        EmployeeDto fromEmployee = employeeService.getEmployeeDto(fromId);

        if (!fromEmployee.getRole().equals("MANAGER")) {
            return new ResponseEntity<>("Only managers can transfer points", HttpStatus.BAD_REQUEST);
        }

        employeeService.getEmployeeDto(toId);

        // Send message to RewardsPointsService to transfer points
        String message = new ObjectMapper().writeValueAsString(new TransferPointsRequest(fromId, toId, amount, msg));

        Object response = rabbitTemplate.convertSendAndReceive("employeeExchange", "employee.points.transfer", message);

        log.info("Attempting to transfer {} points from employee {} to employee {}", amount, fromId, toId);
        log.info("Received response: {}", response);

        if (response == null) {
            return new ResponseEntity<>("Error transferring points", HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (response.toString().contains("Insufficient points")) {
            return new ResponseEntity<>("Insufficient points to complete the transfer", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Points transferred successfully", HttpStatus.OK);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeDto employeeDto) {
        String response = employeeService.updateEmployee(id, employeeDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
