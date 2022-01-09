package ru.otus.spring14homework.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import ru.otus.spring14homework.domain.no_sql.Genre;

import java.util.Map;

@RequiredArgsConstructor
public class GenreConvertService implements EntityConvertService<ru.otus.spring14homework.domain.sql.Genre, Genre>{
    private final Map<Long, String> genreIdMap;

    @Override
    public Genre convert(ru.otus.spring14homework.domain.sql.Genre source) {
        genreIdMap.put(source.getId(), new ObjectId().toHexString());
        return new Genre(genreIdMap.get(source.getId()), source.getName());
    }
}
