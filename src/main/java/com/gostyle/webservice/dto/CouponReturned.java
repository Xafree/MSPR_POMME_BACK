package com.gostyle.webservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponReturned {
    // Coupon or Product id
    private int id;
    private String type;
    private String description;
    private float prix;
    private float prix_pourcentage_reduction;
    private String codePromo;
    private String ville;

    public CouponReturned(int id, String type, String description, float prix, float prix_pourcentage_reduction, String ville) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.prix = prix;
        this.prix_pourcentage_reduction = prix_pourcentage_reduction;
        this.ville = ville;
    }

    public CouponReturned(int id, String type, String description, float prix, float prix_pourcentage_reduction, String codePromo, String ville) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.prix = prix;
        this.prix_pourcentage_reduction = prix_pourcentage_reduction;
        this.codePromo = codePromo;
        this.ville = ville;
    }

   private String stringDateRef;

}