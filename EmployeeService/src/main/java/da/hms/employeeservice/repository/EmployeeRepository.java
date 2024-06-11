package da.hms.employeeservice.repository;

import da.hms.employeeservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    boolean existsByIdNumber(String idNumber);
}
