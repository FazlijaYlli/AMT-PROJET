package ch.heigvd.utilities;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class ApiResponse {

    static public JsonObjectBuilder success() {
        return Json.createObjectBuilder()
                .add("status", "success");
    }

    static public JsonObjectBuilder error(String errorMessage) {
        return Json.createObjectBuilder()
                .add("status", "error")
                .add("reason", errorMessage);
    }

    static public JsonObject buildResponse(JsonObjectBuilder jsonObjectBuilder) {
        return jsonObjectBuilder.build();
    }

}
