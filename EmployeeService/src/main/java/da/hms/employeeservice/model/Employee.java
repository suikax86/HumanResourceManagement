package da.hms.employeeservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    private String bankName;

    @Column(nullable = false)
    private String bankNumber;

    @Transient
    private double rewardPoints; // Transient field to hold reward points

    @OneToOne(mappedBy = "employee")
    private Account account;

    public Employee() {
        this.rewardPoints = 0;
    }

    public Employee(String name, String email, String idNumber, String taxNumber, String address, String phoneNumber, String bankName, String bankNumber) {
        this.name = name;
        this.email = email;
        this.idNumber = idNumber;
        this.taxNumber = taxNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.bankName = bankName;
        this.bankNumber = bankNumber;
        this.rewardPoints = 0;
    }
}
