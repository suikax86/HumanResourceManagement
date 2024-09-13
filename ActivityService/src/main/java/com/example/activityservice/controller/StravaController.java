package com.example.activityservice.controller;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/activities/strava")
public class StravaController {

    private final Dotenv dotenv;

    public StravaController(Dotenv dotenv) {
        this.dotenv = dotenv;
    }


    @PostMapping("/exchange_token")
    public ResponseEntity<?> exchangeToken(@RequestBody Map<String, String> request) {
        String code = request.get("code");

        String url = "https://www.strava.com/oauth/token";

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        String clientId = dotenv.get("STRAVA_CLIENT_ID");
        String clientSecret = dotenv.get("STRAVA_CLIENT_SECRET");
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("code", code);
        params.put("grant_type", "authorization_code");

        ResponseEntity<Map> response = restTemplate.postForEntity(url, params, Map.class);

        return ResponseEntity.ok(response.getBody());
    }
}

