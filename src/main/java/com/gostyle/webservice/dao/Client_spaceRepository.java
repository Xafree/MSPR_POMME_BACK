package com.gostyle.webservice.dao;

import com.gostyle.webservice.entities.Client_space;
import com.gostyle.webservice.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface Client_spaceRepository extends JpaRepository<Client_space, Integer> {

    @Query("SELECT cs FROM Client_space cs WHERE cs.login_mail = ?1")
    List<Client_space> findByLogin_mail(String login_mail);

    @Query("SELECT c FROM Client c WHERE c.mail = ?1")
    List<Client> findClientByMail(String login_mail);

    @Modifying
    @Query("UPDATE Client_space cs SET cs.client.id = :client_id WHERE cs.login_mail = :login_mail")
    int updateClient_SpaceSetClientIdForLogin(@Param("client_id") Integer id_ClientSpace,
                                   @Param("login_mail") String login_mail);


}