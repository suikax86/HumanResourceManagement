package com.example.rewardspointsservice.model.dtos;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VoucherDTO {
    private Long id;
    private String brand;
    private String name;
    private String description;
    private Double points;
    private int quantity;
    private LocalDate DateStart;
    private LocalDate DateEnd;
    private String Code;

    public VoucherDTO() {
    }

    public VoucherDTO(Long id, String brand, String name, String description, Double points, int quantity, LocalDate dateStart, LocalDate dateEnd) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.points = points;
        this.quantity = quantity;
        DateStart = dateStart;
        DateEnd = dateEnd;
    }
}