package ru.otus.spring16homework.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.otus.spring16homework.domain.Author;
import ru.otus.spring16homework.domain.Book;
import ru.otus.spring16homework.domain.Genre;
import ru.otus.spring16homework.dto.BookDto;

import java.util.List;
import java.util.stream.Collectors;

@org.mapstruct.Mapper
public interface BookMapper extends Mapper<Book, BookDto>{

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Override
    @Mapping(target = "id")
    @Mapping(target = "title")
    @Mapping(target = "authors", qualifiedByName = "authorsToString")
    @Mapping(target = "genres", qualifiedByName = "genresToString")
    BookDto toDto(Book entity);

    @Named("authorsToString")
    default String conversionAuthor(List<Author> authors) {
        return authors.stream()
                .map(Author::getName)
                .collect(Collectors.joining(", "));
    }

    @Named("genresToString")
    default String conversionGenre(List<Genre> genres) {
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.joining(", "));
    }

    @Override
    default Book toEntity(BookDto dto) {
        throw new UnsupportedOperationException();
    }
}
