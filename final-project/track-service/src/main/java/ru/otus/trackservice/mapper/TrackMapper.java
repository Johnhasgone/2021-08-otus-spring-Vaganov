package ru.otus.trackservice.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.otus.trackservice.domain.Track;
import ru.otus.trackservice.dto.TrackDto;

@org.mapstruct.Mapper
public interface TrackMapper extends Mapper<Track, TrackDto> {
    TrackMapper INSTANCE = Mappers.getMapper(TrackMapper.class);

    @Override
    @Mapping(target = "id")
    @Mapping(target = "trackId")
    @Mapping(target = "albumId")
    @Mapping(target = "artistId")
    TrackDto toDto(Track entity);

    @Override
    @Mapping(target = "id")
    @Mapping(target = "trackId")
    @Mapping(target = "albumId")
    @Mapping(target = "artistId")
    Track toEntity(TrackDto dto);
}
