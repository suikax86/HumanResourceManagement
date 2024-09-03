package com.example.rewardspointsservice.model;

import com.example.rewardspointsservice.model.enums.TransactionType;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class RewardPointsTransaction {
    @Id
    private String id;
    private Long senderId;
    private Long receiverId;
    private Double points;
    private TransactionType transactionType;
    private String description;
    private LocalDateTime timeStamp;

    public RewardPointsTransaction(Long senderId, Long receiverId, Double points, TransactionType transactionType, String description, LocalDateTime timeStamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.points = points;
        this.transactionType = transactionType;
        this.description = description;
        this.timeStamp = timeStamp;
    }
}
