package com.example.rewardspointsservice.model.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VoucherDTO {
    private String brand;
    private String name;
    private String description;
    private LocalDate DateStart;
    private LocalDate DateEnd;
    private String Code;
}
