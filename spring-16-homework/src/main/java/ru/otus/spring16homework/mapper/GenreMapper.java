package ru.otus.spring16homework.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.otus.spring16homework.domain.Genre;
import ru.otus.spring16homework.dto.GenreDto;

@org.mapstruct.Mapper
public interface GenreMapper extends Mapper<Genre, GenreDto>{
    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    @Override
    @Mapping(target = "id")
    @Mapping(target = "name")
    GenreDto toDto(Genre entity);

    @Override
    Genre toEntity(GenreDto dto);
}
