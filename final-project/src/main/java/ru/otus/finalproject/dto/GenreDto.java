package ru.otus.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
public class GenreDto {
    private Long id;
    private String name;
}
