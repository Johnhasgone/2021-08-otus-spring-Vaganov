package ru.otus.spring07homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring07homework.dao.CommentRepository;
import ru.otus.spring07homework.domain.Comment;
import ru.otus.spring07homework.dto.BookDto;
import ru.otus.spring07homework.dto.CommentDto;
import ru.otus.spring07homework.mapper.BookMapper;
import ru.otus.spring07homework.mapper.CommentMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDto> findById(Long id) {
        return Optional.ofNullable(commentMapper.toDto(commentRepository.findById(id).orElse(null)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findByBook(BookDto bookDto) {
        return commentRepository.findByBook(bookMapper.toEntity(bookDto)).stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAll() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto save(String text, BookDto bookDto) {
        Comment comment = new Comment(null, text, bookMapper.toEntity(bookDto));
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void updateTextById(Long id, String text) {
        commentRepository.updateTextById(id, text);
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
