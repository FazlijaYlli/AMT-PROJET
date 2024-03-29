package ch.heigvd.entities;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category implements Jsonable {
    
    @Column(name = "id", columnDefinition = "serial")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "server_id")
    private Server server;

    @OneToMany(mappedBy = "category")
    private List<ServerChannel> channelsInCategory = new ArrayList<>();

    public Category() {
    }

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

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public List<ServerChannel> getChannelsInCategory() {
        return channelsInCategory;
    }

    public void setChannelsInCategory(List<ServerChannel> channelsInCategory) {
        this.channelsInCategory = channelsInCategory;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", server=" + server +
                ", channelsInCategory=" + channelsInCategory +
                '}';
    }

    @Override
    public JsonObjectBuilder toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("name", name);
    }
}
