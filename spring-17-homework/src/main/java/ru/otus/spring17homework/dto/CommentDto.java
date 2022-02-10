package ru.otus.spring17homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String text;

    @Override
    public String toString() {
        return id + " | " + text;
    }
}
