package com.gostyle.webservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonIgnore
    public List<Coupon> getCoupons() {
        return coupons;
    }
    @JsonProperty("coupons")
    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }
    @JsonIgnore
    public List<Orders> getOrders() {
        return orders;
    }
    @JsonProperty("orders")
    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}
