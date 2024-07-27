package com.example.rewardspointsservice.controller;

import com.example.rewardspointsservice.model.RewardPointsProfile;
import com.example.rewardspointsservice.repository.RewardPointsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/reward-points-profile")
public class RewardPointsProfileController {
    private final RewardPointsRepository rewardPointsRepository;

    public RewardPointsProfileController(RewardPointsRepository rewardPointsRepository) {
        this.rewardPointsRepository = rewardPointsRepository;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getRewardPoints(@PathVariable int employeeId) {
        try {
            RewardPointsProfile profile = rewardPointsRepository.findByEmployeeId(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{employeeId}")
    public RewardPointsProfile updateRewardPoints(@PathVariable int employeeId, @RequestBody RewardPointsProfile rewardPoints) {
        Optional<RewardPointsProfile> existingRewardPoints = rewardPointsRepository.findByEmployeeId(employeeId);
        if (existingRewardPoints.isPresent()) {
            RewardPointsProfile points = existingRewardPoints.get();
            points.setTotalPoints(rewardPoints.getTotalPoints());
            return rewardPointsRepository.save(points);
        } else {
            throw new RuntimeException("Reward points not found for employeeId: " + employeeId);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRewardPointsProfile(@RequestBody int employeeId) {
        try {
            RewardPointsProfile profile = new RewardPointsProfile();
            profile.setEmployeeId(employeeId);
            profile.setTotalPoints(0);
            rewardPointsRepository.save(profile);
            return ResponseEntity.status(HttpStatus.CREATED).body("Profile created successfully for employee ID: " + employeeId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create profile: " + e.getMessage());
        }
    }
}
