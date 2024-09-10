package com.example.rewardspointsservice.repository;

import com.example.rewardspointsservice.model.Voucher;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoucherRepository  extends MongoRepository<Voucher, String> {
    Voucher findByVoucherId(Long voucherId);
}
