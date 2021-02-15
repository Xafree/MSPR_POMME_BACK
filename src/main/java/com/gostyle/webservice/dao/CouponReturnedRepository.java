package com.gostyle.webservice.dao;

import com.gostyle.webservice.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CouponReturnedRepository extends JpaRepository<Coupon, Integer> {
    //@Query("SELECT l FROM Client_space l WHERE l.login_mail = ?1")
    // SELECT DISTINCT(product.id), description, prix, type FROM coupon, product WHERE product.id = 1 and coupon.product_id = product.id
    // SELECT product.id, coupon.id, type, description, prix, coupon.prix_pourcentage_reduction FROM coupon, product WHERE coupon.id = 12 and product.id = 1 and coupon.product_id = product.id
    // SELECT product.id, coupon.id, type, description, prix, coupon.prix_pourcentage_reduction FROM coupon, product WHERE coupon.id = 12 and coupon.product_id = product.id
    // @Query("SELECT new JoinDto(s.id, f.name, c.name, s.amount) " + "FROM Sale s INNER JOIN s.food f INNER JOIN f.company c")
    // @Query("SELECT l FROM Coupon l WHERE l.prix_pourcentage_reduction = ?1")

}
