package com.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

public class JsonDataReader {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String RESOURCES_PATH = "src/test/resources/";

    private static JsonNode readJsonFile(String fileName) {
        try {
            return mapper.readTree(new File(RESOURCES_PATH + fileName));
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read JSON file: " + fileName, e);
        }
    }

    public static String getSuccessMessage(String key) {
        return readJsonFile("messages.json").get("successMessages").get(key).asText();
    }

    public static String getErrorMessage(String key) {
        return readJsonFile("messages.json").get("errorMessages").get(key).asText();
    }

    public static String getValidationMessage(String key) {
        return readJsonFile("messages.json").get("validationMessages").get(key).asText();
    }

    public static Object[][] getAddProductToCartData() {
        JsonNode data = readJsonFile("testdata.json").get("addProductToCart");
        Object[][] result = new Object[data.size()][4];
        for (int i = 0; i < data.size(); i++) {
            JsonNode item = data.get(i);
            result[i][0] = item.get("productName").asText();
            result[i][1] = item.get("color").asText();
            result[i][2] = item.get("quantity").asInt();
            result[i][3] = item.get("price").asDouble();
        }
        return result;
    }
}
