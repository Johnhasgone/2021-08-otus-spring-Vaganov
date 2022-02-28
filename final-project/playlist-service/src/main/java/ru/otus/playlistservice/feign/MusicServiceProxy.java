package ru.otus.playlistservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.playlistservice.dto.GenreDto;
import ru.otus.playlistservice.dto.TrackDto;

import java.util.List;

@FeignClient(name = "music-service")
public interface MusicServiceProxy {

	@PostMapping(value = "track")
	List<TrackDto> findTracksByIds(@RequestBody List<Long> idList);

	@PostMapping(value = "track")
	List<TrackDto> findTracksByGenres(@RequestBody List<GenreDto> genres);

	@GetMapping(value = "genre")
	List<GenreDto> findGenres();
}
