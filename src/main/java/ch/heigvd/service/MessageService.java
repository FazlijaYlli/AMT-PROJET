package ch.heigvd.service;

import ch.heigvd.entities.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
public class MessageService {

    @Inject
    EntityManager em;

    public Message create(Channel channel, Account author, String text) {
        Message message = new Message();
        message.setText(text);
        message.setChannel(channel);
        message.setAuthor(author);
        message.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));

        em.persist(message);

        return message;
    }
}
