package ru.otus.spring11homework.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.otus.spring11homework.domain.Author;
import ru.otus.spring11homework.dto.AuthorDto;

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
