package ru.otus.spring08homework.mongock.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring08homework.dao.AuthorRepository;
import ru.otus.spring08homework.dao.BookRepository;
import ru.otus.spring08homework.dao.GenreRepository;
import ru.otus.spring08homework.domain.Author;
import ru.otus.spring08homework.domain.Book;
import ru.otus.spring08homework.domain.Genre;

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
    public void initAuthors(AuthorRepository repository){
        ans = repository.save(new Author("Аркадий Стругацкий"));
        bns = repository.save(new Author("Борис Стругацкий"));
        asp = repository.save(new Author("Александр Пушкин"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "johnhasgone", runAlways = true)
    public void initGenres(GenreRepository repository) {
        novel = repository.save(new Genre("роман"));
        lyrics = repository.save(new Genre("поэзия"));
        scienceFiction = repository.save(new Genre("фантастика"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "johnhasgone", runAlways = true)
    public void initBooks(BookRepository repository){
        repository.save(new Book("1", "Улитка на склоне", List.of(ans, bns), List.of(novel, scienceFiction)));
        repository.save(new Book("2", "Стихотворения и поэмы", List.of(asp), List.of(lyrics)));
    }
}
