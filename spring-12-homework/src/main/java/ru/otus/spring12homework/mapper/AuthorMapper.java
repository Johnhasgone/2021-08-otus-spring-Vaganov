package ru.otus.spring12homework.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.otus.spring12homework.domain.Author;
import ru.otus.spring12homework.dto.AuthorDto;

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