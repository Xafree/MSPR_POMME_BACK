package com.gostyle.webservice.dao;

import com.gostyle.webservice.entities.Coupon_is_registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface Coupon_is_registeredRepository extends JpaRepository<Coupon_is_registered, Integer> {
}
