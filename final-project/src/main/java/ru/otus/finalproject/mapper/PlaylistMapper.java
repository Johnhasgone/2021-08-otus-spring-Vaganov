package ru.otus.finalproject.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.otus.finalproject.domain.Playlist;
import ru.otus.finalproject.dto.PlaylistDto;

public interface PlaylistMapper extends Mapper<Playlist, PlaylistDto> {

    @Override
    @Mapping(target = "id")
    @Mapping(target = "name")
    @Mapping(target = "tracks", qualifiedByName = "tracksToString")
    PlaylistDto toDto(Playlist entity);
}
