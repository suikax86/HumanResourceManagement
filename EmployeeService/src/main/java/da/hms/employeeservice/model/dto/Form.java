package da.hms.employeeservice.model.dto;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;
    private String name;
    private String phone;
    private LocalDate dayRest;
    private LocalDate dateBackToWork;
    private String type;
    private String reason;

    // Constructors
    public Form() {
    }

    public Form(Long employeeId, String name, String phone, LocalDate dayRest, LocalDate dateBackToWork, String type, String reason) {
        this.employeeId = employeeId;
        this.name = name;
        this.phone = phone;
        this.dayRest = dayRest;
        this.dateBackToWork = dateBackToWork;
        this.type = type;
        this.reason = reason;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDayRest() {
        return dayRest;
    }

    public void setDayRest(LocalDate dayRest) {
        this.dayRest = dayRest;
    }

    public LocalDate getDateBackToWork() {
        return dateBackToWork;
    }

    public void setDateBackToWork(LocalDate dateBackToWork) {
        this.dateBackToWork = dateBackToWork;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

