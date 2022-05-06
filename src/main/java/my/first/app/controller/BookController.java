package my.first.app.controller;

import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import my.first.app.data.Book;
import my.first.app.data.UpdateCommand;
import my.first.app.service.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@ExecuteOn(TaskExecutors.IO)
@Controller("/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    protected final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Get("/list")
    public HttpResponse<?>  list(@Valid Pageable pageable, @QueryValue String genre){
        List<Book> books = bookRepository.findAll(pageable).getContent();
        if (genre.isEmpty()){
            return HttpResponse.ok(books);
        }
        List<Book> chosenBook = (List) books
                .stream()
                .filter((Book book) -> {
                    if(book.getGenre().equalsIgnoreCase(genre)){return true;}
                    return false;
                })
                .collect(Collectors.toList());
        return HttpResponse.ok(chosenBook);
    }

    @Post("/post")
    public HttpResponse<Book> save(@Body("name") String name, @Body("genre") String genre) {
        Book book = bookRepository.save(name,genre);

        log.info("Book created: {} with genre {}",name, genre);
        return HttpResponse
                .created(book)
                .headers(headers -> headers.location(location(book.getId())));
    }

    protected URI location(Long id) {
        return URI.create("/books/" + id);
    }

    protected URI location(Book book) {
        return location(book.getId());
    }


    @Delete("/delete/{id}")
    public void delete(Long id){ bookRepository.deleteById(id);}

    @Put("/edit")
    public HttpResponse update(@Body @Valid UpdateCommand command) { // <7>
        bookRepository.update(command.getId(), command.getName(), command.getGenre());
        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(command.getId()).getPath()); // <8>
    }

}
