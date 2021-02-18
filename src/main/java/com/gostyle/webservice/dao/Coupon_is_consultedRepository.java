package com.gostyle.webservice.dao;

import com.gostyle.webservice.entities.Coupon_is_consulted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
    public interface Coupon_is_consultedRepository extends JpaRepository<Coupon_is_consulted, Integer> {

    @Query("SELECT cic FROM Coupon_is_consulted cic WHERE cic.stringDateRef = ?1")
    List<Coupon_is_consulted> findCouponByDate(String stringDateRef);

}
