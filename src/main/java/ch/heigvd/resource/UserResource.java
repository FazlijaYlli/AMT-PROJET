package ch.heigvd.resource;

import ch.heigvd.entities.Account;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonValue;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.resteasy.reactive.RestForm;

@Path("users")
public class UserResource {

    @Inject
    EntityManager entityManager;

    @GET
    @Path("me")
    @Authenticated()
    @Produces(MediaType.TEXT_PLAIN)
    public String me(@Context SecurityContext securityContext) {
        return securityContext.getUserPrincipal().getName();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String create(@FormParam("username") String username, @FormParam("email") String email, @FormParam("password") String password) {
        var account = new Account();
        account.setUsername(username);
        account.setEmail(email);
        account.setPassword(password);
        entityManager.persist(account);

        return account;
    }

    @GET
    @Path("{id:\\d+}")
    public String get(@PathParam("id") int id) {
        var account = entityManager
                .createQuery("SELECT Account FROM Account WHERE id = :id", Account.class)
                .setParameter("id", id)
                .getSingleResult();

        return account;
    }
}
