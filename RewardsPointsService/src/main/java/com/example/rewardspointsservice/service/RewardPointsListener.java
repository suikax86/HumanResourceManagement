package com.example.rewardspointsservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RewardPointsListener {
    private final RewardPointsService rewardPointsService;

    public RewardPointsListener(RewardPointsService rewardPointsService) {
        this.rewardPointsService = rewardPointsService;
    }

    @RabbitListener( queues = "employeeCreatedQueue")
    public void handleEmployeeCreated(Long employeeId) {
        log.info("Employee created event received: {}", employeeId);
        rewardPointsService.createRewardPointsProfile(employeeId);
    }

    @RabbitListener(queues = "employeeDeletedQueue")
    public void handleEmployeeDeleted(Long employeeId) {
        log.info("Employee deleted event received: {}", employeeId);
        rewardPointsService.deleteRewardPointsProfile(employeeId);
    }


}
