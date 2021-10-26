package ru.otus.spring06homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring06homework.dao.CommentDao;
import ru.otus.spring06homework.domain.Comment;
import ru.otus.spring06homework.dto.BookDto;
import ru.otus.spring06homework.dto.CommentDto;
import ru.otus.spring06homework.mapper.BookMapper;
import ru.otus.spring06homework.mapper.CommentMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    private final BookService bookService;
    private final CommentMapper commentMapper;
    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDto> findById(Long id) {
        return Optional.ofNullable(commentMapper.toDto(commentDao.findById(id).orElse(null)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findByBook(BookDto bookDto) {
        return commentDao.findByBook(bookMapper.toEntity(bookDto)).stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAll() {
        return commentDao.findAll().stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto save(String text, BookDto bookDto) {
        Comment comment = new Comment(null, text, bookMapper.toEntity(bookDto));
        return commentMapper.toDto(commentDao.save(comment));
    }

    @Override
    @Transactional
    public boolean updateTextById(Long id, String text) {
        return commentDao.updateTextById(id, text) != 0;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return commentDao.deleteById(id) != 0;
    }
}
