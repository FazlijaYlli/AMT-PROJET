package ch.heigvd.entities;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "privateChannel")
@PrimaryKeyJoinColumn(name = "channel_id")
public class PrivateChannel extends Channel{

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_privateChannel", joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    private List<Account> accounts = new LinkedList<>();

    public PrivateChannel() {}

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "PrivateChannel{" +
                "users=" + accounts +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", messages=" + getMessages() +
                '}';
    }


    @Override
    public JsonObjectBuilder toJson() {
        return Json.createObjectBuilder()
                .add("id", getId())
                .add("name", getName());
    }
}
