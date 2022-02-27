package ru.otus.trackservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.trackservice.dto.GenreDto;
import ru.otus.trackservice.service.GenreService;

import java.util.List;

@RestController
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("genre")
    public List<GenreDto> getGenres() {
        return genreService.findAll();
    }

    @PostMapping("genre")
    public GenreDto addGenre(@RequestBody GenreDto dto) {
        return genreService.createGenre(dto.getName());
    }

}
