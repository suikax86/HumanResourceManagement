package com.example.rewardspointsservice.service;

import com.example.rewardspointsservice.model.VoucherProfile;
import com.example.rewardspointsservice.model.dtos.VoucherDTO;
import com.example.rewardspointsservice.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public String createVoucher(VoucherDTO voucher) {

        VoucherProfile VoucherProfile = new VoucherProfile();
        VoucherProfile.setBrand(voucher.getBrand());
        VoucherProfile.setName(voucher.getName());
        VoucherProfile.setCode(voucher.getCode());
        VoucherProfile.setDescription(voucher.getDescription());
        VoucherProfile.setDateEnd(voucher.getDateEnd());
        VoucherProfile.setDateStart(voucher.getDateStart());
        voucherRepository.save(VoucherProfile);

        return "Voucher created successfully";
    }
}
