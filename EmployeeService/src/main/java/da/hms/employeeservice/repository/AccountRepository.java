package da.hms.employeeservice.repository;

import da.hms.employeeservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByUsername(String username);
    Account findByUsername(String username);
}
