package ru.otus.spring07homework.mapper;

import com.googlecode.jmapper.JMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring07homework.domain.Comment;
import ru.otus.spring07homework.dto.CommentDto;

@Component
public class CommentMapper implements Mapper<Comment, CommentDto>{
    private static final JMapper<CommentDto, Comment> mapperToDto = new JMapper<>(CommentDto.class, Comment.class);
    private static final JMapper<Comment, CommentDto> mapperToEntity = new JMapper<>(Comment.class, CommentDto.class);

    @Override
    public CommentDto toDto(Comment entity) {
        return mapperToDto.getDestination(entity);
    }

    @Override
    public Comment toEntity(CommentDto dto) {
        return mapperToEntity.getDestination(dto);
    }
}
