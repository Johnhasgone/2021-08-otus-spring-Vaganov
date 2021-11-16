package ru.otus.spring09homework.mapper;

import com.googlecode.jmapper.JMapper;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;
import ru.otus.spring09homework.domain.Book;
import ru.otus.spring09homework.dto.BookDto;

@Component
public class BookMapper implements Mapper<Book, BookDto>{
    private static final JMapper<BookDto, Book> mapperToDto = new JMapper<>(BookDto.class, Book.class);

    @Override
    public BookDto toDto(Book entity) {
        return mapperToDto.getDestination(entity);
    }

    @Override
    public Book toEntity(BookDto dto) {
        throw new NotYetImplementedException();
    }
}
