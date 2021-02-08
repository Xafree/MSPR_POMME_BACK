package com.gostyle.webservice.controller;

import com.gostyle.webservice.entities.Coupon_is_registered;
import com.gostyle.webservice.service.Coupon_is_registeredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Coupon_is_registeredController {

    @Autowired
    private Coupon_is_registeredService service;

    @PostMapping("/coupon_is_registered")
    public Coupon_is_registered addCoupon_is_registered(@RequestBody Coupon_is_registered coupon_is_registered){
        return service.addCoupon_is_registered(coupon_is_registered);
    }

    @GetMapping("/coupon_is_registered/{id}")
    public Coupon_is_registered getCoupon_is_registeredById(@PathVariable int id){
        return service.getCoupon_is_registeredById(id);
    }

    @GetMapping("/coupons_are_registered")
    public List<Coupon_is_registered> getCoupons_are_registered(){
        return  service.getCoupons_are_registered();
    }

}
