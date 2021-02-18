package com.gostyle.webservice.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class Coupon_is_consultedInsertRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertCoupon_is_consulted(int idCoupon, String date) {
        entityManager
                .createNativeQuery("INSERT INTO coupon_is_consulted (coupon_id, string_date_ref) VALUES (?, ?)")
                .setParameter(1, idCoupon)
                .setParameter(2, date)
                .executeUpdate();
    }

}
