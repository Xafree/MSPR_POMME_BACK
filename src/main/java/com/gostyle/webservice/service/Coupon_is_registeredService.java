package com.gostyle.webservice.service;

import com.gostyle.webservice.dao.Coupon_is_registeredRepository;
import com.gostyle.webservice.dao.EntityManagerRepository;
import com.gostyle.webservice.dto.CouponReturned;
import com.gostyle.webservice.entities.Coupon_is_registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Coupon_is_registeredService {

    @Autowired
    private Coupon_is_registeredRepository repository;
    @Autowired
    private EntityManagerRepository entityManagerRepository;

    public Coupon_is_registered addCoupon_is_registered(Coupon_is_registered coupon_is_registered){
        return repository.save(coupon_is_registered);
    }

    public List<Coupon_is_registered> findCoupon_is_registeredByIds(int idSpace_client, int idCoupon_is_consulted){
        return repository.findCoupon_is_registeredByIds(idSpace_client, idCoupon_is_consulted);
    }

    public boolean isCouponInClientSpace(int idCouponEntered, int idClient_space){
        List<Integer> listCouponIdByLogin = entityManagerRepository.findAllCouponIdByLogin(idClient_space);
        boolean isCouponInClientSpace = listCouponIdByLogin.contains(idCouponEntered);
        return isCouponInClientSpace;
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

    public List<CouponReturned> getAllCouponsRegisteredByLogin(String login_mail){
        return entityManagerRepository.findAllCouponByLogin(login_mail);
    }

    public void deleteCoupon_is_registeredByIds(int idClientSpace, int idCoupon_is_consulted) {
        entityManagerRepository.deleteCoupon_is_registeredByIds(idClientSpace, idCoupon_is_consulted);
    }

    public List<Coupon_is_registered> getCoupons_are_registered() {
        return repository.findAll();
    }

}
