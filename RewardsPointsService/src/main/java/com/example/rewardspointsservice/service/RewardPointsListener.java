package com.example.rewardspointsservice.service;

import com.example.rewardspointsservice.model.RewardPointsProfile;
import com.example.rewardspointsservice.repository.RewardPointsRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RewardPointsListener {
    private final RewardPointsRepository rewardPointsRepository;

    public RewardPointsListener(RewardPointsRepository rewardPointsRepository) {
        this.rewardPointsRepository = rewardPointsRepository;
    }

    @RabbitListener( queues = "employeeCreatedQueue")
    public void handleEmployeeCreated(Long employeeId) {
        RewardPointsProfile profile = new RewardPointsProfile();
        profile.setEmployeeId(employeeId);
        rewardPointsRepository.save(profile);
    }

}
