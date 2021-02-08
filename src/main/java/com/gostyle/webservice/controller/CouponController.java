package com.gostyle.webservice.controller;

import com.gostyle.webservice.entities.Coupon;
import com.gostyle.webservice.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CouponController {

    @Autowired
    private CouponService service;

    @PostMapping("/coupon")
    public Coupon addCoupon(@RequestBody Coupon coupon){
        return service.addCoupon(coupon);
    }

    @GetMapping("/coupon/{id}")
    public Coupon getCouponById(@PathVariable int id){
        return service.getCouponById(id);
    }

    @GetMapping("/coupons")
    public List<Coupon> getCoupons(){
        return  service.getCoupons();
    }

}
