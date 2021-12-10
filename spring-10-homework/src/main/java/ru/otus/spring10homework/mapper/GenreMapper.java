package ru.otus.spring10homework.mapper;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.otus.spring10homework.domain.Genre;
import ru.otus.spring10homework.dto.GenreDto;

@Component
public interface GenreMapper extends Mapper<Genre, GenreDto>{
    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    @Override
    GenreDto toDto(Genre entity);

    @Override
    Genre toEntity(GenreDto dto);
}
