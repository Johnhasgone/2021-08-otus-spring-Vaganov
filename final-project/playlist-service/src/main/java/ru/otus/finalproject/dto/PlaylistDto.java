package ru.otus.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
public class PlaylistDto {
    private Long id;
    private String name;
    private List<TrackDto> tracks;
}
