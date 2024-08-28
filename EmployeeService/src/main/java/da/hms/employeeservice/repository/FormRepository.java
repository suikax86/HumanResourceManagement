package da.hms.employeeservice.repository;


import da.hms.employeeservice.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form, Long> {
    }
