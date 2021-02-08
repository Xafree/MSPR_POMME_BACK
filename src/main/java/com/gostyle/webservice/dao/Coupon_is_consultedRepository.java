package com.gostyle.webservice.dao;

import com.gostyle.webservice.entities.Coupon_is_consulted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
    public interface Coupon_is_consultedRepository extends JpaRepository<Coupon_is_consulted, Integer> {
}
