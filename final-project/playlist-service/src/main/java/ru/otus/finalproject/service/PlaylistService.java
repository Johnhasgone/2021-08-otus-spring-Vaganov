package ru.otus.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.finalproject.domain.Playlist;
import ru.otus.finalproject.dto.GenreDto;
import ru.otus.finalproject.dto.PlaylistDto;
import ru.otus.finalproject.dto.TrackDto;
import ru.otus.finalproject.feign.MusicServiceProxy;
import ru.otus.finalproject.mapper.PlaylistMapper;
import ru.otus.finalproject.repository.PlaylistRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final MusicServiceProxy musicServiceProxy;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, MusicServiceProxy musicServiceProxy) {
        this.playlistRepository = playlistRepository;
        this.musicServiceProxy = musicServiceProxy;
    }

    public List<PlaylistDto> findAll() {
        return playlistRepository.findAll().stream()
                .map(PlaylistMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public PlaylistDto findById(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(String.format("Не найден плейлист [id = %s]", id), 1));
        List<TrackDto> tracks = musicServiceProxy.findTracksByIds(playlist.getTracks());
        PlaylistDto dto = PlaylistMapper.INSTANCE.toDto(playlist);
        dto.setTracks(tracks);
        return dto;
    }

    public PlaylistDto savePlaylist(PlaylistDto dto) {
        return PlaylistMapper.INSTANCE.toDto(
                playlistRepository.save(PlaylistMapper.INSTANCE.toEntity(dto))
        );
    }

    public List<TrackDto> getTracks(List<GenreDto> genres) {
        return musicServiceProxy.findTracksByGenres(genres);
    }

}
