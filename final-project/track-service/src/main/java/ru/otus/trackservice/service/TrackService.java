package ru.otus.trackservice.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.otus.trackservice.domain.Track;
import ru.otus.trackservice.dto.TrackDto;
import ru.otus.trackservice.mapper.TrackMapper;
import ru.otus.trackservice.repository.TrackRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
        int limit = 20 / genreIds.size();
        int remain = 20 % genreIds.size();

        List<Track> tracks = new ArrayList<>(trackRepository.findAllByGenre(genreIds.get(0), PageRequest.of(0, limit + remain)));

        for (int i = 1; i < genreIds.size(); i++) {
            tracks.addAll(trackRepository.findAllByGenre(genreIds.get(i), PageRequest.of(0, limit)));
        }

        return tracks.stream()
                .map(TrackMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}
