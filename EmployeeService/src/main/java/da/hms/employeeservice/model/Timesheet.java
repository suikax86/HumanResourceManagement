package da.hms.employeeservice.model;

import da.hms.employeeservice.model.enums.TimesheetStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
public class Timesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private OffsetDateTime checkInTime;

    private OffsetDateTime checkOutTime;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private TimesheetStatus status;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public Timesheet() {
    }

    public Timesheet(OffsetDateTime checkInTime, LocalDate date, TimesheetStatus status, Employee employee) {
        this.checkInTime = checkInTime;
        this.date = date;
        this.status = status;
        this.employee = employee;
    }
}
