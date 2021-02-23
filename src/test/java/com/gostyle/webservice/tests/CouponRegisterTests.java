package com.gostyle.webservice.tests;

import com.gostyle.webservice.util.TestService;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CouponRegisterTests {

    private CloseableHttpClient client;
    private HttpPost httpPost;
    private HttpResponse httpResponse;
    private int httpStatusCode;

    @BeforeEach
    void setUp() {
        client = HttpClients.createDefault();
    }

    @AfterEach
    void closeClient() throws IOException {
        client.close();
    }

    @Test
    void contextLoads() {
    }

    /*
     * Coupon_is_registered is a complex table :
     *  - Need someone to consult the coupon. At this begin the record of the coupon life :
     *      > A stringDateRef is generated (supposed a unique identifier for the consultation)
     *      > this stringDateRef
     *          * is retrieved in the Coupon_is_Consulted for front-end handling
     *          * is saved in the coupon_is_consulted table
     *  - Need someone to connect to his client space and register the coupon
     *      > Here the client (web, mobile) give the login of the person and stringDateRef
     *      > The server can then operate to identify these attributes in the 2 tables of the database
     * That is the reason in case of the below unit test we only assert the status code, not the correct filling of the table
     */


    /*
     * In this test we create (insert) a coupon_is_registered
     * To keep the database clean, and re-run the test without having to do another action,
     * we also delete the client_space just inserted
     */

    @Test
    @DisplayName("POST  PATH=\"/clientspace/coupon\" TEST return Status 200 when the couple login and stringDateRef doesn't already exist in coupon_is_registered")
    public void postRequestToRegisterACoupon_returnStatus200() throws IOException, InterruptedException {

        // Arrange
        String uri = "http://localhost:8080/clientspace/coupon";
        String uriDelete = "http://localhost:8080/coupon_is_registered";
        // ... body for request
        String login_mail = TestService.EXISTING_LOGIN_MAIL_2;
        int idClientSpace = TestService.EXISTING_LOGIN_MAIL_2_ID;
        String stringDateRef = TestService.EXISTING_STRING_DATE_REF;
        int idCoupon_is_consulted = TestService.EXISTING_STRING_DATE_REF_ID;
        String jsonRequestBody = TestService
                .retrieveJsonFromCoupon_is_RegisteredObject(login_mail, stringDateRef);

        // Act
        httpPost = new HttpPost(uri);
        httpResponse = TestService
                .executePOSTRequestAndReturnHttpResponse(httpPost, client, jsonRequestBody);
        httpStatusCode = httpResponse.getStatusLine().getStatusCode();

        // Reset database
        Thread.sleep(2000);
        HttpDelete httpDelete = new HttpDelete(
                uriDelete
                        + "/"
                        + idClientSpace
                        + "/"
                        + idCoupon_is_consulted
        );
        httpDelete.setHeader("Accept", "application/json");
        httpDelete.setHeader("Content-type", "application/json");
        client.execute(httpDelete);

        // Assert
        assertEquals(HttpStatus.SC_OK, httpStatusCode);

    }

    @Test
    @DisplayName("POST  PATH=\"/clientspace/coupon\" TEST return Status 409 when the couple login and stringDateRef already exist in coupon_is_registered")
    public void postRequestToRegisterACoupon_returnStatus409() throws IOException {

        // Arrange
        String uri = "http://localhost:8080/clientspace/coupon";
        // ... body for request
        String login_mail = TestService.MATCHING_LOGIN_WITH_STRING_DATE_REF;
        String stringDateRef = TestService.MATCHING_STRING_DATE_REF_WITH_LOGIN;
        String jsonRequestBody = TestService
                .retrieveJsonFromCoupon_is_RegisteredObject(login_mail, stringDateRef);

        // Act
        httpPost = new HttpPost(uri);
        httpResponse = TestService
                .executePOSTRequestAndReturnHttpResponse(httpPost, client, jsonRequestBody);
        httpStatusCode = httpResponse.getStatusLine().getStatusCode();

        // Assert
        assertEquals(HttpStatus.SC_CONFLICT, httpStatusCode);

    }

    @Test
    @DisplayName("POST  PATH=\"/clientspace/coupon\" TEST return Status 404 when at least one of the parameter login / stringDateRef doesn't exist in the database")
    public void postRequestToRegisterACoupon_returnStatus404() throws IOException {

        // Arrange
        String uri = "http://localhost:8080/clientspace/coupon";
        // ... body for request
        String login_mail = TestService.NOT_EXISTING_LOGIN_MAIL_LOG;
        String stringDateRef = TestService.EXISTING_STRING_DATE_REF;
        String jsonRequestBody = TestService
                .retrieveJsonFromCoupon_is_RegisteredObject(login_mail, stringDateRef);

        // Act
        httpPost = new HttpPost(uri);
        httpResponse = TestService
                .executePOSTRequestAndReturnHttpResponse(httpPost, client, jsonRequestBody);
        httpStatusCode = httpResponse.getStatusLine().getStatusCode();

        // Assert
        assertEquals(HttpStatus.SC_NOT_FOUND, httpStatusCode);

    }

    /*
     * After running the following test
     * Don't forget to insert the deleted line in the database
     * (Not automatically implemented yet)
     */
    @Test
    @DisplayName("POST  PATH=\"/clientspace/coupon/delete\" TEST return Status 200 when both parameters login / stringDateRef match in the database")
    public void postRequestToDeleteACoupon_returnStatus200() throws IOException {

        // Arrange
        String uri = "http://localhost:8080/clientspace/coupon/delete";
        // ... body for request
        String login_mail = TestService.MATCHING_LOGIN_WITH_STRING_DATE_REF_DELETED;
        String stringDateRef = TestService.MATCHING_STRING_DATE_REF_WITH_LOGIN_DELETED;
        String jsonRequestBody = TestService
                .retrieveJsonFromCoupon_is_RegisteredObject(login_mail, stringDateRef);

        // Act
        httpPost = new HttpPost(uri);
        httpResponse = TestService
                .executePOSTRequestAndReturnHttpResponse(httpPost, client, jsonRequestBody);
        httpStatusCode = httpResponse.getStatusLine().getStatusCode();

        // Assert
        assertEquals(HttpStatus.SC_OK, httpStatusCode);

    }

    @Test
    @DisplayName("POST  PATH=\"/clientspace/coupon/delete\" TEST return Status 404 when both parameters login / stringDateRef are found in the database but not associated")
    public void postRequestToDeleteACoupon_returnStatus404() throws IOException {

        // Arrange
        String uri = "http://localhost:8080/clientspace/coupon/delete";
        // ... body for request
        String login_mail = TestService.EXISTING_LOGIN_MAIL_1; // id 15
        String stringDateRef = TestService.MATCHING_STRING_DATE_REF_WITH_LOGIN; // id 16
        String jsonRequestBody = TestService
                .retrieveJsonFromCoupon_is_RegisteredObject(login_mail, stringDateRef);

        // Act
        httpPost = new HttpPost(uri);
        httpResponse = TestService
                .executePOSTRequestAndReturnHttpResponse(httpPost, client, jsonRequestBody);
        httpStatusCode = httpResponse.getStatusLine().getStatusCode();

        // Assert
        assertEquals(HttpStatus.SC_NOT_FOUND, httpStatusCode);

    }
}
