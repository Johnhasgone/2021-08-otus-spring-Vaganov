package ru.otus.spring09homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring09homework.dao.AuthorRepository;
import ru.otus.spring09homework.domain.Author;
import ru.otus.spring09homework.dto.AuthorDto;
import ru.otus.spring09homework.mapper.AuthorMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> findById(Long id) {
        return Optional.ofNullable(mapper.toDto(repository.findById(id).orElse(null)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> findByName(String name) {
        List<Author> authors = repository.findByName(name);
        return Optional.ofNullable(authors.isEmpty() ? null : mapper.toDto(authors.get(0)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AuthorDto save(String authorName) {
        Author author = new Author(authorName);
        return mapper.toDto(repository.save(author));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
