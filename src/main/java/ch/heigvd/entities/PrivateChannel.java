package ch.heigvd.entities;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "privateChannel")
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
}
