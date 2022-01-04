package ru.otus.spring12homework.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.otus.spring12homework.domain.Comment;
import ru.otus.spring12homework.dto.CommentDto;

@org.mapstruct.Mapper
public interface CommentMapper extends Mapper<Comment, CommentDto>{
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);


    @Override
    @Mapping(target = "id")
    @Mapping(target = "text")
    CommentDto toDto(Comment entity);

    @Override
    Comment toEntity(CommentDto dto);
}
