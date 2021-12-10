package ru.otus.spring10homework.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.otus.spring10homework.domain.Author;
import ru.otus.spring10homework.dto.AuthorDto;

@Component
@org.mapstruct.Mapper
public interface AuthorMapper extends Mapper<Author, AuthorDto> {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Override
    AuthorDto toDto(Author entity);

    @Override
    Author toEntity(AuthorDto dto);
}
