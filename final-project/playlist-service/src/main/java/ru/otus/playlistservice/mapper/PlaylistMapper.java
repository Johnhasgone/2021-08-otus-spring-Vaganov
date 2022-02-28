package ru.otus.playlistservice.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.otus.playlistservice.domain.Playlist;
import ru.otus.playlistservice.dto.PlaylistDto;

public interface PlaylistMapper extends Mapper<Playlist, PlaylistDto> {
    PlaylistMapper INSTANCE = Mappers.getMapper(PlaylistMapper.class);

    @Override
    @Mapping(target = "id")
    @Mapping(target = "name")
    PlaylistDto toDto(Playlist entity);
}
