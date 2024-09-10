package com.example.rewardspointsservice.model.dtos;

import com.example.rewardspointsservice.model.RewardPointsTransaction;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class RewardPointsProfileDto {
    private String id;
    private Long employeeId;
    private Double totalPoints;
    private List<RewardPointsTransaction> pointsHistory = new ArrayList<>();
}
