package da.hms.employeeservice.controller;

import da.hms.employeeservice.model.UpdateTimeSheet;


import da.hms.employeeservice.repository.UpdateTimeSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    // Get a timesheet by ID
    @GetMapping("/get/{id}")
    public Optional<UpdateTimeSheet> getTimesheet(@PathVariable Long id) {
        return timeSheetRepository.findById(id);
    }

    // Get all timesheets
    @GetMapping("/all")
    public Iterable<UpdateTimeSheet> getAllTimesheets() {
        return timeSheetRepository.findAll();
    }
}
