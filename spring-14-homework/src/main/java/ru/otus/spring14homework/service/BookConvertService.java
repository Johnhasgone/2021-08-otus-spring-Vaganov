package ru.otus.spring14homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring14homework.domain.no_sql.MongoAuthor;
import ru.otus.spring14homework.domain.no_sql.MongoBook;
import ru.otus.spring14homework.domain.no_sql.MongoGenre;
import ru.otus.spring14homework.domain.sql.SqlDbBook;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookConvertService {
    private final AuthorConvertService authorConvertService;
    private final GenreConvertService genreConvertService;
    private final Set<Long> bookIdSet = new HashSet<>();

    public MongoBook convert(SqlDbBook source) {
        if (bookIdSet.contains(source.getId())) {
            return null; // to skip processed item after restart
        }

        List<MongoAuthor> mongoAuthors = source.getAuthors()
                .stream()
                .map(e -> new MongoAuthor(authorConvertService.getMongoId(e.getId()), e.getName()))
                .collect(Collectors.toList());
        List<MongoGenre> mongoGenres = source.getGenres()
                .stream()
                .map(e -> new MongoGenre(genreConvertService.getMongoId(e.getId()), e.getName()))
                .collect(Collectors.toList());
        bookIdSet.add(source.getId());
        return new MongoBook(null, source.getTitle(), mongoAuthors, mongoGenres);
    }
}
