package my.first.app.data;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

@Introspected
public class UpdateCommand {
    @NonNull
    private final Long id;

    @NotBlank
    private final String name;

    @NotBlank
    private final String genre;


    public UpdateCommand(@NonNull Long id, String name, String genre) {
        this.id = id;
        this.name = name;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {return genre;}
}
