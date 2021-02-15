package com.gostyle.webservice.controller;

import com.gostyle.webservice.entities.Coupon;
import com.gostyle.webservice.dto.CouponReturned;
import com.gostyle.webservice.service.CouponService;
import com.gostyle.webservice.dto.CustomResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CouponController {

    @Autowired
    private CouponService service;
    /*
    @PostMapping("/coupon")
    public Coupon addCoupon(@RequestBody Coupon coupon){
        return service.addCoupon(coupon);
    }
    */
    @GetMapping("/coupon/{id}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable int id) {
        try {

            if (id < 0) {
                CustomResponseBody response = new CustomResponseBody(400, "Bad Request", "Parameter id cannot be negative", "/coupon/id");
                return new ResponseEntity(response, HttpStatus.OK);
            } else {
                Coupon coupon = service.getCouponById(id);

                if (coupon == null) {
                    CustomResponseBody response = new CustomResponseBody(204, "No Content", "This coupon doesn't exist", "/coupon/id");
                    return new ResponseEntity(response, HttpStatus.NO_CONTENT);
                } else {
                    CustomResponseBody response = new CustomResponseBody(200, "OK", "Found", "/coupon/id");
                    return new ResponseEntity(coupon, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            CustomResponseBody response = new CustomResponseBody(500, "Internal Server Error", "", "/coupon/id");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/couponresponse/{idProduit}")
    public ResponseEntity<List<CouponReturned>> getCouponResponse(@PathVariable int idProduit){

        try {

            if (idProduit < 0) {
                CustomResponseBody response = new CustomResponseBody(400, "Bad Request", "Parameter id cannot be negative", "/coupon/id");
                return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
            } else {
                List<CouponReturned> couponReturned = service.getCouponResponse(idProduit);

                if ( couponReturned == null || couponReturned.size() == 0 )
                {
                    CustomResponseBody response = new CustomResponseBody(204, "No Content", "This coupon doesn't exist", "/coupon/id");
                    return new ResponseEntity(response, HttpStatus.NO_CONTENT);
                }
                else if ( couponReturned.size() == 1 )
                {
                    CustomResponseBody response = new CustomResponseBody(200, "OK", "Found", "/coupon/id");
                    return new ResponseEntity(couponReturned, HttpStatus.OK);
                } else
                {
                    CustomResponseBody response = new CustomResponseBody(406, "Not Acceptable", "The client expected one coupon but the server has found more than one", "/coupon/id");
                    return new ResponseEntity(response, HttpStatus.NOT_ACCEPTABLE);
                }
            }
        } catch (Exception e) {
            CustomResponseBody response = new CustomResponseBody(500, "Internal Server Error", "", "/coupon/id");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/coupons")
    public List<Coupon> getCoupons(){
        return service.getCoupons();
    }

}
