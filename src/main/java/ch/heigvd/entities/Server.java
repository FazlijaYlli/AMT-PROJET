package ch.heigvd.entities;


import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "server")
public class Server implements Jsonable{

    @Column(name = "id")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account owner;

    @OneToMany(mappedBy = "server")
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_server", joinColumns = @JoinColumn(name = "server_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    private List<Account> members = new ArrayList<>();

    public Server() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Account> getMembers() {
        return members;
    }

    public void setMembers(List<Account> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Server{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", categories=" + categories +
                ", members=" + members +
                '}';
    }

    @Override
    public JsonObjectBuilder toJson() {
        return Json.createObjectBuilder()
                .add("id", getId())
                .add("name", getName())
                .add("owner", getOwner().getId());
    }
}
