package ru.otus.spring14homework.dao.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring14homework.domain.sql.SqlDbAuthor;

public interface SqlDbAuthorRepository extends JpaRepository<SqlDbAuthor, Long> {
}
