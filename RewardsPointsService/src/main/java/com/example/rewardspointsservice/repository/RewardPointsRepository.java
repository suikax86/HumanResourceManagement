package com.example.rewardspointsservice.repository;

import com.example.rewardspointsservice.model.RewardPointsProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RewardPointsRepository extends MongoRepository<RewardPointsProfile, String> {
    Optional<RewardPointsProfile> findByEmployeeId(Long employeeId);
    void deleteByEmployeeId(Long employeeId);
}
