package ru.otus.spring06homework.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring06homework.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoJpa implements BookDao {

    private final EntityManager em;

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public int updateNameById(Long id, String title) {
        Query query = em.createQuery("update Book b set b.title = :title where b.id = :id");
        query.setParameter("title", title);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findByTitle(String title) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.title = :title", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public int deleteById(Long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        return query.executeUpdate();
    }
}
