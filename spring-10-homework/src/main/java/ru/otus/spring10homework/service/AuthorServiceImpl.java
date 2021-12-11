package ru.otus.spring10homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring10homework.dao.AuthorRepository;
import ru.otus.spring10homework.domain.Author;
import ru.otus.spring10homework.dto.AuthorDto;
import ru.otus.spring10homework.mapper.AuthorMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> findById(Long id) {
        return Optional.ofNullable(AuthorMapper.INSTANCE.toDto(repository.findById(id).orElse(null)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> findByName(String name) {
        List<Author> authors = repository.findByName(name);
        return Optional.ofNullable(authors.isEmpty() ? null : AuthorMapper.INSTANCE.toDto(authors.get(0)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return repository.findAll().stream()
                .map(AuthorMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AuthorDto save(String authorName) {
        Author author = new Author(authorName);
        return AuthorMapper.INSTANCE.toDto(repository.save(author));
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
