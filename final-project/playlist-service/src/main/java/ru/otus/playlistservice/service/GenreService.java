package ru.otus.playlistservice.service;

import org.springframework.stereotype.Service;
import ru.otus.playlistservice.dto.GenreDto;
import ru.otus.playlistservice.feign.MusicServiceProxy;

import java.util.List;

@Service
public class GenreService {
    private final MusicServiceProxy musicServiceProxy;

    public GenreService(MusicServiceProxy musicServiceProxy) {
        this.musicServiceProxy = musicServiceProxy;
    }

    public List<GenreDto> findAll() {
        return musicServiceProxy.findGenres();
    }
}
