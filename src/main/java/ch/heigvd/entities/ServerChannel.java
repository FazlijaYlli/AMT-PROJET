package ch.heigvd.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "serverChannel")
public class ServerChannel extends Channel{

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public ServerChannel() {}

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "PrivateChannel{" +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", messages=" + getMessages() +
                '}';
    }
}
