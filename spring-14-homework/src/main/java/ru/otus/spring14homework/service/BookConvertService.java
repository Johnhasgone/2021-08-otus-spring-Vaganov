package ru.otus.spring14homework.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import ru.otus.spring14homework.domain.no_sql.Author;
import ru.otus.spring14homework.domain.no_sql.Book;
import ru.otus.spring14homework.domain.no_sql.Genre;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BookConvertService implements EntityConvertService<ru.otus.spring14homework.domain.sql.Book, Book> {
    private final Map<Long, String> authorIdMap;
    private final Map<Long, String> genreIdMap;
    private final Map<Long, String> bookIdMap;

    @Override
    public Book convert(ru.otus.spring14homework.domain.sql.Book source) {
        if (bookIdMap.containsKey(source.getId())) {
            return null; // to skip processed item after restart
        }

        List<Author> noSqlAuthors = source.getAuthors()
                .stream()
                .map(e -> new Author(authorIdMap.get(e.getId()), e.getName()))
                .collect(Collectors.toList());
        List<Genre> noSqlGenres = source.getGenres()
                .stream()
                .map(e -> new Genre(genreIdMap.get(e.getId()), e.getName()))
                .collect(Collectors.toList());
        bookIdMap.put(source.getId(), new ObjectId().toHexString());
        return new Book(bookIdMap.get(source.getId()), source.getTitle(), noSqlAuthors, noSqlGenres);
    }
}
