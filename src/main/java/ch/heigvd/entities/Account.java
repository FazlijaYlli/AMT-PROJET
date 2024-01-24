package ch.heigvd.entities;

import io.quarkus.security.jpa.*;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.*;
import jakarta.xml.bind.DatatypeConverter;
import org.wildfly.security.password.interfaces.SimpleDigestPassword;

import java.util.*;

@Entity
@Table(name = "account")
@UserDefinition
public class Account implements Jsonable {
    @Column(name = "id")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    @Basic
    @Username
    private String email;

    @Column(name = "password")
    @Basic
    @Password(value = PasswordType.CLEAR/*value = PasswordType.CUSTOM, provider = CustomPasswordProvider.class*/)
    private String password;

    @OneToMany(mappedBy = "author")
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Server> serversOwner = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_server", joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "server_id"))
    private List<Server> servers = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_privateChannel", joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id"))
    private List<PrivateChannel> directMessages = new ArrayList<>();

    @Roles
    private String role = "user";

    public Account() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public List<PrivateChannel> getDirectMessages() {
        return directMessages;
    }

    public void setDirectMessages(List<PrivateChannel> directMessages) {
        this.directMessages = directMessages;
    }

    public List<Server> getServersOwner() {
        return serversOwner;
    }

    public void setServersOwner(List<Server> serversOwner) {
        this.serversOwner = serversOwner;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", messages=" + messages +
                ", servers=" + servers +
                ", directMessages=" + directMessages +
                '}';
    }

    @Override
    public JsonObjectBuilder toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("username", username)
                .add("email", email)
                .add("password", password);
    }

    static class CustomPasswordProvider implements PasswordProvider {

        public CustomPasswordProvider() {

        }

        @Override
        public org.wildfly.security.password.Password getPassword(String pass) {
            byte[] digest = pass.getBytes();

            return SimpleDigestPassword.createRaw(SimpleDigestPassword.ALGORITHM_SIMPLE_DIGEST_SHA_1, digest);
        }
    }
}
