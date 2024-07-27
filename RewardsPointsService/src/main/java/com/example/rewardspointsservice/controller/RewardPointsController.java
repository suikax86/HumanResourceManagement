package com.example.rewardspointsservice.controller;

import com.example.rewardspointsservice.model.RewardPoints;
import com.example.rewardspointsservice.repository.RewardPointsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/reward-points")
public class RewardPointsController {
    private final RewardPointsRepository rewardPointsRepository;

    public RewardPointsController(RewardPointsRepository rewardPointsRepository) {
        this.rewardPointsRepository = rewardPointsRepository;
    }

    @GetMapping("/{employeeId}")
    public RewardPoints getRewardPoints(@PathVariable int employeeId) {
        return rewardPointsRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @PostMapping("/")
    public RewardPoints addRewardPoints(@RequestBody RewardPoints rewardPoints) {
        return rewardPointsRepository.save(rewardPoints);
    }

    @PutMapping("/{employeeId}")
    public RewardPoints updateRewardPoints(@PathVariable int employeeId, @RequestBody RewardPoints rewardPoints) {
        Optional<RewardPoints> existingRewardPoints = rewardPointsRepository.findByEmployeeId(employeeId);
        if (existingRewardPoints.isPresent()) {
            RewardPoints points = existingRewardPoints.get();
            points.setPoints(rewardPoints.getPoints());
            return rewardPointsRepository.save(points);
        } else {
            throw new RuntimeException("Reward points not found for employeeId: " + employeeId);
        }
    }



}
