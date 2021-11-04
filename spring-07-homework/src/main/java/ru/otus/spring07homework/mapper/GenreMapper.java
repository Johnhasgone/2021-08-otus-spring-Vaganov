package ru.otus.spring07homework.mapper;

import com.googlecode.jmapper.JMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring07homework.domain.Genre;
import ru.otus.spring07homework.dto.GenreDto;

@Component
public class GenreMapper implements Mapper<Genre, GenreDto>{
    private static final JMapper<GenreDto, Genre> mapperToDto = new JMapper<>(GenreDto.class, Genre.class);
    private static final JMapper<Genre, GenreDto> mapperToEntity = new JMapper<>(Genre.class, GenreDto.class);

    @Override
    public GenreDto toDto(Genre entity) {
        return mapperToDto.getDestination(entity);
    }

    @Override
    public Genre toEntity(GenreDto dto) {
        return mapperToEntity.getDestination(dto);
    }
}
