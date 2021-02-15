package com.gostyle.webservice.controller;

import com.gostyle.webservice.entities.Client_space;
import com.gostyle.webservice.service.Client_spaceService;
import com.gostyle.webservice.dto.CustomResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Client_spaceController {

    @Autowired
    private Client_spaceService service;

    @PostMapping("/client_space/create")
    public ResponseEntity<Client_space> addClient_space(@RequestBody Client_space client_space){

        try {
            // Check whether this space client doesn't already exist before creating it
            String mailClientSpaceToCreate = client_space.getLogin_mail();
            List<Client_space> resultSearchLogin = service.getLogin(mailClientSpaceToCreate);

            if (resultSearchLogin.size() == 0) {
                service.addClient_space(client_space);
                CustomResponseBody response = new CustomResponseBody(200, "OK", "Created", "/client_space/create");
                return new ResponseEntity(response, HttpStatus.OK);
            } else {
                CustomResponseBody response = new CustomResponseBody(409, "Conflict", "A client space with this login already exists", "/client_space/create");
                return new ResponseEntity(response, HttpStatus.CONFLICT);
            }

        } catch (Exception e) {
            CustomResponseBody response = new CustomResponseBody(500, "Internal Server Error", "", "/client_space/create");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/client_space/log")
    public ResponseEntity<List<Client_space>> getClient_spaceByLogin(@RequestBody Client_space client_space){

        try {
            String mailClientSpaceToLogIn = client_space.getLogin_mail();
            List<Client_space> resultSearchClientSpaceLogin = service.getLogin(mailClientSpaceToLogIn);

            if ( resultSearchClientSpaceLogin.size() == 0 ) {
                CustomResponseBody response = new CustomResponseBody(404, "Not Found", "No client space found with this login", "/client_space/log");
                return new ResponseEntity(response, HttpStatus.NOT_FOUND);

            } else if ( resultSearchClientSpaceLogin.size() == 1 ){
                String passwordClientSpaceToLogIn = client_space.getPassword();
                String correctPassword = resultSearchClientSpaceLogin.get(0).getPassword();
                if ( passwordClientSpaceToLogIn.equals(correctPassword) ) {
                    CustomResponseBody response = new CustomResponseBody(200,"OK", "","/client_space/log");
                    return new ResponseEntity(response, HttpStatus.OK);
                } else {
                    CustomResponseBody response = new CustomResponseBody(400,"Bad Request", "The password doesn't match the login","/client_space/log");
                    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
                }

            } else {
                CustomResponseBody response = new CustomResponseBody(409,"Conflict", "Multiple client spaces exist with this login","/client_space/log");
                return new ResponseEntity(response, HttpStatus.CONFLICT);
            }

        } catch (Exception e) {
            CustomResponseBody response = new CustomResponseBody(500, "Internal Server Error", "", "/client_space/log");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


/*
    @GetMapping("/client_space/{id}")
    public Client_space getClient_spaceById(@PathVariable int id){
        return service.getClient_spaceById(id);
    }
*/
/*
    @GetMapping("/client_space/{login_mail}")
    public ResponseEntity<List<Client_space>> getClient_spaceByLogin(@PathVariable String login_mail){

        List<Client_space> loginTab = service.getLogin(login_mail);

        if ( loginTab.size() == 0 ) {
            CustomResponseBody response = new CustomResponseBody(204,"No Content", "","/client_space/login");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        } else {
            CustomResponseBody response = new CustomResponseBody(200,"OK", "","/client_space/login");
            return new ResponseEntity(loginTab, HttpStatus.OK);
        }
    }
*/
    @GetMapping("/client_spaces")
    public List<Client_space> getClient_spaces(){
        return  service.getClient_spaces();
    }

}
