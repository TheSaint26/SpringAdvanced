package bg.softuni.booksrest.service;

import bg.softuni.booksrest.model.dto.BookDTO;
import bg.softuni.booksrest.model.entity.AuthorEntity;
import bg.softuni.booksrest.model.entity.BookEntity;
import bg.softuni.booksrest.repository.AuthorRepository;
import bg.softuni.booksrest.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(b -> modelMapper.map(b, BookDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<BookDTO> getBookById(Long id) {
        return Optional.ofNullable(modelMapper.map(bookRepository.findById(id), BookDTO.class));
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public long createBook(BookDTO bookDTO) {
        String authorName = bookDTO.getAuthor().getName();

        Optional<AuthorEntity> optionalAuthor = authorRepository.findAuthorEntityByName(authorName);

        BookEntity newBook = new BookEntity()
                .setTitle(bookDTO.getTitle())
                .setIsbn(bookDTO.getIsbn())
                .setAuthor(optionalAuthor.orElseGet(() -> creteAuthor(authorName)));

        return bookRepository.save(newBook).getId();
    }

    private AuthorEntity creteAuthor(String authorName) {
        AuthorEntity author = new AuthorEntity().setName(authorName);
        return authorRepository.save(author);
    }
}
