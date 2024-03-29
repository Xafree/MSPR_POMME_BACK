package com.gostyle.webservice.controller;

import com.gostyle.webservice.entities.Coupon;
import com.gostyle.webservice.dto.CouponReturned;
import com.gostyle.webservice.service.CouponService;
import com.gostyle.webservice.dto.CustomResponseBody;
import com.gostyle.webservice.service.Coupon_is_consultedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class CouponController {

    @Autowired
    private CouponService service;
    @Autowired
    private Coupon_is_consultedService cic_service;

    @GetMapping("/coupon/{id}")
    public ResponseEntity getCoupon(@PathVariable int id) {
        try {

            if (id < 0) {
                CustomResponseBody response = new CustomResponseBody(400, "Bad Request", "Parameter id cannot be negative", "/coupon/id");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                Coupon coupon = service.getCouponById(id);

                if (coupon == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(coupon, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            CustomResponseBody response = new CustomResponseBody(500, "Internal Server Error", "", "/coupon/id");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/private/couponresponse/{idCoupon}")
    public ResponseEntity getCouponResponse(@PathVariable int idCoupon){

        try {

            if (idCoupon < 0) {
                CustomResponseBody response = new CustomResponseBody(
                        400,
                        "Bad Request",
                        "Parameter id cannot be negative",
                        "/couponresponse/{idCoupon}");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                LocalDateTime timestamp = LocalDateTime.now();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String stringDateRef = timestamp.format(dateFormatter).replaceAll("\\s", "_");

                List<CouponReturned> couponReturned = service.getCouponResponse(idCoupon);

                if ( couponReturned == null || couponReturned.size() == 0 )
                {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                else if ( couponReturned.size() == 1 )
                {
                    cic_service.insertCic(idCoupon, stringDateRef);
                    couponReturned.get(0).setStringDateRef(stringDateRef);
                    return new ResponseEntity<>(couponReturned, HttpStatus.OK);
                } else
                {
                    CustomResponseBody response = new CustomResponseBody(406,
                            "Not Acceptable",
                            "The client expected one coupon but the server has found more than one",
                            "/couponresponse/{idCoupon}");
                    return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
                }
            }
        } catch (Exception e) {
            CustomResponseBody response = new CustomResponseBody(500, "Internal Server Error", "", "/couponresponse/{idCoupon}");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/couponresponse/ville/{ville}")
    public ResponseEntity getAllCouponResponse(@PathVariable String ville){

        try {
            List<CouponReturned> couponReturned = service.getAllCouponResponse(ville);

            if ( couponReturned == null || couponReturned.size() == 0 )
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else
            {
                return new ResponseEntity<>(couponReturned, HttpStatus.OK);
            }
        } catch (Exception e) {
            CustomResponseBody response = new CustomResponseBody(500, "Internal Server Error", "", "/couponresponse/ville/{ville}");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
