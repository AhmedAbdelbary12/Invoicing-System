package com.invoicing.APIs;

import com.invoicing.Utils.DataManagement.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Builder {
    private static String baseURIAPI = PropertyReader.getProperty("baseURLAPI");

    public static RequestSpecification getUserRequestSpecification(Map<String, ?> body, Map<String, String> headers) {
        return new RequestSpecBuilder()
                .setBaseUri(baseURIAPI)
                .setContentType(ContentType.JSON)
                .setBody(body)
                .addHeaders(headers)
                .build();
    }
}
