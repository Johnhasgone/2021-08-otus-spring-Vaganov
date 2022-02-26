package ru.otus.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackDto {
    private Long trackId;
    private Long albumId;
    private Long artistId;
}
