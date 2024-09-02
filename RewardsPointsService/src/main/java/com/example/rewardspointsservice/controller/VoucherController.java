package com.example.rewardspointsservice.controller;

import com.example.rewardspointsservice.model.dtos.VoucherDTO;
import com.example.rewardspointsservice.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/voucher-profile")
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createVoucher(@RequestBody VoucherDTO voucher) {
       String response = voucherService.createVoucher(voucher);
       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
