package bg.softuni.booksrest.web;

import bg.softuni.booksrest.model.dto.BookDTO;
import bg.softuni.booksrest.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable("id") Long id) {
        Optional<BookDTO> book = bookService.getBookById(id);

        return
                book.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<BookDTO> deleteBookById(@PathVariable("id") Long id) {
        bookService.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping()
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO,
                                              UriComponentsBuilder uriComponentsBuilder) {
        Long newBookId = bookService.createBook(bookDTO);

        return ResponseEntity.created(uriComponentsBuilder
                .path("/api/books/{id}").build(newBookId))
                .build();
    }
}
