package com.example.rewardspointsservice.controller;

import com.example.rewardspointsservice.model.dtos.CreateVoucherRequest;
import com.example.rewardspointsservice.model.dtos.RedeemVoucherRequest;
import com.example.rewardspointsservice.model.dtos.VoucherDTO;
import com.example.rewardspointsservice.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createVoucher(@RequestBody CreateVoucherRequest voucher) {
        String response = voucherService.createVoucher(voucher);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<?> getVouchers() {
        return new ResponseEntity<>(voucherService.getVouchers(), HttpStatus.OK);
    }

    @PostMapping("/redeem")
    public ResponseEntity<String> redeemVoucher(@RequestBody RedeemVoucherRequest request) {
        String response = voucherService.redeemVoucher(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
