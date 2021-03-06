package ru.otus.spring05homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring05homework.dao.BookDao;
import ru.otus.spring05homework.domain.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao dao) {
        this.bookDao = dao;
    }

    @Override
    public Book getById(Long id) {
        return bookDao.getById(id);
    }

    @Override
    public Book getByName(String name) {
        return bookDao.getByTitle(name);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Long create(Book book) {
        return bookDao.insert(book);
    }

    @Override
    public boolean update(Book book) {
        return bookDao.update(book);
    }

    @Override
    public boolean deleteById(Long id) {
        return bookDao.deleteById(id);
    }
}
