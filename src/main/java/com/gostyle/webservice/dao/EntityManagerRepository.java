package com.gostyle.webservice.dao;

import com.gostyle.webservice.dto.CouponReturned;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EntityManagerRepository {

    @PersistenceContext
    private EntityManager entityManager;
    public List<CouponReturned> findAllCouponByLogin(String login) {
        List<CouponReturned> list =  entityManager
                .createNativeQuery(
                        "SELECT c.id, p.type, p.description, p.prix, c.prix_pourcentage_reduction, c.code_promo, c.ville " +
                                "FROM ((((client_space INNER JOIN coupon_is_registered ON coupon_is_registered.client_space_id = client_space.id) " +
                                "INNER JOIN coupon_is_consulted ON coupon_is_registered.coupon_is_consulted_id = coupon_is_consulted.id) " +
                                "INNER JOIN coupon c ON coupon_is_consulted.coupon_id = c.id) " +
                                "INNER JOIN product p ON p.id = c.product_id) " +
                                "WHERE client_space.login_mail = (?) " +
                                "GROUP BY coupon_is_consulted.coupon_id ")
                .setParameter(1, login)
                .getResultList();
        return list;
    }
}
