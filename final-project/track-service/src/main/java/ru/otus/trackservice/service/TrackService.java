package ru.otus.trackservice.service;

import ru.otus.trackservice.dto.TrackDto;
import ru.otus.trackservice.mapper.TrackMapper;
import ru.otus.trackservice.repository.TrackRepository;

import java.util.List;
import java.util.stream.Collectors;

public class TrackService {
    private final TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public List<TrackDto> findAllByIds(List<Long> ids) {
        return trackRepository.findAllById(ids).stream()
                .map(TrackMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public List<TrackDto> findAllByGenreIds(List<Long> genreIds) {
        return trackRepository.findAllByGenres(genreIds).stream()
                .map(TrackMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}
