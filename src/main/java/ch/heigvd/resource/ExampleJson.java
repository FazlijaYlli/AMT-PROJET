package ch.heigvd.resource;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("user")
public class ExampleJson {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject get(@QueryParam("username") String username, @QueryParam("password") String password) {

        if(username == null || password == null || username.isEmpty() || password.isEmpty()){
            return Json.createObjectBuilder()
                    .add("status", "error")
                    .build();
        }

        JsonObject user = Json.createObjectBuilder()
                .add("status", "success")
                .add("user", Json.createObjectBuilder()
                        .add("username", username)
                        .add("password", password))
                .build();

        return user;
    }

}
