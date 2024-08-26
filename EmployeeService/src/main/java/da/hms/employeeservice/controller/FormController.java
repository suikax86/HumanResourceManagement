package da.hms.employeeservice.controller;

import da.hms.employeeservice.model.dto.Form;
import da.hms.employeeservice.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("api/forms")
public class FormController {
    @Autowired
    private FormRepository formRepository;
    
    // Get all forms
    @GetMapping
    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    // Get a form by ID
    @GetMapping("/{id}")
    public Form getFormById(@PathVariable Long id) {
        return formRepository.findById(id).orElse(null);
    }

    // Create a new form
    @PostMapping
    public Form createForm(@RequestBody Form form) {
        return formRepository.save(form);
    }

    // Update a form
    @PutMapping("/{id}")
    public Form updateForm(@PathVariable Long id, @RequestBody Form formDetails) {
        Form form = formRepository.findById(id).orElse(null);
        if (form != null) {
            form.setEmployeeId(formDetails.getEmployeeId());
            form.setName(formDetails.getName());
            form.setPhone(formDetails.getPhone());
            form.setDayRest(formDetails.getDayRest());
            form.setDateBackToWork(formDetails.getDateBackToWork());
            form.setType(formDetails.getType());
            form.setReason(formDetails.getReason());
            return formRepository.save(form);
        }
        return null;
    }

    // Delete a form
    @DeleteMapping("/{id}")
    public void deleteForm(@PathVariable Long id) {
        formRepository.deleteById(id);
    }
}
