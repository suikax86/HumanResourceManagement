package com.example.rewardspointsservice.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRewardPointsRequest {
    private Long employeeId;
    private String employeeName;
}
