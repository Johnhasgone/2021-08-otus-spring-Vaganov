package ru.otus.spring08homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring08homework.dao.AuthorRepository;
import ru.otus.spring08homework.dao.BookRepository;
import ru.otus.spring08homework.domain.Author;
import ru.otus.spring08homework.dto.AuthorDto;
import ru.otus.spring08homework.mapper.AuthorMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> findById(String id) {
        return Optional.ofNullable(mapper.toDto(authorRepository.findById(id).orElse(null)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> findByName(String name) {
        List<Author> authors = authorRepository.findByName(name);
        return Optional.ofNullable(authors.isEmpty() ? null : mapper.toDto(authors.get(0)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AuthorDto save(String authorName) {
        Author author = new Author(authorName);
        return mapper.toDto(authorRepository.save(author));
    }

    @Override
    @Transactional
    public void updateNameById(String id, String name) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            author.setName(name);
            authorRepository.save(author);
        }
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            throw new EmptyResultDataAccessException("автор не найден", 1);
        }
        if (!bookRepository.findByAuthorsContaining(author).isEmpty()) {
            throw new DataIntegrityViolationException("удаление отклонено - в базе имеются книги автора");
        }
        authorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(String id) {
        return authorRepository.existsById(id);
    }
}
