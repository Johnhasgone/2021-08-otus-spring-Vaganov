package ru.otus.spring11homework.mongock.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring11homework.dao.AuthorRepository;
import ru.otus.spring11homework.dao.BookRepository;
import ru.otus.spring11homework.dao.GenreRepository;
import ru.otus.spring11homework.domain.Author;
import ru.otus.spring11homework.domain.Book;
import ru.otus.spring11homework.domain.Genre;

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
        ans = repository.save(new Author("Аркадий Стругацкий")).block();
        bns = repository.save(new Author("Борис Стругацкий")).block();
        asp = repository.save(new Author("Александр Пушкин")).block();
    }

    @ChangeSet(order = "002", id = "initGenres", author = "johnhasgone", runAlways = true)
    public void initGenres(GenreRepository repository) {
        novel = repository.save(new Genre("роман")).block();
        lyrics = repository.save(new Genre("поэзия")).block();
        scienceFiction = repository.save(new Genre("фантастика")).block();
    }

    @ChangeSet(order = "003", id = "initBooks", author = "johnhasgone", runAlways = true)
    public void initBooks(BookRepository repository){
        repository.save(new Book("Улитка на склоне", List.of(ans, bns), List.of(novel, scienceFiction))).block();
        repository.save(new Book("Стихотворения и поэмы", List.of(asp), List.of(lyrics))).block();
    }
}
