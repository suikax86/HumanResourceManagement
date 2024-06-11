package da.hms.employeeservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String idNumber;

    @Column(nullable = false)
    private String taxNumber;

    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String bankNumber;

    public Employee() {
    }

    public Employee(String name, String email, String idNumber, String taxNumber, String address, String phoneNumber, String bankNumber) {
        this.name = name;
        this.email = email;
        this.idNumber = idNumber;
        this.taxNumber = taxNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.bankNumber = bankNumber;
    }
}
