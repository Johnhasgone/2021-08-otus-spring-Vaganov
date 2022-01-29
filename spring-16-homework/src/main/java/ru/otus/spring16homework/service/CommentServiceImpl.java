package ru.otus.spring16homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring16homework.dao.BookRepository;
import ru.otus.spring16homework.dao.CommentRepository;
import ru.otus.spring16homework.domain.Comment;
import ru.otus.spring16homework.dto.CommentDto;
import ru.otus.spring16homework.mapper.CommentMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDto> findById(Long id) {
        return Optional.ofNullable(CommentMapper.INSTANCE.toDto(commentRepository.findById(id).orElse(null)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findByBookId(Long bookId) {
        return commentRepository.findByBookId(bookId).stream()
                .map(CommentMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAll() {
        return commentRepository.findAll().stream()
                .map(CommentMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto save(Long bookId, String text) {
        Comment comment = new Comment(null, text, bookRepository.findById(bookId)
                .orElseThrow(() -> new EmptyResultDataAccessException("Не найдена книга с id " + bookId, 1)));
        return CommentMapper.INSTANCE.toDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return commentRepository.existsById(id);
    }
}
