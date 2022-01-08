package ru.otus.spring14homework.mongock.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring14homework.dao.no_sql.AuthorMongoRepository;
import ru.otus.spring14homework.dao.no_sql.BookMongoRepository;
import ru.otus.spring14homework.dao.no_sql.GenreMongoRepository;
import ru.otus.spring14homework.domain.no_sql.Author;
import ru.otus.spring14homework.domain.no_sql.Book;
import ru.otus.spring14homework.domain.no_sql.Genre;

import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author ans;
    private Author bns;
    private Author asp;

    private Genre novel;
    private Genre lyrics;
    private Genre scienceFiction;

    @ChangeSet(order = "000", id = "dropDB", author = "johnhasgone", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "johnhasgone", runAlways = true)
    public void initAuthors(AuthorMongoRepository repository){
        ans = repository.save(new Author("Аркадий Стругацкий"));
        bns = repository.save(new Author("Борис Стругацкий"));
        asp = repository.save(new Author("Александр Пушкин"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "johnhasgone", runAlways = true)
    public void initGenres(GenreMongoRepository repository) {
        novel = repository.save(new Genre("роман"));
        lyrics = repository.save(new Genre("поэзия"));
        scienceFiction = repository.save(new Genre("фантастика"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "johnhasgone", runAlways = true)
    public void initBooks(BookMongoRepository repository){
        repository.save(new Book("1", "Улитка на склоне", List.of(ans, bns), List.of(novel, scienceFiction)));
        repository.save(new Book("2", "Стихотворения и поэмы", List.of(asp), List.of(lyrics)));
    }
}
