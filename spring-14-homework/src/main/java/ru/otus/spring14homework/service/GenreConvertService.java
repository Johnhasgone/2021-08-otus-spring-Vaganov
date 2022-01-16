package ru.otus.spring14homework.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.spring14homework.domain.no_sql.MongoGenre;
import ru.otus.spring14homework.domain.sql.SqlDbGenre;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class GenreConvertService {
    private final ConcurrentMap<Long, String> genreIdMap = new ConcurrentHashMap<>();

    public MongoGenre convert(SqlDbGenre source) {
        if (genreIdMap.containsKey(source.getId())) {
            return null; // to skip processed item after restart
        }

        genreIdMap.put(source.getId(), new ObjectId().toHexString());
        return new MongoGenre(genreIdMap.get(source.getId()), source.getName());
    }

    public String getMongoId(Long id) {
        return genreIdMap.get(id);
    }
}
