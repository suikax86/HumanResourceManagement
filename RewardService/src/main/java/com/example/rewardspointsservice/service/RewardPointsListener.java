package com.example.rewardspointsservice.service;

import com.example.rewardspointsservice.model.dtos.CreateRewardPointsRequest;
import com.example.rewardspointsservice.model.dtos.TransferPointsRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void handleEmployeeCreated(String message) throws JsonProcessingException {
        log.info("Employee created event received: {}", message);
        CreateRewardPointsRequest request = new ObjectMapper().readValue(message, CreateRewardPointsRequest.class);
        rewardPointsService.createRewardPointsProfile(request.getEmployeeId(), request.getEmployeeName());
    }

    @RabbitListener(queues = "employeeDeletedQueue")
    public void handleEmployeeDeleted(Long employeeId) {
        log.info("Employee deleted event received: {}", employeeId);
        rewardPointsService.deleteRewardPointsProfile(employeeId);
    }

    @RabbitListener(queues = "employeeRewardRequestQueue")
    public double handleEmployeeRewardRequest(Long employeeId) {
        log.info("Employee reward request received: {}", employeeId);
        double points = rewardPointsService.getRewardPoints(employeeId);
        log.info("Reward points for employee {}: {}", employeeId, points);
        return points;
    }

    @RabbitListener(queues = "employeeTransferPointsQueue")
    public String handleEmployeeTransferPoints(String message) throws JsonProcessingException {
        log.info("Employee transfer points event received: {}", message);
        TransferPointsRequest request = new ObjectMapper().readValue(message, TransferPointsRequest.class);
        return rewardPointsService.transferPoints(request.getFromId(), request.getToId(), request.getAmount(), request.getMessage());

    }


}
