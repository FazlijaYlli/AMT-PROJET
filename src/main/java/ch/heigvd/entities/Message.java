package ch.heigvd.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "message")
public class Message {

    @Column(name = "id")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Account author;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "message")
    private List<Attachment> attachments = new ArrayList<>();

    public Message() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", author=" + author +
                ", channel=" + channel +
                ", attachments=" + attachments +
                '}';
    }
}
