package com.gostyle.webservice.service;

import com.gostyle.webservice.dao.Coupon_is_registeredDeleteRepository;
import com.gostyle.webservice.dao.Coupon_is_registeredRepository;
import com.gostyle.webservice.entities.Coupon_is_registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class Coupon_is_registeredService {

    @Autowired
    private Coupon_is_registeredRepository repository;
    @Autowired
    private Coupon_is_registeredDeleteRepository repositoryDelete;

    public Coupon_is_registered addCoupon_is_registered(Coupon_is_registered coupon_is_registered){
        return repository.save(coupon_is_registered);
    }

    public List<Coupon_is_registered> findCoupon_is_registeredByIds(int idSpace_client, int idCoupon_is_consulted){
        return repository.findCoupon_is_registeredByIds(idSpace_client, idCoupon_is_consulted);
    }

    public String deleteCouponInSpaceClientById(int idCoupon_is_registered) {
        if ( repository.existsById(idCoupon_is_registered) ) {
            repository.deleteById(idCoupon_is_registered);
            return "success";
        }
        else {
            return "not exists";
        }
    }

    public void deleteCoupon_is_registeredByIds(int idClientSpace, int idCoupon_is_consulted) {
        repositoryDelete.deleteCoupon_is_registeredByIds(idClientSpace, idCoupon_is_consulted);
    }


    public Coupon_is_registered getCoupon_is_registeredById(int id){
        return repository.findById(id).orElse(null);
    }

    public List<Coupon_is_registered> getCoupons_are_registered() {
        return repository.findAll();
    }

}
