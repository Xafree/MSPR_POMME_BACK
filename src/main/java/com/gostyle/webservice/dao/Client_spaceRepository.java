package com.gostyle.webservice.dao;

import com.gostyle.webservice.entities.Client_space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface Client_spaceRepository extends JpaRepository<Client_space, Integer> {
}
