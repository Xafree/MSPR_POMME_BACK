package com.gostyle.webservice.dao;

import com.gostyle.webservice.entities.Coupon;
import com.gostyle.webservice.dto.CouponReturned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
     // When user get 1 product (via QR Code scan), a coupon is returned with its codePromo
    @Query("SELECT new com.gostyle.webservice.dto.CouponReturned(c.id, p.type, p.description, p.prix, c.prix_pourcentage_reduction, c.codePromo, c.ville) " +
            "FROM Coupon c " +
            "JOIN c.product p " +
            "WHERE c.id = ?1")
    List<CouponReturned> returnCouponByProductId_1(int idCoupon);


     // When user only consult all promotions, all coupons are returned, without codePromo
    @Query("SELECT DISTINCT new com.gostyle.webservice.dto.CouponReturned(p.id, p.type, p.description, p.prix, c.prix_pourcentage_reduction, c.ville) " +
            "FROM Coupon c " +
            "JOIN c.product p " +
            "WHERE c.ville = ?1 " +
            "OR c.ville = 'All' " +
            "ORDER BY p.id")
    List<CouponReturned> findAllCouponsReturned(String ville);

}
