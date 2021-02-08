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
public class Client_space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login_mail;
    private String password;
    @OneToOne
    private Client client;
    @OneToMany(mappedBy = "client_space")
    private List<Coupon_is_registered> coupons_are_registered;

}
