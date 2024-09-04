package com.example.rewardspointsservice.model.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class CreateVoucherRequest {
    private String brand;
    private String name;
    private String description;
    private Double points;
    private int quantity;
    private LocalDate DateStart;
    private LocalDate DateEnd;

    public CreateVoucherRequest() {
    }

    public CreateVoucherRequest(String brand, String name, String description, Double points, int quantity, LocalDate dateStart, LocalDate dateEnd) {
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.points = points;
        this.quantity = quantity;
        DateStart = dateStart;
        DateEnd = dateEnd;
    }
}
