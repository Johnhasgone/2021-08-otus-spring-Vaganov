package ru.otus.spring06homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring06homework.dao.CommentDao;
import ru.otus.spring06homework.domain.Book;
import ru.otus.spring06homework.domain.Comment;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    @Override
    public Optional<Comment> findById(Long id) {
        return commentDao.findById(id);
    }

    @Override
    public List<Comment> findByBook(Book book) {
        return commentDao.findByBook(book);
    }

    @Override
    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    @Override
    public Comment save(Comment comment) {
        return commentDao.save(comment);
    }

    @Override
    public boolean updateTextById(Long id, String text) {
        return commentDao.updateTextById(id, text) != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return commentDao.deleteById(id) != 0;
    }
}
