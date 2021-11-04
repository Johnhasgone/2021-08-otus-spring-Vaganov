package ru.otus.spring07homework.mapper;

import com.googlecode.jmapper.JMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring07homework.domain.Book;
import ru.otus.spring07homework.dto.BookDto;

@Component
public class BookMapper implements Mapper<Book, BookDto>{
    private static final JMapper<BookDto, Book> mapperToDto = new JMapper<>(BookDto.class, Book.class);
    private static final JMapper<Book, BookDto> mapperToEntity = new JMapper<>(Book.class, BookDto.class);

    @Override
    public BookDto toDto(Book entity) {
        return mapperToDto.getDestination(entity);
    }

    @Override
    public Book toEntity(BookDto dto) {
        return mapperToEntity.getDestination(dto);
    }
}
