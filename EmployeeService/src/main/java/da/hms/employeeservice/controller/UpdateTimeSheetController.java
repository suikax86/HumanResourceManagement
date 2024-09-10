package da.hms.employeeservice.controller;

import da.hms.employeeservice.model.UpdateTimeSheet;


import da.hms.employeeservice.repository.UpdateTimeSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/timesheet")
public class UpdateTimeSheetController {

    @Autowired
    private UpdateTimeSheetRepository timeSheetRepository;

    // Save or update timesheet
    @PostMapping("/update")
    public String saveOrUpdateTimesheet(@RequestBody UpdateTimeSheet updateTimeSheet) {
        timeSheetRepository.save(updateTimeSheet);
        return "TimeSheet saved successfully!";
    }

    // Get a timesheet by employeeId

    @GetMapping("/get/{employeeId}")
    public List<UpdateTimeSheet> getTimesheetsByEmployeeId(@PathVariable Long employeeId) {
        return timeSheetRepository.findByEmployeeId(employeeId);
    }
    // Get all timesheets
    @GetMapping("/all")
    public Iterable<UpdateTimeSheet> getAllTimesheets() {
        return timeSheetRepository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTimesheetById(@PathVariable Long id) {
        Optional<UpdateTimeSheet> timesheet = timeSheetRepository.findById(id);
        if (timesheet.isPresent()) {
            timeSheetRepository.deleteById(id);
            return "Timesheet deleted successfully!";
        } else {
            return "Timesheet not found with the given id.";
        }
    }

}
