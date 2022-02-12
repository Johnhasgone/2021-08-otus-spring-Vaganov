package ru.otus.spring18homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring18homework.dao.BookRepository;
import ru.otus.spring18homework.domain.Author;
import ru.otus.spring18homework.domain.Book;
import ru.otus.spring18homework.domain.Genre;
import ru.otus.spring18homework.dto.BookDto;
import ru.otus.spring18homework.mapper.AuthorMapper;
import ru.otus.spring18homework.mapper.BookMapper;
import ru.otus.spring18homework.mapper.GenreMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final MutableAclService mutableAclService;
    private static final String NO_INFO = "N/A";

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(fallbackMethod = "getBookFallback")
    public BookDto findById(Long id) {
        return BookMapper.INSTANCE.toDto(
                Optional.ofNullable(bookRepository.findBookById(id))
                        .orElseThrow(() -> new EmptyResultDataAccessException("Не найдена книга с id " + id, 1)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findByTitle(String title) {
        return bookRepository.findByTitle(title).stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(fallbackMethod = "getBooksFallback")
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "getBookFallback")
    public BookDto save(BookDto bookDto) {
        List<Author> authors = getAuthors(bookDto.getAuthors());
        List<Genre> genres = getGenres(bookDto.getGenres());
        Book book = new Book(bookDto.getId(), bookDto.getTitle(), authors, genres);
        book = bookRepository.save(book);

        if (bookDto.getId() == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final Sid owner = new PrincipalSid(authentication);
            ObjectIdentity oid = new ObjectIdentityImpl(book.getClass(), book.getId());

            final Sid admin = new GrantedAuthoritySid("ROLE_ADMIN");

            MutableAcl acl = mutableAclService.createAcl(oid);
            acl.setOwner(owner);
            acl.insertAce(acl.getEntries().size(), BasePermission.READ, admin, true);
            mutableAclService.updateAcl(acl);
        }

        return BookMapper.INSTANCE.toDto(book);
    }


    private List<Genre> getGenres(String genres) {
        if (genres == null) {
            return List.of();
        }
        List<String> genreNames = Arrays.stream(genres.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        List<Genre> genreList = new ArrayList<>();
        for (String genreName : genreNames) {
            Genre genre = GenreMapper.INSTANCE.toEntity(
                    genreService.findByName(genreName)
                            .orElseGet(() -> genreService.save(genreName))
            );
            genreList.add(genre);
        }
        return genreList;
    }

    private List<Author> getAuthors(String authors) {
        if (authors == null) {
            return List.of();
        }
        List<String> authorNames = Arrays.stream(authors.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        List<Author> authorList = new ArrayList<>();
        for (String authorName : authorNames) {
            Author author = AuthorMapper.INSTANCE.toEntity(
                    authorService.findByName(authorName)
                            .orElseGet(() -> authorService.save(authorName))
            );
            authorList.add(author);
        }
        return authorList;
    }

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "deleteBookFallback")
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }

    private BookDto getBookFallback() {
        return new BookDto(0L, NO_INFO, NO_INFO, NO_INFO);
    }

    private List<BookDto> getBooksFallback() {
        return List.of(getBookFallback());
    }

    private void deleteBookFallback() {
        // nothing to do
    }
}
