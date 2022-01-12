package ru.otus.spring14homework.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.spring14homework.domain.no_sql.MongoAuthor;
import ru.otus.spring14homework.domain.sql.SqlDbAuthor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class AuthorConvertService {
    private final ConcurrentMap<Long, String> authorIdMap = new ConcurrentHashMap<>();

    public MongoAuthor convert(SqlDbAuthor source) {
        if (authorIdMap.containsKey(source.getId())) {
            return null; // to skip processed item after restart
        }
        authorIdMap.put(source.getId(), new ObjectId().toHexString());
        return new MongoAuthor(authorIdMap.get(source.getId()), source.getName());
    }

    public String getMongoId(Long id) {
        return authorIdMap.get(id);
    }
}
