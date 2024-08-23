package da.hms.employeeservice;

import da.hms.employeeservice.model.Role;
import da.hms.employeeservice.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EmployeeServiceApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceApplication.class);
    private final RoleRepository roleRepository;

    public EmployeeServiceApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }

    @Override
    public void run(String ... args) {
        if(roleRepository.count() == 0) {
            Role role1 = new Role(1L,"EMPLOYEE");
            Role role2 = new Role(2L,"MANAGER");
            roleRepository.save(role1);
            roleRepository.save(role2);
            logger.info("Thêm dữ liệu mẫu vào bảng roles trong mysql thành công!");
        }
    }

}
