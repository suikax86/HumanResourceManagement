package da.hms.employeeservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@Entity
public class UpdateTimeSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Added an ID field to be the primary key

    private Long employeeId;
    private String name;
    private LocalDate date;
    private LocalTime timeBegin;
    private LocalTime timeEnd;

    // Getters and Setters

}