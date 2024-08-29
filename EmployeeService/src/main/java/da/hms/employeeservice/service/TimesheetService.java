package da.hms.employeeservice.service;

import da.hms.employeeservice.model.Employee;
import da.hms.employeeservice.model.Timesheet;
import da.hms.employeeservice.model.dto.TimesheetDto;
import da.hms.employeeservice.model.enums.TimesheetStatus;
import da.hms.employeeservice.repository.EmployeeRepository;
import da.hms.employeeservice.repository.TimesheetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final EmployeeRepository employeeRepository;

    public TimesheetService(TimesheetRepository timesheetRepository, EmployeeRepository employeeRepository) {
        this.timesheetRepository = timesheetRepository;
        this.employeeRepository = employeeRepository;
    }

    public Timesheet checkIn(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Employee not found"));

        LocalDate today = LocalDate.now();
        if (timesheetRepository.findByEmployeeIdAndDate(employeeId, today).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Employee has already checked in today");
        }

        Timesheet timesheet = new Timesheet(OffsetDateTime.now(), today, TimesheetStatus.ON_TIME, employee);
        return timesheetRepository.save(timesheet);
    }

    public Timesheet checkOut(Long employeeId) {
        LocalDate today = LocalDate.now();
        Timesheet timesheet = timesheetRepository.findByEmployeeIdAndDate(employeeId, today).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Employee has not checked in today"));

        // check if employee has already checked out
        if (timesheet.getCheckOutTime() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Employee has already checked out today");
        }

        timesheet.setCheckOutTime(OffsetDateTime.now());
        return timesheetRepository.save(timesheet);
    }

    public List<TimesheetDto> getTimesheetHistory(Long employeeId) {

        employeeRepository.findById(employeeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee not found"));

        List<Timesheet> timesheets = timesheetRepository.findByEmployeeId(employeeId);
        List<TimesheetDto> timesheetDtos = new ArrayList<>();
        for(Timesheet timesheet : timesheets) {
            TimesheetDto timesheetDto = new TimesheetDto();
            timesheetDto.setCheckInTime(timesheet.getCheckInTime());
            timesheetDto.setCheckOutTime(timesheet.getCheckOutTime());
            timesheetDto.setDate(timesheet.getDate());
            timesheetDto.setStatus(timesheet.getStatus());
            timesheetDto.setEmployeeId(timesheet.getEmployee().getId());
            timesheetDtos.add(timesheetDto);
        }

        return timesheetDtos;
    }

    public TimesheetDto getTimesheetHistory(Long employeeId, LocalDate date) {

        employeeRepository.findById(employeeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee not found"));

        // check if date is in the future
        if (date.isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Date cannot be in the future");
        }

        Timesheet timesheet = timesheetRepository.findByEmployeeIdAndDate(employeeId, date).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Timesheet not found"));

        TimesheetDto timesheetDto = new TimesheetDto();
        timesheetDto.setCheckInTime(timesheet.getCheckInTime());
        timesheetDto.setCheckOutTime(timesheet.getCheckOutTime());
        timesheetDto.setDate(timesheet.getDate());
        timesheetDto.setStatus(timesheet.getStatus());
        timesheetDto.setEmployeeId(timesheet.getEmployee().getId());
        return timesheetDto;
    }

}
