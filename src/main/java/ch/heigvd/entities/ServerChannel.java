package ch.heigvd.entities;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.*;

@Entity
@Table(name = "serverChannel")
@PrimaryKeyJoinColumn(name = "channel_id")
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
        return "ServerChannel{" +
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
