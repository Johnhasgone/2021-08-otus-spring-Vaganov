package ru.otus.spring10homework.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.otus.spring10homework.domain.Author;
import ru.otus.spring10homework.dto.AuthorDto;

@org.mapstruct.Mapper
public interface AuthorMapper extends Mapper<Author, AuthorDto> {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Override
    @Mapping(target = "id")
    @Mapping(target = "name")
    AuthorDto toDto(Author entity);

    @Override
    Author toEntity(AuthorDto dto);
}
