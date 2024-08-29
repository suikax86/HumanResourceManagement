package com.example.rewardspointsservice.service;

import com.example.rewardspointsservice.model.RewardPointsProfile;
import com.example.rewardspointsservice.repository.RewardPointsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RewardPointsListener {
    private final RewardPointsRepository rewardPointsRepository;

    public RewardPointsListener(RewardPointsRepository rewardPointsRepository) {
        this.rewardPointsRepository = rewardPointsRepository;
    }

    @RabbitListener( queues = "employeeCreatedQueue")
    public void handleEmployeeCreated(Long employeeId) {
        log.info("Employee created event received: {}", employeeId);
        RewardPointsProfile profile = new RewardPointsProfile();
        profile.setEmployeeId(employeeId);
        rewardPointsRepository.save(profile);
    }

    @RabbitListener(queues = "employeeDeletedQueue")
    public void handleEmployeeDeleted(Long employeeId) {
        log.info("Employee deleted event received: {}", employeeId);
        rewardPointsRepository.deleteByEmployeeId(employeeId);
    }


}
