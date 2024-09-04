package com.example.rewardspointsservice.service;

import com.example.rewardspointsservice.model.RewardPointsProfile;
import com.example.rewardspointsservice.model.Voucher;
import com.example.rewardspointsservice.model.dtos.CreateVoucherRequest;
import com.example.rewardspointsservice.model.dtos.RedeemVoucherRequest;
import com.example.rewardspointsservice.model.dtos.VoucherDTO;
import com.example.rewardspointsservice.repository.VoucherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final RewardPointsService rewardPointsService;

    public VoucherService(VoucherRepository voucherRepository, RewardPointsService rewardPointsService) {
        this.voucherRepository = voucherRepository;
        this.rewardPointsService = rewardPointsService;
    }

    public String createVoucher(CreateVoucherRequest voucher) {

        Voucher Voucher = new Voucher(
                voucher.getBrand(),
                voucher.getName(),
                voucher.getDescription(),
                voucher.getPoints(),
                voucher.getQuantity(),
                voucher.getDateStart(),
                voucher.getDateEnd()
        );
        voucherRepository.save(Voucher);

        return "Voucher created successfully";
    }

    public List<VoucherDTO> getVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        List<VoucherDTO> voucherDTOS = new ArrayList<>();
        for (Voucher voucher : vouchers) {
            VoucherDTO voucherDTO = new VoucherDTO(
                    voucher.getVoucherId(),
                    voucher.getBrand(),
                    voucher.getName(),
                    voucher.getDescription(),
                    voucher.getPoints(),
                    voucher.getQuantity(),
                    voucher.getDateStart(),
                    voucher.getDateEnd()
            );
            voucherDTOS.add(voucherDTO);
        }
        return voucherDTOS;
    }

    public String redeemVoucher(RedeemVoucherRequest request) {
        Voucher voucher = voucherRepository.findByVoucherId(request.getVoucherId());

        if (voucher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Voucher not found");
        }

        if (voucher.getQuantity() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Voucher out of stock");
        }

        RewardPointsProfile profile = rewardPointsService.getProfile(request.getEmployeeId());

        if(profile.getTotalPoints() < voucher.getPoints()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient points");
        }

        profile.subtractPoints(0L, voucher.getPoints(), "Voucher redeemed: " + voucher.getName());
        voucher.setQuantity(voucher.getQuantity() - 1);

        rewardPointsService.saveProfile(profile);
        voucherRepository.save(voucher);

        return "Voucher redeemed successfully";
    }
}
