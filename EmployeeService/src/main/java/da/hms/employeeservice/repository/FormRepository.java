package da.hms.employeeservice.repository;


import da.hms.employeeservice.model.dto.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface FormRepository extends JpaRepository<Form, Long> {
    }
