package com.gostyle.webservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gostyle.webservice.entities.Client_space;
import com.gostyle.webservice.entities.Coupon_is_consulted;
import com.gostyle.webservice.entities.Coupon_is_registered;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import org.apache.http.impl.client.HttpClientBuilder;

public class TestService {

    /*
     * DATABASE TABLE = "client_space"
     * Constants for ClientSpaceManagementTests & CouponRegisterTests
     */
    // ... Logins
    public static final String  EXISTING_LOGIN_MAIL_1          = "mail1";
    // Constraint for tests: EXISTING_LOGIN_MAIL_2_ID must not be associated with EXISTING_STRING_DATE_REF_ID in coupon_is_registered
    public static final String  EXISTING_LOGIN_MAIL_2          = "mail2";
    public static final int     EXISTING_LOGIN_MAIL_2_ID       = 2;
    public static final String  NOT_EXISTING_LOGIN_MAIL_LOG    = "tryToLog@notExistingLogin";
    public static final String  NOT_EXISTING_LOGIN_MAIL_CREATE = "tryToCreate@notExistingLogin";
    // ... Passwords
    public static final String  MATCHING_PASSWORD_LOGIN_1   = "pass1";
    public static final String  NOT_MATCHING_PASSWORD       = "NotMatchingPassword";
    public static final String  CREATE_PASSWORD             = "MyPasswordForTest";

    /*
     * DATABASE TABLE = "coupon"
     * Constants for CouponConsultTests
     */
    // ... Coupons id
    public static final String EXISTING_COUPON_ID_1          = "3";
    public static final String EXISTING_COUPON_ID_2          = "6";
    public static final String NOT_EXISTING_COUPON_ID        = "120";
    public static final String CITY_WITH_COUPONS_IN_DATABASE = "all";

    /*
     * DATABASE TABLE = "coupon_is_consulted"
     * Constants for CouponRegisterTests
     */
    // Constraint for tests: EXISTING_LOGIN_MAIL_2_ID must not be associated with EXISTING_STRING_DATE_REF_ID in coupon_is_registered
    public static final String  EXISTING_STRING_DATE_REF    = "19-03-2021_18:33:29";
    public static final int     EXISTING_STRING_DATE_REF_ID = 10;

    /*
     * DATABASE TABLE = "coupon_is_registered"
     * Constants for CouponRegisterTests
     */
    // client_space_id = 12 & coupon_is_consulted_id = 18
    public static final String  MATCHING_LOGIN_WITH_STRING_DATE_REF         = "mail12";
    public static final String  MATCHING_STRING_DATE_REF_WITH_LOGIN         = "27-03-2021_18:33:29";
    // Constants for Delete use => To re-insert manually after running the test which delete them
    // client_space_id = 4 & coupon_is_consulted_id = 10 (= coupon_is_registered with id = 4)
    public static final String  MATCHING_LOGIN_WITH_STRING_DATE_REF_DELETED = "mail4";
    public static final String  MATCHING_STRING_DATE_REF_WITH_LOGIN_DELETED = "19-03-2021_18:33:29";


    public static HttpResponse executeGETRequestAndReturnHttpResponse(HttpUriRequest request)
            throws IOException
    {
        return HttpClientBuilder
                .create()
                .build()
                .execute(request);
    }

    public static CloseableHttpResponse executePOSTRequestAndReturnHttpResponse(
            HttpPost httpPost,
            CloseableHttpClient client,
            String jsonRequestBody
    ) throws IOException
    {
        StringEntity entity = new StringEntity(jsonRequestBody);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        return client.execute(httpPost);
    }

    public static <T> T retrieveObjectFromHttpResponse(
        Class<T> clazz,
        HttpResponse httpResponse
    ) throws IOException
    {

        String jsonFromResponse = EntityUtils.toString(httpResponse.getEntity());
        ObjectMapper mapper = new ObjectMapper()
                                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper.readValue(jsonFromResponse, clazz);

    }

    public static String retrieveJsonFromClient_SpaceObject (String login_mail, String password)
            throws JsonProcessingException
    {

        Client_space client_space = new Client_space();

        // Affect values
        client_space.setLogin_mail(login_mail);
        client_space.setPassword(password);

        // Java object to JSON string
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(client_space);
    }

    public static String retrieveJsonFromCoupon_is_RegisteredObject (String login_mail, String stringDateRef)
            throws JsonProcessingException
    {

        // Create the 2 Objects which are included in Coupon_is_Registered and required in JSON request body
        Client_space cs = new Client_space();
        Coupon_is_consulted cic = new Coupon_is_consulted();

        // Affect values to the 2 Objects
        cs.setLogin_mail(login_mail);
        cic.setStringDateRef(stringDateRef);

        // Affect these 2 Objects in Coupon_is_Registered
        Coupon_is_registered coupon_is_registered = new Coupon_is_registered();
        coupon_is_registered.setClient_space(cs);
        coupon_is_registered.setCoupon_is_consulted(cic);

        // Java object to JSON string
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(coupon_is_registered);
    }
}

