package ru.otus.spring14homework.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import ru.otus.spring14homework.domain.no_sql.Author;

import java.util.Map;

@RequiredArgsConstructor
public class AuthorConvertService implements EntityConvertService<ru.otus.spring14homework.domain.sql.Author, Author>{
    private final Map<Long, String> authorIdMap;

    @Override
    public Author convert(ru.otus.spring14homework.domain.sql.Author source) {
        authorIdMap.put(source.getId(), new ObjectId().toHexString());
        return new Author(authorIdMap.get(source.getId()), source.getName());
    }
}
