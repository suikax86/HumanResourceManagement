package com.example.rewardspointsservice.controller;

import com.example.rewardspointsservice.model.RedeemedVoucher;
import com.example.rewardspointsservice.model.RewardPointsProfile;
import com.example.rewardspointsservice.model.dtos.RewardPointsProfileDto;
import com.example.rewardspointsservice.model.dtos.UpdatePointsRequest;
import com.example.rewardspointsservice.service.RewardPointsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardPointsProfileController {
    private final RewardPointsService rewardPointsService;

    public RewardPointsProfileController(RewardPointsService rewardPointsService) {
        this.rewardPointsService = rewardPointsService;
    }

    @GetMapping("/{employeeId}")
    public RewardPointsProfileDto getRewardPoints(@PathVariable Long employeeId) {
        return rewardPointsService.getProfileDto(employeeId);
    }

    @GetMapping("/all")
    public List<RewardPointsProfileDto> getAllRewardPoints() {
        return rewardPointsService.getAllProfilesDto();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRewardPointsProfile(@RequestBody Long employeeId, String employeeName) {
        String response = rewardPointsService.createRewardPointsProfile(employeeId, employeeName);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}/redeemed-vouchers")
    public ResponseEntity<List<RedeemedVoucher>> getRedeemedVouchers(@PathVariable Long employeeId) {
        RewardPointsProfile profile = rewardPointsService.getProfile(employeeId);
        return ResponseEntity.ok(profile.getRedeemedVouchers());
    }

    @PutMapping("/update-points")
    public ResponseEntity<String> updatePoints(@RequestBody UpdatePointsRequest request) {
        String response = rewardPointsService.updatePoints(request);
        return ResponseEntity.ok(response);
    }

}
