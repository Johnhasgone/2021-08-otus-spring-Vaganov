package ru.otus.trackservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackDto {
    private Long id;
    private Long trackId;
    private Long albumId;
    private Long artistId;
}
