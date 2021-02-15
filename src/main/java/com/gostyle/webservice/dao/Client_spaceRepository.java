package com.gostyle.webservice.dao;

import com.gostyle.webservice.entities.Client_space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface Client_spaceRepository extends JpaRepository<Client_space, Integer> {

    @Query("SELECT cs FROM Client_space cs WHERE cs.login_mail = ?1")
    List<Client_space> findByLogin_mail(String login_mail);


}
