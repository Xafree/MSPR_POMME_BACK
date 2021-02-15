package com.gostyle.webservice.service;

import com.gostyle.webservice.dao.Client_spaceRepository;
import com.gostyle.webservice.entities.Client_space;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Client_spaceService {

    @Autowired
    private Client_spaceRepository repository;

    public Client_space addClient_space(Client_space client_space) {
        return repository.save(client_space);
    }

    public Client_space getClient_spaceById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Client_space> getClient_spaces() {
        return repository.findAll();
    }

    public List<Client_space> getLogin(String login_mail) {
        return repository.findByLogin_mail(login_mail);
    }

}
