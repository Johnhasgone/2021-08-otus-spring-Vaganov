package ru.otus.spring18homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring18homework.dao.AuthorRepository;
import ru.otus.spring18homework.domain.Author;
import ru.otus.spring18homework.dto.AuthorDto;
import ru.otus.spring18homework.mapper.AuthorMapper;

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
    @HystrixCommand(fallbackMethod = "getAuthorFallback")
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
    @HystrixCommand(fallbackMethod = "saveAuthorFallback")
    public AuthorDto save(String authorName) {
        Author author = new Author(authorName);
        return AuthorMapper.INSTANCE.toDto(repository.save(author));
    }

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "deleteAuthorFallback")
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    private Optional<AuthorDto> getAuthorFallback(String name) {
        return Optional.of(new AuthorDto(0L, "N/A"));
    }

    private AuthorDto saveAuthorFallback(String name) {
        return new AuthorDto(0L, "N/A");
    }

    private void deleteAuthorFallback(Long id) {
        // nothing to do
    }
}
