package br.com.kievmaia.bookservice.controller;

import br.com.kievmaia.bookservice.model.Book;
import br.com.kievmaia.bookservice.proxy.CambioProxy;
import br.com.kievmaia.bookservice.repository.BookRepository;
import br.com.kievmaia.bookservice.response.Cambio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping("book-service")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {
    private final Environment environment;
    private final BookRepository repository;
    private final CambioProxy cambioProxy;

    @Operation(summary = "Find a specific book by id")
    @GetMapping(value = "/{id}/{curerncy}")
    public Book findBook(@PathVariable("id") Long id,
                         @PathVariable("curerncy") String currency) {
        final var book =
                repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Book %s not found", id)));

        final Cambio cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency);

        final var port = environment.getProperty("local.server.port");
        book.setEnvironment("Book port: " + port + " Cambio port: " + cambio.getEnvironment());
        book.setPrice(cambio.getConvertedValue());
        return book;
    }

    //    @GetMapping(value = "/{id}/{curerncy}")
    //    public Book findBook(@PathVariable("id") Long id,
    //                         @PathVariable("curerncy") String currency) {
    //        final var book =
    //                repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Book %s not
    //                found", id)));
    //
    //        final var params = new HashMap<String, String>();
    //        params.put("amount", book.getPrice().toString());
    //        params.put("from", "USD");
    //        params.put("to", currency);
    //
    //        final var response = new RestTemplate()
    //                .getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}", Cambio.class, params);
    //        final Cambio cambio = response.getBody();
    //
    //        final var port = environment.getProperty("local.server.port");
    //        book.setEnvironment(port);
    //        book.setPrice(cambio.getConvertedValue());
    //        return book;
    //    }
}
