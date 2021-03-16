package com.gostyle.webservice.service;

import com.gostyle.webservice.dao.CouponRepository;
import com.gostyle.webservice.entities.Coupon;
import com.gostyle.webservice.dto.CouponReturned;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRepository repository;

    public Coupon getCouponById(int id){
        return repository.findById(id).orElse(null);
    }

    public List<CouponReturned> getCouponResponse(int idCoupon){
        return repository.returnCouponByProductId_1(idCoupon);
    }

    public List<CouponReturned> getAllCouponResponse(String ville) {
        return repository.findAllCouponsReturned(ville);
    }

}
