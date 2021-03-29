package com.gostyle.webservice.controller;

import com.gostyle.webservice.dto.CouponReturned;
import com.gostyle.webservice.dto.CustomResponseBody;
import com.gostyle.webservice.entities.Client_space;
import com.gostyle.webservice.entities.Coupon_is_consulted;
import com.gostyle.webservice.entities.Coupon_is_registered;
import com.gostyle.webservice.service.Client_spaceService;
import com.gostyle.webservice.service.Coupon_is_consultedService;
import com.gostyle.webservice.service.Coupon_is_registeredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Coupon_is_registeredController {

    @Autowired
    private Coupon_is_registeredService service;
    @Autowired
    private Client_spaceService serviceClient_Space;
    @Autowired
    private Coupon_is_consultedService serviceCic;

    @PostMapping("/clientspace/coupon")
    public ResponseEntity<CustomResponseBody> add_Coupon_in_SpaceClient(
            @RequestBody Coupon_is_registered coupon_is_registered
    ){
        try {

            String mailEntered = coupon_is_registered
                    .getClient_space()
                    .getLogin_mail();

            String dateCoupon_is_consultedEntered = coupon_is_registered
                    .getCoupon_is_consulted()
                    .getStringDateRef();

            if ( mailEntered != null && dateCoupon_is_consultedEntered != null) {
                List<Client_space> listClient_spacesWithLogin = serviceClient_Space.getLogin(mailEntered);
                List<Coupon_is_consulted> listCoupon_is_consulted = serviceCic.findCouponByDate(dateCoupon_is_consultedEntered);

                if ( listClient_spacesWithLogin.size() == 0 || listCoupon_is_consulted.size() == 0 ) {
                    CustomResponseBody response = new CustomResponseBody(
                            404,
                            "Not Found",
                            "No client space found with this login and/or no Coupon_is_consulted found with the date entered",
                            "/clientspace/coupon");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

                } else if ( listClient_spacesWithLogin.size() == 1 && listCoupon_is_consulted.size() == 1 ){
                    // Search and find the client_space and coupon_is_consulted ids as they are required in table Coupon_is_registered,
                    // They are unknown by the front end, so we know these ids are missing in the request body
                    int idSpace_client = listClient_spacesWithLogin.get(0).getId();
                    coupon_is_registered
                            .getClient_space()
                            .setId(idSpace_client);

                    int idCoupon_is_consulted = listCoupon_is_consulted.get(0).getId();
                    coupon_is_registered
                            .getCoupon_is_consulted()
                            .setId(idCoupon_is_consulted);

                    // identify the couponId of the coupon_is_consulted entered
                    int idCouponEntered = listCoupon_is_consulted
                                                            .get(0)
                                                            .getCoupon()
                                                            .getId();

                    // The couple idSpace_client/idCoupon must be unique
                    boolean isCouponInClientSpace =  service.isCouponInClientSpace(idCouponEntered, idSpace_client);

                    if ( !isCouponInClientSpace) {
                        service.addCoupon_is_registered(coupon_is_registered);
                        CustomResponseBody response = new CustomResponseBody(200, "OK", "Created", "/clientspace/coupon");
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    } else {
                        CustomResponseBody response = new CustomResponseBody(
                                409,
                                "Conflict",
                                "Already existing coupon in this client space",
                                "/clientspace/coupon");
                        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
                    }

                } else {
                    CustomResponseBody response = new CustomResponseBody(409,"Conflict",
                            "Multiple client spaces exist with this login",
                            "/clientspace/coupon");
                    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
                }


            } else {
                CustomResponseBody response = new CustomResponseBody(400, "Bad Request",
                        "mail of the client space is missing or/and the date of coupon_is_consulted",
                        "/clientspace/coupon");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            CustomResponseBody response = new CustomResponseBody(500, "Internal Server Error", "", "/clientspace/coupon");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/clientspace/coupon/delete")
    public ResponseEntity<CustomResponseBody> delete_Coupon_in_SpaceClient(
            @RequestBody Coupon_is_registered coupon_is_registered
    ){
        try {
            // Retrieve the arguments in request
            String mailEntered = coupon_is_registered
                    .getClient_space()
                    .getLogin_mail();

            String dateCoupon_is_consultedEntered = coupon_is_registered
                    .getCoupon_is_consulted()
                    .getStringDateRef();

            if ( mailEntered != null && dateCoupon_is_consultedEntered != null) {
                // Search the parameters in database
                List<Client_space> listClient_spacesWithLogin = serviceClient_Space.getLogin(mailEntered);
                List<Coupon_is_consulted> listCoupon_is_consulted = serviceCic.findCouponByDate(dateCoupon_is_consultedEntered);

                if ( listClient_spacesWithLogin.size() == 0 || listCoupon_is_consulted.size() == 0 ) {
                    CustomResponseBody response = new CustomResponseBody(404, "Not Found",
                            "No client space found with this login and/or no Coupon_is_consulted found with the date entered",
                            "/clientspace/coupon/delete");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

                } else if ( listClient_spacesWithLogin.size() == 1 && listCoupon_is_consulted.size() == 1 ){
                    // Search and find the client_space and coupon_is_consulted ids as they are required in table Coupon_is_registered,
                    // They are unknown by the front end, so we know these ids are missing in the request body
                    int idSpace_client = listClient_spacesWithLogin.get(0).getId();
                    int idCoupon_is_consulted = listCoupon_is_consulted.get(0).getId();

                    // The couple idSpace_client/idCoupon_is_consulted must exists before delete
                    // Checking this information first enable to raise a custom response status rather than an error
                    List<Coupon_is_registered> listCirAccordingIds = service.findCoupon_is_registeredByIds(idSpace_client, idCoupon_is_consulted);

                    if ( listCirAccordingIds.size() == 1) {
                        service.deleteCouponInSpaceClientById(listCirAccordingIds.get(0).getId());
                        CustomResponseBody response = new CustomResponseBody(200, "Deleted", "", "/clientspace/coupon/delete");
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    } else {
                        CustomResponseBody response = new CustomResponseBody(404, "Not found",
                                "Coupon and client space are found but this client space doesn't contain this coupon",
                                "/clientspace/coupon/delete");
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    }

                } else {
                    CustomResponseBody response = new CustomResponseBody(409,"Conflict",
                            "Multiple entries are found in database with this client space and coupon",
                            "/clientspace/coupon/delete");
                    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
                }

            } else {
                CustomResponseBody response = new CustomResponseBody(400, "Bad Request",
                        "mail of the client space is missing or/and the date of coupon_is_consulted",
                        "/clientspace/coupon/delete");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            CustomResponseBody response = new CustomResponseBody(500, "Internal Server Error", "", "/clientspace/coupon/delete");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @PostMapping("/clientspace/coupons")
    public ResponseEntity get_Coupons_in_SpaceClient(@RequestBody Coupon_is_registered coupon_is_registered){
        try {

            String loginEntered = coupon_is_registered
                    .getClient_space()
                    .getLogin_mail();

            List<CouponReturned> listCir = service.getAllCouponsRegisteredByLogin(loginEntered);

            if ( listCir == null ) {
                CustomResponseBody response = new CustomResponseBody(404, "Not Found", "", "/clientspace/coupons");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else if ( listCir.size() == 0 ) {
                CustomResponseBody response = new CustomResponseBody(204, "Not Content", "", "/clientspace/coupons");
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(listCir, HttpStatus.OK);
            }
        } catch (Exception e) {
            CustomResponseBody response = new CustomResponseBody(500, "Internal Server Error", "", "/clientspace/coupons");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/coupons_are_registered")
    public List<Coupon_is_registered> getCoupons_are_registered(){
        return  service.getCoupons_are_registered();
    }

    @DeleteMapping("/coupon_is_registered/{idClientSpace}/{idCoupon_is_consulted}")
    public ResponseEntity<CustomResponseBody> deleteCoupon_is_registered(
            @PathVariable int idClientSpace,
            @PathVariable int idCoupon_is_consulted) {

        try {
            service.deleteCoupon_is_registeredByIds(idClientSpace, idCoupon_is_consulted);
            CustomResponseBody response = new CustomResponseBody(200, "OK", "", "/client_space/log");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            CustomResponseBody response = new CustomResponseBody(500, "Internal Server Error", "", "/client_space/id");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
