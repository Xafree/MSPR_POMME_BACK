package com.gostyle.webservice.dao;

import com.gostyle.webservice.entities.Coupon;
import com.gostyle.webservice.dto.CouponReturned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    @Query("SELECT new com.gostyle.webservice.dto.CouponReturned(p.id, p.type, p.description, p.prix, c.prix_pourcentage_reduction) " +
            "FROM Coupon c " +
            "JOIN c.product p " +
            "WHERE c.id = ?1")
    List<CouponReturned> returnCouponByProductId_1(int idCoupon);

}
