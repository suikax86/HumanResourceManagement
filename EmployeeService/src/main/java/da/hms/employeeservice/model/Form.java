package da.hms.employeeservice.model;

import da.hms.employeeservice.model.enums.FormStatus;
import da.hms.employeeservice.model.enums.FormType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class  Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String approverName;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @Column(nullable = false)
    private FormStatus formStatus;
    @Column(nullable = false)
    private FormType formType;
    private String reason;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private Employee approver;

    // Constructors
    public Form() {
    }

    public Form(String name, String approverName, String phone, LocalDate startDate, LocalDate endDate, FormStatus formStatus, FormType formType, String reason, Employee employee, Employee approver) {
        this.name = name;
        this.approverName = approverName;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.formStatus = formStatus;
        this.formType = formType;
        this.reason = reason;
        this.employee = employee;
        this.approver = approver;
    }
}

