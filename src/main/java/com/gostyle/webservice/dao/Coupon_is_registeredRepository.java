package com.gostyle.webservice.dao;

import com.gostyle.webservice.entities.Coupon_is_registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface Coupon_is_registeredRepository extends JpaRepository<Coupon_is_registered, Integer> {

    @Query("SELECT cir " +
            "FROM Coupon_is_registered cir " +
            "WHERE cir.client_space.id = ?1 " +
            "AND cir.coupon_is_consulted.id = ?2")
    List<Coupon_is_registered> findCoupon_is_registeredByIds(int idSpace_client, int idCoupon_is_consulted);

}
