package da.hms.employeeservice.repository;

import da.hms.employeeservice.model.UpdateTimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UpdateTimeSheetRepository extends JpaRepository<UpdateTimeSheet, Long> {
    // Corrected the method to use Long for employeeId
    List<UpdateTimeSheet> findByEmployeeId(Long employeeId);
    void deleteByEmployeeId(Long employeeId);
}
