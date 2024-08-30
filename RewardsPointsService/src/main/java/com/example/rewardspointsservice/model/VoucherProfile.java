package com.example.rewardspointsservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "voucherProfile")
@Data
public class VoucherProfile {
    @Id
    private String id;
    private String brand;
    private String name;
    private String description;
    private LocalDate DateStart;
    private LocalDate DateEnd;
    private String Code;

    public VoucherProfile() {

    }

    public VoucherProfile(String id, String brand, String name, String description, LocalDate dateStart, LocalDate dateEnd, String code) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.description = description;
        DateStart = dateStart;
        DateEnd = dateEnd;
        Code = code;
    }
}
