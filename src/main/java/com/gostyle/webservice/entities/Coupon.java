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
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float prix_pourcentage_reduction;
    private String codePromo;
    private String ville;
    @ManyToOne
    private Product product;
    @OneToMany(mappedBy = "coupon")
    private List<Coupon_is_consulted> coupons_are_consulted;
}
