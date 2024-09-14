package com.example.rewardspointsservice.controller;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/vouchery")
public class VoucheryController {

    private final Dotenv dotenv;

    public VoucheryController(Dotenv dotenv) {
        this.dotenv = dotenv;
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
}
