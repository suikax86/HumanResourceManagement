package da.hms.employeeservice.controller;

import da.hms.employeeservice.model.Employee;
import da.hms.employeeservice.model.Form;
import da.hms.employeeservice.model.dto.AddFormDto;
import da.hms.employeeservice.model.dto.ApproveFormDto;
import da.hms.employeeservice.model.dto.FormDto;
import da.hms.employeeservice.model.enums.FormStatus;
import da.hms.employeeservice.repository.EmployeeRepository;
import da.hms.employeeservice.repository.FormRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/forms")
public class FormController {

    private final FormRepository formRepository;
    private final EmployeeRepository employeeRepository;

    public FormController(FormRepository formRepository, EmployeeRepository employeeRepository) {
        this.formRepository = formRepository;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/")
    public List<FormDto> getForms() {
        List<Form> forms = formRepository.findAll();
        List<FormDto> formDtos = new ArrayList<>();
        // check if current approver is null
        for (Form form : forms) {
            formDtos.add(new FormDto(form.getId(), form.getEmployee().getId(), form.getApprover() != null ? form.getApprover().getId() : null, form.getName(), form.getApproverName(), form.getPhone(), form.getStartDate(), form.getEndDate(), form.getFormStatus(), form.getFormType(), form.getReason(), form.getComment()));
        }
        return formDtos;
    }

    @GetMapping("/{id}")
    public FormDto getForm(@PathVariable Long id) {
        Form form = formRepository.findById(id).orElse(null);
        if (form == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found");
        }
        return new FormDto(form.getId(), form.getEmployee().getId(), form.getApprover() != null ? form.getApprover().getId() : null, form.getName(), form.getApproverName(), form.getPhone(), form.getStartDate(), form.getEndDate(), form.getFormStatus(), form.getFormType(), form.getReason(), form.getComment());
    }






    @PostMapping("/")
    public ResponseEntity<String> addForm(@Valid @RequestBody AddFormDto addFormDto) {
        Form form = new Form();

        Employee employee = employeeRepository.findById(addFormDto.getEmployeeId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        form.setEmployee(employee);
        form.setName(employee.getName());
        form.setPhone(employee.getPhoneNumber());
        form.setStartDate(addFormDto.getStartDate());
        form.setEndDate(addFormDto.getEndDate());
        form.setFormStatus(FormStatus.PENDING);
        form.setFormType(addFormDto.getFormType());
        form.setReason(addFormDto.getReason());

        formRepository.save(form);
        return new ResponseEntity<>("Form added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> approveForm(@PathVariable Long id, @RequestBody ApproveFormDto approveFormDto) {
        Form form = formRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));
        Employee approver = employeeRepository.findById(approveFormDto.getApproverId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Approver not found"));

        form.setApprover(approver);
        form.setApproverName(approver.getName());
        form.setFormStatus(approveFormDto.getFormStatus());
        form.setComment(approveFormDto.getComment());
        formRepository.save(form);

        return new ResponseEntity<>(String.format("Form %s successfully",approveFormDto.getFormStatus()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteForm(@PathVariable Long id, Long employeeId) {

        Form form = formRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));

        //check if the employee is the owner of the form
        if (form.getEmployee().getId() != employeeId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this form");
        }

        // only allow deletion of pending forms
        if (form.getFormStatus() != FormStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Form already approved or rejected so it cannot be deleted");
        }

        formRepository.deleteById(id);
        return new ResponseEntity<>("Form deleted successfully", HttpStatus.OK);
    }




}