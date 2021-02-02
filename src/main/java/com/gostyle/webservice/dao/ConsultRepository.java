package com.gostyle.webservice.dao;

import com.gostyle.webservice.entities.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ConsultRepository extends JpaRepository<Consult, Integer> {
}
