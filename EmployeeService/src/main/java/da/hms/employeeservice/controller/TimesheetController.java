package da.hms.employeeservice.controller;

import da.hms.employeeservice.model.Timesheet;
import da.hms.employeeservice.model.dto.TimesheetDto;
import da.hms.employeeservice.service.TimesheetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/timesheets")
public class TimesheetController {
    private final TimesheetService timesheetService;

    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @PostMapping("/check-in/{employeeId}")
    public ResponseEntity<String> checkIn(@PathVariable Long employeeId) {
        timesheetService.checkIn(employeeId);
        return new ResponseEntity<>("Checked in successfully", HttpStatus.OK);
    }

    @PostMapping("/check-out/{employeeId}")
    public ResponseEntity<String> checkOut(@PathVariable Long employeeId) {
        timesheetService.checkOut(employeeId);
        return new ResponseEntity<>("Checked in successfully", HttpStatus.OK);
    }

    @GetMapping("/history/{employeeId}")
    public ResponseEntity<List<TimesheetDto>> getTimesheetHistory(@PathVariable Long employeeId) {
        return ResponseEntity.ok(timesheetService.getTimesheetHistory(employeeId));
    }

    @GetMapping("/current/{employeeId}")
    public ResponseEntity<TimesheetDto> getCurrentTimesheet(@PathVariable Long employeeId) {
        return ResponseEntity.ok(timesheetService.getTimesheetHistory(employeeId, LocalDate.now()));
    }
}
