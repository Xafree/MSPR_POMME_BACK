package com.gostyle.webservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponReturned {
    // Coupon id
    private int id;
    private String type;
    private String description;
    private float prix;
    private float prix_pourcentage_reduction;

    public CouponReturned(int id, String type, String description, float prix, float prix_pourcentage_reduction) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.prix = prix;
        this.prix_pourcentage_reduction = prix_pourcentage_reduction;
    }

   private String stringDateRef;

}