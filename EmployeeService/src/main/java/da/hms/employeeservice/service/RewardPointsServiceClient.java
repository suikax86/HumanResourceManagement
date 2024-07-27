package da.hms.employeeservice.service;

import da.hms.employeeservice.model.client.RewardPointsProfile;
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
        restTemplate.postForObject(url, employeeId, String.class);
    }




}
