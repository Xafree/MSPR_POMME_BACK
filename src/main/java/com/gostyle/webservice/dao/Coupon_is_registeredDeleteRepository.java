package com.gostyle.webservice.dao;

import com.gostyle.webservice.entities.Coupon_is_registered;
import com.gostyle.webservice.service.Coupon_is_registeredService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class Coupon_is_registeredDeleteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deleteCoupon_is_registeredByIds(int idClientSpace, int idCoupon_is_consulted) {
        entityManager
                .createNativeQuery("DELETE FROM coupon_is_registered WHERE client_space_id = (?) AND coupon_is_consulted_id = (?)")
                .setParameter(1, idClientSpace)
                .setParameter(2, idCoupon_is_consulted)
                .executeUpdate();
    }
}
