package da.hms.employeeservice.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import da.hms.employeeservice.client.EmailInfo;
import da.hms.employeeservice.model.Employee;
import da.hms.employeeservice.model.dto.ApproveFormDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.server.ResponseStatusException;

import da.hms.employeeservice.model.Form;
import da.hms.employeeservice.model.dto.AddFormDto;
import da.hms.employeeservice.model.dto.FormDto;
import da.hms.employeeservice.model.enums.FormStatus;
import da.hms.employeeservice.repository.FormRepository;

@Service
@Slf4j
public class FormService {

    private final FormRepository formRepository;
    private final EmployeeService employeeService;
    private final RabbitTemplate rabbitTemplate;

    public FormService(FormRepository formRepository, EmployeeService employeeService,
            RabbitTemplate rabbitTemplate) {
        this.formRepository = formRepository;
        this.employeeService = employeeService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<FormDto> getForms(FormStatus formStatus) {
        List<Form> forms;

        if (formStatus != null) {
            forms = formRepository.findByFormStatus(formStatus);
        } else {
            forms = formRepository.findAll();
        }
        List<FormDto> formDtos = new ArrayList<>();

        for (Form form : forms) {
            formDtos.add(new FormDto(form.getId(), form.getEmployee().getId(),
                    form.getApprover() != null ? form.getApprover().getId() : null, form.getName(),
                    form.getApproverName(), form.getPhone(), form.getStartDate(), form.getEndDate(),
                    form.getFormStatus(), form.getFormType(), form.getReason(), form.getComment()));
        }

        return formDtos;
    }

    public FormDto getForm(Long id) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));
        return new FormDto(form.getId(), form.getEmployee().getId(),
                form.getApprover() != null ? form.getApprover().getId() : null, form.getName(), form.getApproverName(),
                form.getPhone(), form.getStartDate(), form.getEndDate(), form.getFormStatus(), form.getFormType(),
                form.getReason(), form.getComment());
    }

    public List<FormDto> getFormsByEmployee(Long id) {
        List<Form> forms = formRepository.findByEmployeeId(id);
        List<FormDto> formDtos = new ArrayList<>();

        for (Form form : forms) {
            formDtos.add(new FormDto(form.getId(), form.getEmployee().getId(),
                    form.getApprover() != null ? form.getApprover().getId() : null, form.getName(),
                    form.getApproverName(), form.getPhone(), form.getStartDate(), form.getEndDate(),
                    form.getFormStatus(), form.getFormType(), form.getReason(), form.getComment()));
        }

        return formDtos;
    }

    public String addForm(AddFormDto addFormDto) {
        Form form = new Form();
        
        Employee employee = employeeService.getEmployee(addFormDto.getEmployeeId());
        
        form.setEmployee(employee);
        form.setName(employee.getName());
        form.setPhone(employee.getPhoneNumber());
        form.setStartDate(addFormDto.getStartDate());
        form.setEndDate(addFormDto.getEndDate());
        form.setFormStatus(FormStatus.PENDING);
        form.setFormType(addFormDto.getFormType());
        form.setReason(addFormDto.getReason());

        formRepository.save(form);

        return "Form added successfully";
    }

    private String loadEmailTemplate(String title, String message) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/emailTemplate.html");
        String template = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        return template.replace("{{title}}", title).replace("{{message}}", message);
    }

    public String approveForm(Long id, ApproveFormDto approveFormDto) throws IOException{
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));
        Employee approver = employeeService.getEmployee(approveFormDto.getApproverId());

        // Check if the form is already approved or rejected
        if (form.getFormStatus() != FormStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Form already approved or rejected");
        }

        form.setApprover(approver);
        form.setApproverName(approver.getName());
        form.setFormStatus(approveFormDto.getFormStatus());
        form.setComment(approveFormDto.getComment());
        formRepository.save(form);

        String title = "Your " + form.getFormType().toString().toLowerCase() + " form has been " + form.getFormStatus();
        String message = String.format("Your %s form with Id: %d has been %s with reason: %s",
                form.getFormType().toString().toLowerCase(), form.getId(),
                form.getFormStatus().toString().toLowerCase(), form.getComment());

        String emailContent = loadEmailTemplate(
                title,
                message);

        EmailInfo emailInfo = new EmailInfo(
                form.getEmployee().getEmail(),
                title,
                emailContent);

        log.info("Sending email to {}", form.getEmployee().getEmail());
        rabbitTemplate.convertAndSend("emailExchange", "email.sent", emailInfo);
        return String.format("Form %s successfully", approveFormDto.getFormStatus());
    }

    public String deleteForm(Long id, Long employeeId) {
        Form form = formRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));

         // check if the employee is the owner of the form
         if (!Objects.equals(form.getEmployee().getId(), employeeId)) {
         throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this form");
         }

         // only allow deletion of pending forms
         if (form.getFormStatus() != FormStatus.PENDING) {
         throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Form already approved or rejected so it cannot be deleted");
         }

         formRepository.deleteById(id);
         return "Form deleted successfully";
    }

}
