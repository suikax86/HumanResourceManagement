package com.example.rewardspointsservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Random;

@Document(collection = "Vouchers")
@Getter
@Setter
public class Voucher {
    @Id
    private String id;
    private Long voucherId;
    private String brand;
    private String name;
    private String description;
    private Double points;
    private int quantity;
    private LocalDate DateStart;
    private LocalDate DateEnd;

    public Voucher() {
        this.voucherId = generateVoucherId();
    }

    public Voucher(String brand, String name, String description, Double points, int quantity, LocalDate dateStart, LocalDate dateEnd) {
        this.voucherId = generateVoucherId();
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.points = points;
        this.quantity = quantity;
        DateStart = dateStart;
        DateEnd = dateEnd;
    }

    private Long generateVoucherId() {
        Random random = new Random();
        return (long) (100000 + random.nextInt(900000)); // Generates a 6-digit random number
    }





}
