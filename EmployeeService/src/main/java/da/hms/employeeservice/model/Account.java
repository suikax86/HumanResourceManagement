package da.hms.employeeservice.model;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Account() {
    }

    public Account(String username, String password, Employee employee, Role role) {
        this.username = username;
        this.password = password;
        this.employee = employee;
        this.role = role;
    }
}
