package ru.otus.finalproject.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.finalproject.dto.GenreDto;
import ru.otus.finalproject.dto.TrackDto;

import java.util.List;

@FeignClient(name = "music-service")
public interface MusicServiceProxy {

	@PostMapping(value = "track")
	List<TrackDto> findTracksByIds(@RequestBody List<Long> idList);

	@PostMapping(value = "track")
	List<TrackDto> findTracksByGenres(@RequestBody List<GenreDto> genres);
}
