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
    public List<CouponReturned> returnCouponByProductId_1(int idProduit);

/*
    // SELECT product.id, coupon.id, type, description, prix, coupon.prix_pourcentage_reduction
    // FROM coupon
    // INNER JOIN product
    // ON coupon.product_id = product.id
    // WHERE coupon.id = 12
    @Query("SELECT p.id, c.id, p.type, p.description, p.prix, c.prix_pourcentage_reduction " +
            "FROM Coupon c " +
            "INNER JOIN c.product p " +
            "WHERE c.id = 12")
    CouponReturned returnCouponByProductId_1();

    // SELECT product.id, coupon.id, type, description, prix, coupon.prix_pourcentage_reduction
    // FROM coupon, product
    // WHERE coupon.id = 12 and coupon.product_id = product.id
    @Query("SELECT p.id, c.id, p.type, p.description, p.prix, c.prix_pourcentage_reduction " +
            "FROM Coupon c, Product P " +
            "WHERE c.id = 12 ")
    CouponReturned returnCouponByProductId_2();

 */
}
