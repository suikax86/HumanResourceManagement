package com.example.rewardspointsservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class RewardPointsTransaction {
    @Id
    private String id;
    private int senderId;
    private int receiverId;
    private int points;
    private TransactionType transactionType;
    private String description;
    private LocalDateTime timeStamp;

    public RewardPointsTransaction(int senderId, int receiverId, int points, TransactionType transactionType, String description, LocalDateTime timeStamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.points = points;
        this.transactionType = transactionType;
        this.description = description;
        this.timeStamp = timeStamp;
    }
}
