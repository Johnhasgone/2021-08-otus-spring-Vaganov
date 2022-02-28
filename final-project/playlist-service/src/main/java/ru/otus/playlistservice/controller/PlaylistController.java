package ru.otus.playlistservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.playlistservice.dto.GenreDto;
import ru.otus.playlistservice.dto.PlaylistDto;
import ru.otus.playlistservice.dto.TrackDto;
import ru.otus.playlistservice.service.PlaylistService;

import java.util.List;

@RestController
public class PlaylistController {
    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("playlist")
    public List<PlaylistDto> getPlaylists() {
        return playlistService.findAll();
    }

    @GetMapping("playlist")
    public PlaylistDto getPlaylistById(@RequestParam Long id) {
        return playlistService.findById(id);
    }

    @PostMapping("playlist/create")
    public PlaylistDto createPlaylist(@RequestBody List<GenreDto> genres) {
        List<TrackDto> tracks = playlistService.getTracks(genres);
        PlaylistDto dto = new PlaylistDto();
        dto.setName("Новый плейлист");
        dto.setTracks(tracks);
        return dto;
    }
}
