package ru.otus.spring06homework.dto;

import com.googlecode.jmapper.annotations.JGlobalMap;
import lombok.Data;

@Data
@JGlobalMap
public class CommentDto {
    private Long id;
    private String text;

    @Override
    public String toString() {
        return id + " | " + text;
    }
}
