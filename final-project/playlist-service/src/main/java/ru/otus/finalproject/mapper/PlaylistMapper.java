package ru.otus.finalproject.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.otus.finalproject.domain.Playlist;
import ru.otus.finalproject.dto.PlaylistDto;

public interface PlaylistMapper extends Mapper<Playlist, PlaylistDto> {
    PlaylistMapper INSTANCE = Mappers.getMapper(PlaylistMapper.class);

    @Override
    @Mapping(target = "id")
    @Mapping(target = "name")
    PlaylistDto toDto(Playlist entity);
}
