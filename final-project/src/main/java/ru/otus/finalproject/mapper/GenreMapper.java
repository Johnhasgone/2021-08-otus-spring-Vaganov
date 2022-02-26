package ru.otus.finalproject.mapper;

import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.otus.finalproject.domain.Genre;
import ru.otus.finalproject.dto.GenreDto;

@Component
public interface GenreMapper extends Mapper<Genre, GenreDto> {

    @Override
    @Mapping(target = "id")
    @Mapping(target = "name")
    GenreDto toDto(Genre entity);

    @Override
    @Mapping(target = "id")
    @Mapping(target = "name")
    Genre toEntity(GenreDto dto);
}
