package da.hms.employeeservice.repository;

import da.hms.employeeservice.model.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
    List<Timesheet> findByEmployeeId(Long employeeId);
    Optional<Timesheet> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
}
