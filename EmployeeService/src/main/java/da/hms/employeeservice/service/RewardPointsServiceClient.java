package da.hms.employeeservice.service;

import da.hms.employeeservice.model.client.RewardPoints;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RewardPointsServiceClient {

    private final RestTemplate restTemplate;

    public RewardPointsServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int getRewardPoints(int employeeId) {
        String url = "http://localhost:8082/api/reward-points/" + employeeId;
        RewardPoints rewardPoints = restTemplate.getForObject(url, RewardPoints.class);
        return rewardPoints != null ? rewardPoints.getPoints() : 0;
    }




}
