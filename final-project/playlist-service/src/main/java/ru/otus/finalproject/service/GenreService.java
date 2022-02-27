package ru.otus.finalproject.service;

import org.springframework.stereotype.Service;
import ru.otus.finalproject.dto.GenreDto;
import ru.otus.finalproject.feign.MusicServiceProxy;

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
