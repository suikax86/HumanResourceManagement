package com.example.rewardspointsservice.model;

import com.example.rewardspointsservice.model.enums.TransactionType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Document(collection = "rewardPointsProfile")
@Data
public class RewardPointsProfile {
    @Id
    private String id;
    private Long employeeId;
    private Double totalPoints;
    private List<RewardPointsTransaction> pointsHistory = new ArrayList<>();

    public RewardPointsProfile() {
        this.totalPoints = (double) 0;
    }

    public void addPoints(Long seederId,Double points, String description) {
        this.totalPoints += points;
        this.pointsHistory.add(new RewardPointsTransaction(seederId,employeeId, points, TransactionType.ADD, description, LocalDateTime.now()));
    }

    public void subtractPoints(Long seederId, Double points, String description) {
        this.totalPoints -= points;
        this.pointsHistory.add(new RewardPointsTransaction(seederId, employeeId, points, TransactionType.SUBTRACT, description, LocalDateTime.now()));
    }


}
