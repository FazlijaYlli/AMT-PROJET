package ch.heigvd.service;

import ch.heigvd.entities.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
public class CategoryService {

    @Inject
    EntityManager em;

    public Category create(Server server, String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        category.setServer(server);

        em.persist(category);

        return category;
    }

    public Category get(Long categoryId) {
        TypedQuery<Category> query = em.createQuery("""
                SELECT category
                FROM Category category
                WHERE category.id = :id
        """, Category.class).setParameter("id", categoryId);

        return query.getSingleResult();
    }
}
