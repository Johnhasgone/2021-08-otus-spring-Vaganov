package ru.otus.spring07homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring07homework.dao.AuthorDao;
import ru.otus.spring07homework.domain.Author;
import ru.otus.spring07homework.dto.AuthorDto;
import ru.otus.spring07homework.mapper.AuthorMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final AuthorMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> findById(Long id) {
        return Optional.ofNullable(mapper.toDto(authorDao.findById(id).orElse(null)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> findByName(String name) {
        List<Author> authors = authorDao.findByName(name);
        return Optional.ofNullable(authors.isEmpty() ? null : mapper.toDto(authors.get(0)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return authorDao.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AuthorDto save(String authorName) {
        Author author = new Author(authorName);
        return mapper.toDto(authorDao.save(author));
    }

    @Override
    @Transactional
    public boolean updateNameById(Long id, String name) {
        return authorDao.updateNameById(id, name) != 0;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return authorDao.deleteById(id) != 0;
    }
}
