package com.example.rewardspointsservice.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RedeemVoucherRequest {
    private Long voucherId;
    private Long employeeId;
}
