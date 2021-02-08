package com.gostyle.webservice.service;

import com.gostyle.webservice.dao.Coupon_is_registeredRepository;
import com.gostyle.webservice.entities.Coupon_is_registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Coupon_is_registeredService {

    @Autowired
    private Coupon_is_registeredRepository repository;

    public Coupon_is_registered addCoupon_is_registered(Coupon_is_registered coupon_is_registered){
        return repository.save(coupon_is_registered);
    }

    public Coupon_is_registered getCoupon_is_registeredById(int id){
        return repository.findById(id).orElse(null);
    }

    public List<Coupon_is_registered> getCoupons_are_registered() {
        return repository.findAll();
    }

}
