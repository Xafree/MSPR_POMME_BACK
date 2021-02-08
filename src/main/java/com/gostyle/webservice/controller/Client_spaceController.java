package com.gostyle.webservice.controller;

import com.gostyle.webservice.entities.Client_space;
import com.gostyle.webservice.service.Client_spaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Client_spaceController {

    @Autowired
    private Client_spaceService service;

    @PostMapping("/client_space")
    public Client_space addClient_space(@RequestBody Client_space client_space){
        return service.addClient_space(client_space);
    }

    @GetMapping("/client_space/{id}")
    public Client_space getClient_spaceById(@PathVariable int id){
        return service.getClient_spaceById(id);
    }

    @GetMapping("/client_spaces")
    public List<Client_space> getClient_spaces(){
        return  service.getClient_spaces();
    }

}
