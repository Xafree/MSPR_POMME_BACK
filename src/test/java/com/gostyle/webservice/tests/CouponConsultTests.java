package com.gostyle.webservice.tests;

import com.gostyle.webservice.dto.CouponReturned;
import com.gostyle.webservice.entities.Coupon_is_consulted;
import com.gostyle.webservice.util.TestService;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CouponConsultTests {

    private HttpUriRequest request;
    private HttpResponse httpResponse;
    private int httpStatusCode;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("GET  PATH=\"/couponresponse/ville/{ville}\" TEST Return Status 200 with a not null/empty result and no duplicated products, when coupon(s) exist(s) for the city")
    public void getAllCouponResponseByCity_returnListResult() throws IOException {

        // Arrange
        String pathVariable = TestService.CITY_WITH_COUPONS_IN_DATABASE;
        String uri = "http://localhost:8080/couponresponse/ville/" + pathVariable;

        // Act
        request = new HttpGet(uri);
        httpResponse = TestService.executeGETRequestAndReturnHttpResponse(request);
        httpStatusCode = httpResponse.getStatusLine().getStatusCode();
        CouponReturned[] responseObject = TestService.retrieveObjectFromHttpResponse(CouponReturned[].class, httpResponse);

        // Assert
        int nbResultFromDatabase = responseObject.length;
        List<Integer> listProductIds = new ArrayList<>();
        boolean duplicateProducts = false;

        int productId;
        for ( CouponReturned couponReturned: responseObject ) {
            productId = couponReturned.getId();
            if ( listProductIds.contains(productId) ) {
                duplicateProducts = true;
                break;
            } else {
                listProductIds.add(productId);
            }
        }

        assertTrue(
                httpStatusCode == HttpStatus.SC_OK
                && nbResultFromDatabase > 0
                && !duplicateProducts
        );

    }


    @Test
    @DisplayName("GET  PATH=\"/couponresponse/{idCoupon}\" TEST Return Status 200 when coupon exists")
    public void getCouponResponse_returnStatus200() throws IOException {

        // Arrange
        String pathVariable = TestService.EXISTING_COUPON_ID_1;
        String uri = "http://localhost:8080/couponresponse/" + pathVariable;

        // Act
        request = new HttpGet(uri);
        httpResponse = TestService.executeGETRequestAndReturnHttpResponse(request);
        httpStatusCode = httpResponse.getStatusLine().getStatusCode();

        // Assert
        assertEquals(httpStatusCode, HttpStatus.SC_OK);

    }

    @Test
    @DisplayName("GET  PATH=\"/couponresponse/{idCoupon}\" TEST Return Status 204 when coupon doesn't exist")
    public void getCouponResponse_returnStatus204() throws IOException {

        // Arrange
        String pathVariable = TestService.NOT_EXISTING_COUPON_ID;
        String uri = "http://localhost:8080/couponresponse/" + pathVariable;

        // Act
        request = new HttpGet(uri);
        httpResponse = TestService.executeGETRequestAndReturnHttpResponse(request);
        httpStatusCode = httpResponse.getStatusLine().getStatusCode();

        // Assert
        assertEquals(httpStatusCode, HttpStatus.SC_NO_CONTENT);

    }

    @Test
    @DisplayName("GET  PATH=\"/couponresponse/{idCoupon}\" TEST Return only one response of type CouponReturned when coupon exists")
    public void getCouponResponse_returnOneCouponReturned() throws IOException {

        // Arrange
        String pathVariable = TestService.EXISTING_COUPON_ID_1;
        String uri = "http://localhost:8080/couponresponse/" + pathVariable;

        // Act
        request = new HttpGet(uri);
        httpResponse = TestService.executeGETRequestAndReturnHttpResponse(request);
        CouponReturned[] responseObject = TestService.retrieveObjectFromHttpResponse(CouponReturned[].class, httpResponse);

        // Assert
        assertEquals(1, responseObject.length);

    }

    /*
     * <!> Test can fail while running with other tests
     * Try to run run only this
     * To prevent this failure while running with the others test of the class,
     * it can be useful to insert a pause in the thread
     * Despite all the tricks, run can fail because of Jackson (de)serialization.
     * As a last resort, you can run in a HTTP client (like Postman).
     */

    @Test
    @DisplayName("GET  PATH=\"/couponresponse/{idCoupon}\" TEST CouponReturned also save a Coupon_is_consulted, both sharing the same stringDateRef and IdCoupon")
    public void getCouponResponse_saveAlsoACoupon_is_consulted() throws IOException, InterruptedException {

        //Thread.sleep(2000);
        // Arrange
        // ... for CouponReturned (Cr)
        String pathVariableCr = TestService.EXISTING_COUPON_ID_2;
        int idCouponOfCr = Integer.parseInt(pathVariableCr);
        String uriCr = "http://localhost:8080/couponresponse/" + pathVariableCr;

        // Act
        // ... for CouponReturned (Cr)
        HttpUriRequest requestCr = new HttpGet(uriCr);
        HttpResponse httpResponseCr = TestService.executeGETRequestAndReturnHttpResponse(requestCr);
        CouponReturned[] responseObject = TestService.retrieveObjectFromHttpResponse(CouponReturned[].class, httpResponseCr);
        String stringDateRefOfCouponReturned = responseObject[0].getStringDateRef();
        // ... for Coupon_is_Consulted (Cic)
        String uriCic = "http://localhost:8080/coupon_is_consulted/" + stringDateRefOfCouponReturned;
        HttpUriRequest requestCic = new HttpGet(uriCic);
        HttpResponse httpResponseCic = TestService.executeGETRequestAndReturnHttpResponse(requestCic);
        Coupon_is_consulted[] responseObjectCic = TestService.retrieveObjectFromHttpResponse(Coupon_is_consulted[].class, httpResponseCic);
        String stringDateDeRefPathVariable = responseObjectCic[0].getStringDateRef();
        int idCouponOfCic = responseObjectCic[0].getCoupon().getId();

        // Assert
        assertTrue(
        responseObjectCic.length == 1 &&
                stringDateDeRefPathVariable.equals(stringDateRefOfCouponReturned) &&
                idCouponOfCr == idCouponOfCic
        );

    }

}
