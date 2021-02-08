package com.gostyle.webservice.dao;

import com.gostyle.webservice.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
}
