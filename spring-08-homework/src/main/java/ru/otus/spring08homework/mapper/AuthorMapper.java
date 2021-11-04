package ru.otus.spring08homework.mapper;

import com.googlecode.jmapper.JMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring08homework.domain.Author;
import ru.otus.spring08homework.dto.AuthorDto;

@Component
public class AuthorMapper implements Mapper<Author, AuthorDto>{
    private static final JMapper<AuthorDto, Author> mapperToDto = new JMapper<>(AuthorDto.class, Author.class);
    private static final JMapper<Author, AuthorDto> mapperToEntity = new JMapper<>(Author.class, AuthorDto.class);

    @Override
    public AuthorDto toDto(Author entity) {
        return mapperToDto.getDestination(entity);
    }

    @Override
    public Author toEntity(AuthorDto dto) {
        return mapperToEntity.getDestination(dto);
    }
}
