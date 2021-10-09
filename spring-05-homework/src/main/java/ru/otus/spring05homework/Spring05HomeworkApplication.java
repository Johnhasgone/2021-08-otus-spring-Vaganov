package ru.otus.spring05homework;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring05homework.dao.AuthorDao;
import ru.otus.spring05homework.dao.BookDao;
import ru.otus.spring05homework.dao.GenreDao;
import ru.otus.spring05homework.domain.Author;
import ru.otus.spring05homework.domain.Book;
import ru.otus.spring05homework.domain.Genre;

import java.sql.SQLException;

@SpringBootApplication
public class Spring05HomeworkApplication {

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(Spring05HomeworkApplication.class, args);

        AuthorDao authorDao = context.getBean(AuthorDao.class);
        BookDao bookDao = context.getBean(BookDao.class);
        GenreDao genreDao = context.getBean(GenreDao.class);

        Author author = new Author(1L, "Stanislav Lem");
        authorDao.insert(author);
        System.out.println(authorDao.getAll());
        System.out.println(authorDao.getById(1L));

        Genre genre = new Genre(1L, "Lyrics");
        genreDao.insert(genre);
        System.out.println(genreDao.getAll());
        System.out.println(genreDao.getById(1L));

        bookDao.insert(new Book(1L, "One fly over the cookoo's nest", genre, author));
        System.out.println(bookDao.getAll());
        System.out.println(bookDao.getById(1L));

        Console.main(args);
    }


}
