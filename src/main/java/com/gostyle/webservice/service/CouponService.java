package com.gostyle.webservice.service;

import com.gostyle.webservice.dao.CouponRepository;
import com.gostyle.webservice.entities.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRepository repository;

    public Coupon addCoupon(Coupon coupon){
        return repository.save(coupon);
    }

    public Coupon getCouponById(int id){
        return repository.findById(id).orElse(null);
    }

    public List<Coupon> getCoupons(){
        return repository.findAll();
    }

}
