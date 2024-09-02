package da.hms.employeeservice.controller;

import da.hms.employeeservice.model.dto.AddFormDto;
import da.hms.employeeservice.model.dto.ApproveFormDto;
import da.hms.employeeservice.model.dto.FormDto;
import da.hms.employeeservice.service.FormService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/forms")
@Slf4j
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @GetMapping("")
    public List<FormDto> getForms() {
        return formService.getForms();
    }

    @GetMapping("/{id}")
    public FormDto getForm(@PathVariable Long id) {
        return formService.getForm(id);
    }

    @PostMapping("")
    public ResponseEntity<String> addForm(@Valid @RequestBody AddFormDto addFormDto) {
        String response = formService.addForm(addFormDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> approveForm(@PathVariable Long id, @RequestBody ApproveFormDto approveFormDto)
            throws IOException {
        String response = formService.approveForm(id, approveFormDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteForm(@PathVariable Long id, Long employeeId) {
        String response = formService.deleteForm(id, employeeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}