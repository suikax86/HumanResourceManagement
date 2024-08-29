package com.example.rewardspointsservice.repository;

import com.example.rewardspointsservice.model.RewardPointsProfile;
import com.example.rewardspointsservice.model.VoucherProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VoucherRepository  extends MongoRepository<VoucherProfile, String> {
}
