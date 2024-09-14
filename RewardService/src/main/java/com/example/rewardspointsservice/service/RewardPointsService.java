package com.example.rewardspointsservice.service;

import com.example.rewardspointsservice.model.RedeemedVoucher;
import com.example.rewardspointsservice.model.RewardPointsProfile;
import com.example.rewardspointsservice.model.dtos.RewardPointsProfileDto;
import com.example.rewardspointsservice.repository.RewardPointsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class RewardPointsService {
    private final RewardPointsRepository rewardPointsRepository;


    public RewardPointsService(RewardPointsRepository rewardPointsRepository) {
        this.rewardPointsRepository = rewardPointsRepository;
    }

    public RewardPointsProfile getProfile(Long employeeId) {
        return rewardPointsRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    public void saveProfile(RewardPointsProfile profile) {
        rewardPointsRepository.save(profile);
    }

    public RewardPointsProfileDto getProfileDto(Long employeeId) {
        RewardPointsProfile profile = rewardPointsRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        RewardPointsProfileDto profileDto = new RewardPointsProfileDto();
        profileDto.setId(profile.getId());
        profileDto.setEmployeeId(profile.getEmployeeId());
        profileDto.setTotalPoints(profile.getTotalPoints());
        profileDto.setPointsHistory(profile.getPointsHistory());
        return profileDto;
    }

    public List<RewardPointsProfileDto> getAllProfilesDto() {
        List<RewardPointsProfile> profiles = rewardPointsRepository.findAll();
        List<RewardPointsProfileDto> profileDtos = new ArrayList<>();
        for (RewardPointsProfile profile : profiles) {
            RewardPointsProfileDto profileDto = new RewardPointsProfileDto();
            profileDto.setId(profile.getId());
            profileDto.setEmployeeId(profile.getEmployeeId());
            profileDto.setTotalPoints(profile.getTotalPoints());
            profileDto.setPointsHistory(profile.getPointsHistory());
            profileDtos.add(profileDto);
        }
        return profileDtos;
    }

    public String createRewardPointsProfile(Long employeeId, String employeeName) {
            // check if the employee already has a reward points profile
            if (rewardPointsRepository.findByEmployeeId(employeeId).isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile already exists for employee ID: " + employeeId);
            }
            RewardPointsProfile profile = new RewardPointsProfile();
            profile.setEmployeeId(employeeId);
            profile.setEmployeeName(employeeName);
            rewardPointsRepository.save(profile);
            return "Profile created successfully for employee ID: " + employeeId;
    }

    public double getRewardPoints(Long employeeId) {
        RewardPointsProfile profile = rewardPointsRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        return profile.getTotalPoints();
    }

    public void deleteRewardPointsProfile(Long employeeId) {
        rewardPointsRepository.deleteByEmployeeId(employeeId);
    }


    public String transferPoints(Long fromEmployeeId, Long toEmployeeId, double amount, String msg) {
        RewardPointsProfile fromProfile = rewardPointsRepository.findByEmployeeId(fromEmployeeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        RewardPointsProfile toProfile = rewardPointsRepository.findByEmployeeId(toEmployeeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        if (fromProfile.getTotalPoints() < amount) {
            return "Insufficient points";
        }

        fromProfile.subtractPoints(toEmployeeId, amount, "Transfer to employee: " + toProfile.getEmployeeName());

        String message = "Transfer from employee: " + fromProfile.getEmployeeName();

        if(msg != null && !msg.isEmpty()) {
            message = message + " with message: " + msg;
        }

        toProfile.addPoints(fromEmployeeId, amount, message);

        rewardPointsRepository.save(fromProfile);
        rewardPointsRepository.save(toProfile);

        return "Points transferred successfully";
    }

    public void saveRedeemedVoucher(Long employeeId, RedeemedVoucher redeemedVoucher) {
        RewardPointsProfile profile = getProfile(employeeId);
        profile.getRedeemedVouchers().add(redeemedVoucher);
        saveProfile(profile);
    }


}
