package ch.heigvd.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "attachment")
public class Attachment {

    @Column(name = "id")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    public Attachment() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", message=" + message +
                '}';
    }
}
