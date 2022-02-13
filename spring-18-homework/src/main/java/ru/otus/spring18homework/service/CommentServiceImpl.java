package ru.otus.spring18homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring18homework.dao.BookRepository;
import ru.otus.spring18homework.dao.CommentRepository;
import ru.otus.spring18homework.domain.Comment;
import ru.otus.spring18homework.dto.CommentDto;
import ru.otus.spring18homework.mapper.CommentMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private static final String NO_INFO = "N/A";


    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDto> findById(Long id) {
        return Optional.ofNullable(CommentMapper.INSTANCE.toDto(commentRepository.findById(id).orElse(null)));
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(fallbackMethod = "getCommentsFallback")
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
    @HystrixCommand(fallbackMethod = "saveCommentFallback")
    public CommentDto save(Long bookId, String text) {
        Comment comment = new Comment(null, text, bookRepository.findById(bookId)
                .orElseThrow(() -> new EmptyResultDataAccessException("Не найдена книга с id " + bookId, 1)));
        return CommentMapper.INSTANCE.toDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "deleteCommentFallback")
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return commentRepository.existsById(id);
    }

    private CommentDto getCommentFallback(Long id) {
        return new CommentDto(0L, NO_INFO);
    }

    private CommentDto saveCommentFallback(Long id, String text) {
        return new CommentDto(0L, NO_INFO);
    }

    private List<CommentDto> getCommentsFallback(Long id) {
        return List.of(getCommentFallback(0L));
    }

    private void deleteCommentFallback() {
        // nothing to do
    }
}
