package com.gostyle.webservice.service;

import com.gostyle.webservice.dao.Coupon_is_consultedRepository;
import com.gostyle.webservice.dao.EntityManagerRepository;
import com.gostyle.webservice.entities.Coupon_is_consulted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Coupon_is_consultedService {

    @Autowired
    private Coupon_is_consultedRepository repository;
    @Autowired
    private EntityManagerRepository entityManagerRepository;

    public Coupon_is_consulted addCoupon_is_consulted(Coupon_is_consulted coupon_is_consulted){
        return repository.save(coupon_is_consulted);
    }

    public void insertCic(int idCoupon, String stringDateRef){
        entityManagerRepository.insertCoupon_is_consulted(idCoupon, stringDateRef);
    }

    public List<Coupon_is_consulted> findCouponByDate(String stringDateRef) {
        return repository.findCouponByDate(stringDateRef);
    }

    public List<Coupon_is_consulted> getCoupons_are_consulted(){
        return repository.findAll();
    }

}
