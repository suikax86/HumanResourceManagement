package com.example.rewardspointsservice.repository;

import com.example.rewardspointsservice.model.RewardPoints;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RewardPointsRepository extends MongoRepository<RewardPoints, String> {
    Optional<RewardPoints> findByEmployeeId(int employeeId);
}
