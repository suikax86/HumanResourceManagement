package com.example.rewardspointsservice.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransferPointsRequest {
    private Long fromId;
    private Long toId;
    private Double amount;
    private String message;
}
