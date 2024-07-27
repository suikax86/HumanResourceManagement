package com.example.rewardspointsservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "rewardPointsProfile")
@Data
public class RewardPointsProfile {
    @Id
    private String id;
    private int employeeId;
    private int totalPoints;
    private List<RewardPointsTransaction> pointsHistory = new ArrayList<>();

    public RewardPointsProfile() {
        this.totalPoints = 0;
    }

    public void addPoints(int seederId,int points, String description) {
        this.totalPoints += points;
        this.pointsHistory.add(new RewardPointsTransaction(seederId,employeeId, points, TransactionType.ADD, description, LocalDateTime.now()));
    }

    public void subtractPoints(int seederId, int points, String description) {
        this.totalPoints -= points;
        this.pointsHistory.add(new RewardPointsTransaction(seederId, employeeId, points, TransactionType.SUBTRACT, description, LocalDateTime.now()));
    }
}
