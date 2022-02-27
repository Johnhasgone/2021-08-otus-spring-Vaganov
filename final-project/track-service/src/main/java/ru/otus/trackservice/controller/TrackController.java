package ru.otus.trackservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.trackservice.dto.TrackDto;
import ru.otus.trackservice.service.TrackService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TrackController {
    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping("track")
    public List<TrackDto> getTracks(@RequestParam(required = false) String filter, @RequestBody ArrayList<Long> ids) {
        if ("genre".equals(filter)) {
            return trackService.findAllByGenreIds(ids);
        }
        return trackService.findAllByIds(ids);
    }

}
