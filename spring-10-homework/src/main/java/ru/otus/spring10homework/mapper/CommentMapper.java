package ru.otus.spring10homework.mapper;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.otus.spring10homework.domain.Comment;
import ru.otus.spring10homework.dto.CommentDto;

@Component
@org.mapstruct.Mapper
public interface CommentMapper extends Mapper<Comment, CommentDto>{
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);


    @Override
    CommentDto toDto(Comment entity);

    @Override
    Comment toEntity(CommentDto dto);
}
