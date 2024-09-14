package com.example.rewardspointsservice.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePointsRequest {
    private Long employeeId;
    private Double points;
    private String description;
}
