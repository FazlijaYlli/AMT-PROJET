package ch.heigvd.resource;

import ch.heigvd.entities.Account;
import ch.heigvd.utilities.ApiResponse;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.resteasy.reactive.RestForm;

@Path("users")
@ApplicationScoped
public class UserResource {

    @Inject
    EntityManager entityManager;

    //TODO: remove this
    @GET
    @Path("me")
    @Authenticated()
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject me(@Context SecurityContext securityContext) {
        return ApiResponse.buildResponse(ApiResponse.success().add("user", ((Account)securityContext.getUserPrincipal()).toJsonObjectBuilder()));
    }

    //TODO: Implement register
    @POST
    @Path("login")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject register(@FormParam("username") String username, @FormParam("email") String email, @FormParam("password") String password) {

        // TODO: Check if username/email is already taken

        // TODO: Check if username/email/password are ok

        // TODO: Implement password hashing / hashing strategy

        Account account = new Account();
        account.setUsername(username);
        account.setEmail(email);
        account.setPassword(password);
        entityManager.persist(account);

        if (account.getId() == null) {
            return ApiResponse.buildResponse(ApiResponse.error("User not created"));
        }
        return ApiResponse.buildResponse(ApiResponse.success().add("user", account.toJsonObjectBuilder()));
    }

    //TODO: Implement login
    @POST
    @Path("register")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject login(@FormParam("username") String username, @FormParam("password") String password) {

        // TODO: Check if username exists

        // TODO : Response with success token or error
        return JsonObject.EMPTY_JSON_OBJECT;

    }


    // TODO get by username also
    @GET
    @Authenticated
    @Path("{id:\\d+}")
    public JsonObject get(@PathParam("id") int id) {
        System.out.println("id: " + id);
        Account account = entityManager
                .createQuery("SELECT Account FROM Account WHERE id = :id", Account.class)
                .setParameter("id", id).getResultStream().findFirst().orElse(null);

        if (account == null) {
            return ApiResponse.buildResponse(ApiResponse.error("errrrrrrrror")/*TODO*/);
        }

        return ApiResponse.buildResponse(ApiResponse.success().add("user", account.toJsonObjectBuilder()));
    }
}
