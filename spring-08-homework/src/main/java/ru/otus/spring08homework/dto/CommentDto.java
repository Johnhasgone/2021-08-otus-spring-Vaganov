package ru.otus.spring08homework.dto;

import com.googlecode.jmapper.annotations.JGlobalMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JGlobalMap
public class CommentDto {
    private String id;
    private String text;

    @Override
    public String toString() {
        return id + " | " + text;
    }
}
