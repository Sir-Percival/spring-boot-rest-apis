package dev.walichnowski.springbootrestapis.controller;

import dev.walichnowski.springbootrestapis.entity.Book;
import dev.walichnowski.springbootrestapis.exception.BookNotFoundException;
import dev.walichnowski.springbootrestapis.request.BookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Books API", description = "Operations related to books")
@RestController
@RequestMapping("/api/books")
public class BooksController
{
    private final List<Book> books = new ArrayList<>();

    public BooksController()
    {
        initialiseBooks();
    }

    private void initialiseBooks()
    {
        books.addAll(List.of(
                new Book(1, "Title 1", "Author 1", "fantasy", 5),
                new Book(2, "Title 2", "Author 2", "science", 4),
                new Book(3, "Title 3", "Author 3", "it", 3),
                new Book(4, "Title 4", "Author 4", "fantasy", 4),
                new Book(5, "Title 5", "Author 5", "it", 5)
        ));
    }

    @Operation(summary = "Get all books", description = "Retrieve a list of all available books")
    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String category)
    {
        if(category == null)
            return books;

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @Operation(summary = "Get a book by Id", description = "Retrieve a specific book by Id")
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable @Min(1) long id)
    {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book not found - " + id));
    }

    @Operation(summary = "Create a new book")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createBook(@Valid @RequestBody BookRequest bookRequest)
    {
        long id = books.isEmpty() ? 1 : books.getLast().getId() + 1;
        books.add(convertRequestToBook(id, bookRequest));
    }

    @Operation(summary = "Update a book", description = "Update the details of an existing book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable @Min(1) long id, @Valid @RequestBody BookRequest bookRequest)
    {
        for(int i = 0; i < books.size(); i++)
        {
            if(books.get(i).getId() == id)
            {
                Book updatedBook = convertRequestToBook(id, bookRequest);
                books.set(i, updatedBook);
                return updatedBook;
            }
        }
        throw new BookNotFoundException("Book not found - " + id);
    }

    @Operation(summary = "Delete a book", description = "Delete a book by given Id")
    @ApiResponse(responseCode = "204", description = "Successfully deleted")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable @Min(1) @Parameter(name = "id", description = "Book's id", example = "1") long id)
    {
        boolean removed = books.removeIf(book -> book.getId() == id);
        if(!removed)
            throw new BookNotFoundException("Book not found - " + id);
    }

    private Book convertRequestToBook(long id, BookRequest request)
    {
        return new Book(id, request.getTitle(), request.getAuthor(), request.getCategory(), request.getRating());
    }
}
