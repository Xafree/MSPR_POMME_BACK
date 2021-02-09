package com.gostyle.webservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;
    private String description;
    private long barcode;
    private float prix;
    @OneToMany(mappedBy = "product")
    private List<Coupon> coupons;
    @OneToMany(mappedBy = "product")
    private List<Orders> orders;
}
