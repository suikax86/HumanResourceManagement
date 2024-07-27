package da.hms.employeeservice.service;

import da.hms.employeeservice.model.client.RewardPointsProfile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class RewardPointsServiceClient {

    private final RestTemplate restTemplate;

    public RewardPointsServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int getRewardPoints(int employeeId) {
        String url = "http://localhost:8082/api/reward-points-profile/" + employeeId;
        RewardPointsProfile rewardPointsProfile = restTemplate.getForObject(url, RewardPointsProfile.class);
        return rewardPointsProfile != null ? rewardPointsProfile.getTotalPoints() : 0;
    }

    public void createRewardPointsProfile(int employeeId) {
        String url = "http://localhost:8082/api/reward-points-profile/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Integer> request = new HttpEntity<>(employeeId, headers);
        restTemplate.postForEntity(url, request, String.class);
    }




}
