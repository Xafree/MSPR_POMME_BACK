package com.gostyle.webservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponReturned {
    private int id;
    private String type;
    private String description;
    private float prix;
    private float prix_pourcentage_reduction;
}