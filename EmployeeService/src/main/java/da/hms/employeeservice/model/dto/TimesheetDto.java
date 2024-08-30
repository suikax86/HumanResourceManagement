package da.hms.employeeservice.model.dto;

import da.hms.employeeservice.model.enums.TimesheetStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
public class TimesheetDto {
    private OffsetDateTime checkInTime;
    private OffsetDateTime checkOutTime;
    private LocalDate date;
    private TimesheetStatus status;
    private Long employeeId;

    public TimesheetDto() {
    }

    public TimesheetDto(OffsetDateTime checkInTime, OffsetDateTime checkOutTime, LocalDate date, TimesheetStatus status, Long employeeId) {
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.date = date;
        this.status = status;
        this.employeeId = employeeId;
    }
}
