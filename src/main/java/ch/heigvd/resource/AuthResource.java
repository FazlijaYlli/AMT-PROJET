package ch.heigvd.resource;

import ch.heigvd.entities.Account;
import ch.heigvd.service.API;
import ch.heigvd.service.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

@Path("")
@ApplicationScoped
public class AuthResource {

    @Inject
    UserService us;

    @Inject
    EntityManager entityManager;

    @ConfigProperty(name = "quarkus.http.auth.form.cookie-name")
    String cookieName;

    @POST
    @Path("logout")
    public Response logout() {
        final NewCookie removeCookie = new NewCookie.Builder(cookieName)
                .maxAge(0)
                .expiry(Date.from(Instant.EPOCH))
                .path("/")
                .build();
        return Response.noContent().cookie(removeCookie).build();
    }


    @GET
    @Path("me")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject me(@Context SecurityContext securityContext) {

        if (securityContext.getUserPrincipal() == null) {
            return API.createErrorResponse("Not logged in");
        }

        return API.createResponse("Me :3 WuW", us.resolve(securityContext));
    }

    //TODO: Implement register
    @POST
    @Path("register")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject register(@FormParam("username") String username, @FormParam("email") String email, @FormParam("password") String password) {

        //check if parameters are not null
        if(username == null || email == null || password == null || username.isEmpty() || email.isEmpty() || password.isEmpty()){
            return API.createErrorResponse("Missing parameters");
        }

        //check if username or email are already taken
        Query query =  entityManager.createQuery("SELECT a FROM Account a WHERE a.username = :username OR a.email = :email");
        query.setParameter("username", username);
        query.setParameter("email", email);

        if(!query.getResultList().isEmpty()){
            return API.createErrorResponse("Username or email already taken");
        }

        // TODO: Check if username/email/password are ok



        // TODO: Implement password hashing / hashing strategy

        Account account = new Account();
        account.setUsername(username);
        account.setEmail(email);
        account.setPassword(BcryptUtil.bcryptHash(password));
        account.setServers(Collections.emptyList());
        entityManager.persist(account);

        if (account.getId() == null) {
            return API.createErrorResponse("Could not create account");
        }

        return API.createResponse("Account created", account);
    }
}
