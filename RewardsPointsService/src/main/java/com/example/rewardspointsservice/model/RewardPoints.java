package com.example.rewardspointsservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "reward_points")
@Data
public class RewardPoints {
    @Id
    private String id;
    private int employeeId;
    private int points;
}
