package com.gostyle.webservice.tests;

import com.gostyle.webservice.dto.CustomResponseBody;
import com.gostyle.webservice.util.TestService;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
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
class ClientSpaceManagementTests {

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

    @Test
    @DisplayName("POST  PATH=\"/client_space/log\" TEST Return Status 200 when login and password match a client space")
    public void postRequestToLogIn_returnStatus200() throws IOException {

        // Arrange
        String uri = "http://localhost:8080/client_space/log";
        // ... body for request
        String login_mail = TestService.EXISTING_LOGIN_MAIL_1;
        String password = TestService.MATCHING_PASSWORD_LOGIN_1;
        String jsonRequestBody = TestService
                                    .retrieveJsonFromClient_SpaceObject(login_mail, password);

        // Act
        httpPost = new HttpPost(uri);
        httpResponse = TestService
                            .executePOSTRequestAndReturnHttpResponse(httpPost, client, jsonRequestBody);
        httpStatusCode = httpResponse.getStatusLine().getStatusCode();

        // Assert
        assertEquals(HttpStatus.SC_OK, httpStatusCode);
    }

    @Test
    @DisplayName("POST  PATH=\"/client_space/log\" TEST Return Status 400 when login exists but password does not match")
    public void postRequestToLogIn_returnStatus400_IncorrectPassword() throws IOException {

        // Arrange
        String uri = "http://localhost:8080/client_space/log";
        // ... body for request
        String login_mail = TestService.EXISTING_LOGIN_MAIL_1;
        String password = TestService.NOT_MATCHING_PASSWORD;
        String jsonRequestBody = TestService
                                    .retrieveJsonFromClient_SpaceObject(login_mail, password);

        // Act
        httpPost = new HttpPost(uri);
        httpResponse = TestService
                            .executePOSTRequestAndReturnHttpResponse(httpPost, client, jsonRequestBody);
        httpStatusCode = httpResponse.getStatusLine().getStatusCode();

        // Assert
        assertEquals(HttpStatus.SC_BAD_REQUEST, httpStatusCode);

    }

    @Test
    @DisplayName("POST  PATH=\"/client_space/log\" TEST Return Status 404 when login doesn't exist")
    public void postRequestToLogIn_returnStatus404_LoginDoesNotExist() throws IOException {

        // Arrange
        String uri = "http://localhost:8080/client_space/log";
        // ... body for request
        String login_mail = TestService.NOT_EXISTING_LOGIN_MAIL_LOG;
        String password = TestService.NOT_MATCHING_PASSWORD;
        String jsonRequestBody = TestService
                                    .retrieveJsonFromClient_SpaceObject(login_mail, password);

        // Act
        httpPost = new HttpPost(uri);
        httpResponse = TestService
                            .executePOSTRequestAndReturnHttpResponse(httpPost, client, jsonRequestBody);
        httpStatusCode = httpResponse.getStatusLine().getStatusCode();

        // Assert
        assertEquals(HttpStatus.SC_NOT_FOUND, httpStatusCode);

    }

    /*
     * In this test we create (insert) a client_space
     * To be able to re-run the test without having to do another action,
     * we also delete the client_space just inserted
     * (if re-run without deleting it, then the result will change because it cannot have duplicates in the table)
     */

    @Test
    @DisplayName("POST  PATH=\"/client_space/create\" TEST Return Status 200 when login doesn't already exist")
    public void postRequestToCreateClientSpace_returnStatus200 () throws IOException, InterruptedException {

        // Arrange
        String uri = "http://localhost:8080/client_space/create";
        String uriDelete = "http://localhost:8080/client_space/";
        // ... body for request
        String login_mail = TestService.NOT_EXISTING_LOGIN_MAIL_CREATE;
        String password = TestService.CREATE_PASSWORD;
        String jsonRequestBody = TestService
                .retrieveJsonFromClient_SpaceObject(login_mail, password);

        // Act
        httpPost = new HttpPost(uri);
        httpResponse = TestService
                .executePOSTRequestAndReturnHttpResponse(httpPost, client, jsonRequestBody);
        CustomResponseBody responseObject = TestService.retrieveObjectFromHttpResponse(CustomResponseBody.class, httpResponse);

        // Reset database
        Thread.sleep(2000);
        HttpDelete httpDelete = new HttpDelete(uriDelete + login_mail);
        httpDelete.setHeader("Accept", "application/json");
        httpDelete.setHeader("Content-type", "application/json");
        client.execute(httpDelete);

        // Assert
        assertEquals( 200, responseObject.getStatus());

    }

    @Test
    @DisplayName("POST  PATH=\"/client_space/create\" TEST Return Status 409 when login already exist")
    public void postRequestToCreateClientSpace_returnStatus409() throws IOException {

        // Arrange
        String uri = "http://localhost:8080/client_space/create";
        // ... body for request
        String login_mail = TestService.EXISTING_LOGIN_MAIL_1;
        String password = TestService.NOT_MATCHING_PASSWORD;
        String jsonRequestBody = TestService
                .retrieveJsonFromClient_SpaceObject(login_mail, password);

        // Act
        httpPost = new HttpPost(uri);
        httpResponse = TestService
                .executePOSTRequestAndReturnHttpResponse(httpPost, client, jsonRequestBody);
        httpStatusCode = httpResponse.getStatusLine().getStatusCode();

        // Assert
        assertEquals(httpStatusCode, HttpStatus.SC_CONFLICT);

    }

}
