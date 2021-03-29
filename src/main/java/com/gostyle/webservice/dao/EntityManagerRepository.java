package com.gostyle.webservice.dao;

import com.gostyle.webservice.dto.CouponReturned;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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


    public List<Integer> findAllCouponIdByLogin(int idClient_space){
        List<Integer> list =  entityManager
                .createNativeQuery(
                        "SELECT coupon.id " +
                                "FROM coupon_is_registered, coupon_is_consulted, coupon " +
                                "WHERE coupon_is_registered.coupon_is_consulted_id = coupon_is_consulted.id " +
                                "AND coupon_is_consulted.coupon_id = coupon.id " +
                                "AND coupon_is_registered.client_space_id = (?)")
                .setParameter(1, idClient_space)
                .getResultList();
        return list;
    }

    @Transactional
    public void deleteClient_spaceByLogin(String login) {
        entityManager
                .createNativeQuery("DELETE FROM client_space WHERE login_mail = (?)")
                .setParameter(1, login)
                .executeUpdate();
    }

    @Transactional
    public void insertCoupon_is_consulted(int idCoupon, String date) {
        entityManager
                .createNativeQuery("INSERT INTO coupon_is_consulted (coupon_id, string_date_ref) VALUES (?, ?)")
                .setParameter(1, idCoupon)
                .setParameter(2, date)
                .executeUpdate();
    }

    @Transactional
    public void deleteCoupon_is_registeredByIds(int idClientSpace, int idCoupon_is_consulted) {
        entityManager
                .createNativeQuery("DELETE FROM coupon_is_registered WHERE client_space_id = (?) AND coupon_is_consulted_id = (?)")
                .setParameter(1, idClientSpace)
                .setParameter(2, idCoupon_is_consulted)
                .executeUpdate();
    }

}
