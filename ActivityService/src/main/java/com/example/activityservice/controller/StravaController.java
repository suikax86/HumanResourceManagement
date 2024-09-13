package com.example.activityservice.controller;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/activities/strava")
public class StravaController {

    private final Dotenv dotenv;
    private final Map<String, String> tokenStorage = new HashMap<>();

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
        Map<String, Object> tokenData = response.getBody();

        String userId = "user_" + System.currentTimeMillis(); // Generate a unique user ID
        tokenStorage.put(userId, (String) tokenData.get("access_token"));

        // Return the user ID to the client
        return ResponseEntity.ok(Map.of("userId", userId));
    }

    @GetMapping("/activities")
    public ResponseEntity<?> getActivities(@RequestParam String userId) {
        String accessToken = tokenStorage.get(userId);
        if (accessToken == null) {
            return ResponseEntity.badRequest().body("Invalid user ID");
        }

        String url = "https://www.strava.com/api/v3/clubs/1257078/activities?page=1&per_page=30";
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<Object[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);

        return ResponseEntity.ok(response.getBody());
    }
}

