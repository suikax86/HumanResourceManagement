package com.example.rewardspointsservice.controller;

import com.example.rewardspointsservice.model.RedeemedVoucher;
import com.example.rewardspointsservice.model.dtos.RedeemVoucherRequest;
import com.example.rewardspointsservice.service.RewardPointsService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/vouchery")
public class VoucheryController {

    private final Dotenv dotenv;
    private final RewardPointsService rewardPointsService;

    public VoucheryController(Dotenv dotenv, RewardPointsService rewardPointsService) {
        this.dotenv = dotenv;
        this.rewardPointsService = rewardPointsService;
    }

    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<?> getCampaign(@PathVariable String campaignId) {
        String token = dotenv.get("VOUCHERY_API_KEY");

        if(token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid API Key");
        }

        String url = "https://udpt09.sandbox.vouchery.app/api/v2.1/campaigns/" + campaignId;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/redeem")
    public ResponseEntity<?> redeemVoucher(@RequestBody RedeemVoucherRequest request) {
        String token = dotenv.get("VOUCHERY_API_KEY");

        if(token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid API Key");
        }

        String url = "https://udpt09.sandbox.vouchery.app/api/v2.1/campaigns/" + request.getVoucherId() + "/vouchers/distribute?amount=1";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null && !response.getBody().isEmpty()) {
            Map<String, Object> voucherResponse = (Map<String, Object>) response.getBody().get(0);
            Map<String, Object> campaign = (Map<String, Object>) voucherResponse.get("campaign");

            // Save redeemed voucher to MongoDB
            RedeemedVoucher redeemedVoucher = new RedeemedVoucher(
                    campaign.get("name").toString(),
                    campaign.get("description").toString(),
                    Instant.parse(campaign.get("end_at").toString()).atZone(ZoneId.systemDefault()).toLocalDateTime(),
                    voucherResponse.get("code").toString()
            );

            double points = 20;
            String msg = "Vouchery Redeem: " + redeemedVoucher.getCampaignName();
            rewardPointsService.deductPoints(request.getEmployeeId(), points, msg);

            rewardPointsService.saveRedeemedVoucher(request.getEmployeeId(), redeemedVoucher);

            return ResponseEntity.ok(voucherResponse);
        }
        return ResponseEntity.badRequest().body("Failed to redeem voucher");
    }
}
