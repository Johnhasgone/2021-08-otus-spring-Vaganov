package ru.otus.trackservice.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.otus.trackservice.domain.Genre;
import ru.otus.trackservice.dto.GenreDto;

@org.mapstruct.Mapper
public interface GenreMapper extends Mapper<Genre, GenreDto> {
    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    @Override
    @Mapping(target = "id")
    @Mapping(target = "name")
    GenreDto toDto(Genre entity);

    @Override
    @Mapping(target = "id")
    @Mapping(target = "name")
    Genre toEntity(GenreDto dto);
}
