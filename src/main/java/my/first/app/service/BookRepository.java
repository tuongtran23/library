package my.first.app.service;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;
import my.first.app.data.Book;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface BookRepository extends PageableRepository <Book, Long> {

    Book save(@NonNull @NotBlank String name, @NonNull @NotBlank String genre);

    @Transactional
    default Book saveWithException(@NonNull @NotBlank String name, @NonNull @NotBlank String genre){
        save(name,genre);
        throw new DataAccessException("test exception");
    }

    long update(@NonNull @NotNull @Id Long id, @NonNull @NotBlank String name, @NonNull @NotBlank String genre);
}
