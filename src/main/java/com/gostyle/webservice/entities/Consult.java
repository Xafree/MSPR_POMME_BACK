package com.gostyle.webservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Consult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date dateConsultation;
/*
    @OneToMany(targetEntity = Person.class)
    private Collection<Person> persons;

    @OneToMany(targetEntity = Product.class)
    private Collection<Product> products;
*/

}
