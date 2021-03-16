package com.gostyle.webservice.controller;

import com.gostyle.webservice.entities.Coupon_is_consulted;
import com.gostyle.webservice.service.Coupon_is_consultedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Coupon_is_consultedController {

    @Autowired
    private Coupon_is_consultedService service;

    @PostMapping("/coupon_is_consulted")
    public Coupon_is_consulted addCoupon_is_consulted(@RequestBody Coupon_is_consulted coupon_is_consulted){
        return service.addCoupon_is_consulted(coupon_is_consulted);
    }

    @GetMapping("/coupon_is_consulted/{stringDateRef}")
    public List<Coupon_is_consulted> getCoupon_is_consultedByStringDateRef(@PathVariable String stringDateRef){
        return service.findCouponByDate(stringDateRef);
    }

    @GetMapping("/coupons_are_consulted")
    public List<Coupon_is_consulted> getCoupons_are_consulted(){
        return  service.getCoupons_are_consulted();
    }

}
