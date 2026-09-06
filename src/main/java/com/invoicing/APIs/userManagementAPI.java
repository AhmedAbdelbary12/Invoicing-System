package com.invoicing.APIs;

import com.invoicing.Utils.DataManagement.PropertyReader;
import com.invoicing.Utils.Logs.LogsManager;
import com.invoicing.Validations.SoftAssertion;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userManagementAPI {
    // End Points
    private static final String login_EndPoint = "/api/auth/login";
    private static final String createRole_EndPoint = "/api/roles";
    private static final String createOrg_EndPoint = "/api/tenants";
    private static String token;
    SoftAssertion softAssertion;
    RequestSpecification requestSpecification;
    Response response;

    // API Methods

    public userManagementAPI() {
        requestSpecification = RestAssured.given();
        softAssertion = new SoftAssertion();
    }

    public static String getToken() {
        return token;
    }

    @Step("Create Role using API EndPoint")
    public userManagementAPI createRoleAPI(String roleName) {
        List<Map<String, Object>> permissions = new ArrayList<>();
        permissions.add(Map.of(
                "id", "d060d2e4-72f9-404e-5abd-08deed6398de",
                "isSelected", true));
        permissions.add(Map.of(
                "id", "7c93ff20-983e-4b17-5abe-08deed6398de",
                "isSelected", true));
        permissions.add(Map.of(
                "id", "b1ba49f5-86b5-4326-5abf-08deed6398de",
                "isSelected", true));

        Map<String, Object> body = new HashMap<>();
        body.put("name", roleName);
        body.put("permissions", permissions);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("accept-Encoding", "gzip, deflate");
        headers.put("connection", "keep-alive");
        headers.put("Authorization", "Bearer " + getToken());

        headers.put("Host", "10.140.105.111:30001");
        headers.put("Origin", "http://10.140.105.111:30002");
        headers.put("referer", "http://10.140.105.111:30002/");
        headers.put("X-Tenant-Slug", "adb");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/150.0.0.0 Safari/537.36 Edg/150.0.0.0");

        response = requestSpecification
                .spec(Builder.getUserRequestSpecification(body, headers))
                .post(createRole_EndPoint);

        LogsManager.info(response.asPrettyString());
        return this;
    }

    //Request Parameters: name, email, password, title (for example: Mr, Mrs, Miss), birth_date, birth_month, birth_year, firstname, lastname, company, address1, address2, country, zipcode, state, city, mobile_number
    @Step("login using API EndPoint")
    public userManagementAPI login(String email, String password) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", email);
        formParams.put("password", password);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("accept-encoding", "gzip, deflate");
        headers.put("connection", "keep-alive");
        headers.put("Host", "10.140.105.111:30001");
        headers.put("X-Tenant-Id", "a98a39ed-2070-472f-3238-08dec24d8998");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/150.0.0.0 Safari/537.36 Edg/150.0.0.0");

        response = requestSpecification
                .spec(Builder.getUserRequestSpecification(formParams, headers))
                .post(login_EndPoint);

        token = response.jsonPath().getString("accessToken");

        LogsManager.info(response.asPrettyString());
        return this;
    }

    @Step("Organisation Information is Created Successfully Through API Level")
    public userManagementAPI createOrg(String DisplayName, String Slug, String MobileNumber, String LandlineNumber,
                                       String ContactEmail, String ContactAddress, String FirstDayOfWeek, String DateFormat,
                                       String SelectedPaymentMethodTypes, File Logo) {


        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data");
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("accept-Encoding", "gzip, deflate");
        headers.put("connection", "keep-alive");
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Host", "10.140.105.111:30001");
        headers.put("Origin", "http://10.140.105.111:30002");
        headers.put("referer", "http://10.140.105.111:30002/");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/150.0.0.0 Safari/537.36 Edg/150.0.0.0");


        response = RestAssured.given()
                .baseUri(PropertyReader.getProperty("baseURLAPI"))
                .headers(headers)
                // ✅ multipart fields
                .multiPart("DisplayName", DisplayName)
                .multiPart("Slug", Slug)
                .multiPart("MobileNumber", MobileNumber)
                .multiPart("LandlineNumber", LandlineNumber)
                .multiPart("ContactEmail", ContactEmail)
                .multiPart("ContactAddress", ContactAddress)
                .multiPart("FirstDayOfWeek", FirstDayOfWeek)
                .multiPart("DateFormat", DateFormat)
                .multiPart("SelectedPaymentMethodTypes", SelectedPaymentMethodTypes)
                .multiPart("Logo", Logo, "image/png")
                .post(createOrg_EndPoint);

        LogsManager.debug("DisplayName " + DisplayName);
        LogsManager.debug("Slug " + Slug);
        LogsManager.debug("MobileNumber " + MobileNumber);
        LogsManager.debug("LandlineNumber " + LandlineNumber);
        LogsManager.debug("ContactEmail " + ContactEmail);
        LogsManager.debug("ContactAddress " + ContactAddress);
        LogsManager.debug("FirstDayOfWeek " + FirstDayOfWeek);
        LogsManager.debug("DateFormat " + DateFormat);
        LogsManager.debug("SelectedPaymentMethodTypes " + SelectedPaymentMethodTypes);


        token = response.jsonPath().getString("accessToken");
        LogsManager.info(response.asPrettyString());
        return this;
    }


    // Validation
    // I need to check on response code = 200

    @Step("Validate that Account is created")
    public userManagementAPI validateUserIsLoggedIn() {
        softAssertion.checkStatusCode(response.getStatusCode(), 200, "Expected status code is 200");
        return this;
    }

    @Step("Validate that Organisation is created")
    public userManagementAPI validateOrgIsCreated() {
        softAssertion.checkStatusCode(response.getStatusCode(), 201, "Expected status code is 200");
        return this;
    }

    @Step("Validate that Role is created")
    public userManagementAPI validateRoleIsCreated() {
        softAssertion.checkStatusCode(response.getStatusCode(), 201, "Expected status code is 200");
        return this;
    }


}
