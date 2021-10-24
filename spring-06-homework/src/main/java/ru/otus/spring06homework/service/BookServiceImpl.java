package ru.otus.spring06homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring06homework.dao.BookDao;
import ru.otus.spring06homework.domain.Book;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(Long id) {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookDao.findByTitle(title);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public Book save(Book book) {
        return bookDao.save(book);
    }

    @Override
    public boolean updateNameById(Long id, String title) {
        return bookDao.updateNameById(id, title) != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return bookDao.deleteById(id) != 0;
    }
}
