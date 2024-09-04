package da.hms.employeeservice.repository;


import da.hms.employeeservice.model.Form;
import da.hms.employeeservice.model.enums.FormStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {
    List<Form> findByEmployeeId(Long employeeId);
    List<Form> findByFormStatus(FormStatus formStatus);
}
