package com.example.rewardspointsservice.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RedeemedVoucher {
    private String campaignName;
    private String campaignDescription;
    private LocalDateTime endAt;
    private String code;
    private LocalDateTime redeemedAt;

    public RedeemedVoucher(String campaignName, String campaignDescription, LocalDateTime endAt, String code) {
        this.campaignName = campaignName;
        this.campaignDescription = campaignDescription;
        this.endAt = endAt;
        this.code = code;
        this.redeemedAt = LocalDateTime.now();
    }
}
