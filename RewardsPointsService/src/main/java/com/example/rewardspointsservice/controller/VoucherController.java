package com.example.rewardspointsservice.controller;

import com.example.rewardspointsservice.model.RewardPointsProfile;
import com.example.rewardspointsservice.model.VoucherDTO;
import com.example.rewardspointsservice.model.VoucherProfile;
import com.example.rewardspointsservice.repository.VoucherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/voucher-profile")
public class VoucherController {
    private final VoucherRepository voucherRepository;

    public VoucherController(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createVoucher(@RequestBody VoucherDTO voucher) {
        try {
            VoucherProfile VoucherProfile = new VoucherProfile();
            VoucherProfile.setBrand(voucher.getBrand());
            VoucherProfile.setName(voucher.getName());
            VoucherProfile.setCode(voucher.getCode());
            VoucherProfile.setDescription(voucher.getDescription());
            VoucherProfile.setDateEnd(voucher.getDateEnd());
            VoucherProfile.setDateStart(voucher.getDateStart());
            voucherRepository.save(VoucherProfile);
            return new ResponseEntity<>("OK", HttpStatus.CREATED );
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create profile: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
