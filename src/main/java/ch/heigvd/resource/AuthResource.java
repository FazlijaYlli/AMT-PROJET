package ch.heigvd.resource;

import ch.heigvd.entities.Account;
import ch.heigvd.utilities.ApiResponse;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Instant;
import java.util.Date;

@Path("")
@ApplicationScoped
public class AuthResource {

    @Inject
    EntityManager entityManager;

    @ConfigProperty(name = "quarkus.http.auth.form.cookie-name")
    String cookieName;

    @POST
    @Path("logout")
    @Authenticated
    public Response logout() {
        final NewCookie removeCookie = new NewCookie.Builder(cookieName)
                .maxAge(0)
                .expiry(Date.from(Instant.EPOCH))
                .path("/")
                .build();
        return Response.noContent().cookie(removeCookie).build();
    }

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
    @Path("register")
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
}
