package com.gostyle.webservice.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class Client_spaceDeleteRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deleteClient_spaceByLogin(String login) {
        entityManager
                .createNativeQuery("DELETE FROM client_space WHERE login_mail = (?)")
                .setParameter(1, login)
                .executeUpdate();
    }


}
