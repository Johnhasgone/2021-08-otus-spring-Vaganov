package ru.otus.spring06homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring06homework.dao.AuthorDao;
import ru.otus.spring06homework.domain.Author;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao dao) {
        this.authorDao = dao;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorDao.findById(id);
    }

    @Override
    public Optional<Author> findByName(String name) {
        List<Author> authors = authorDao.findByName(name);
        return Optional.ofNullable(authors.isEmpty() ? null : authors.get(0));
    }

    @Override
    public List<Author> findAll() {
        return authorDao.findAll();
    }

    @Override
    public Author save(Author author) {
        return authorDao.save(author);
    }

    public boolean updateNameById(Long id, String name) {
        return authorDao.updateNameById(id, name) != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return authorDao.deleteById(id) != 0;
    }
}
