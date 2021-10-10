package ru.otus.spring05homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring05homework.dao.AuthorDao;
import ru.otus.spring05homework.domain.Author;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao dao) {
        this.authorDao = dao;
    }

    @Override
    public Author getById(Long id) {
        return authorDao.getById(id);
    }

    @Override
    public Author getByName(String name) {
        return authorDao.getByName(name);
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public Long create(Author author) {
        return authorDao.insert(author);
    }

    @Override
    public void update(Author author) {
        authorDao.update(author);
    }

    @Override
    public void deleteById(Long id) {
        authorDao.deleteById(id);
    }
}
