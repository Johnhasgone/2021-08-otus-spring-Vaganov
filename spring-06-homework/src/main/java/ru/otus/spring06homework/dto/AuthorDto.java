package ru.otus.spring06homework.dto;

import com.googlecode.jmapper.annotations.JGlobalMap;
import lombok.Data;

@Data
@JGlobalMap
public class AuthorDto {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return id + " | " + name;
    }
}
