package com.gostyle.webservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String rue;
    private String ville;
    private int codePostal;
    @OneToOne
    private Client_space client_space;
    @OneToMany(mappedBy = "client")
    private List<Orders> orders;

}
