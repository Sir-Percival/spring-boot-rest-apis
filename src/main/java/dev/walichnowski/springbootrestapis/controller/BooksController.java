package dev.walichnowski.springbootrestapis.controller;

import dev.walichnowski.springbootrestapis.entity.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String category)
    {
        if(category == null)
            return books;

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable long id)
    {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public void createBook(@RequestBody Book newBook)
    {
        boolean isNewBook = books.stream()
                .noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));

        if(isNewBook)
            books.add(newBook);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable long id, @RequestBody Book updatedBook)
    {
        for(int i = 0; i < books.size(); i++)
        {
            if(books.get(i).getId() == id)
            {
                books.set(i, updatedBook);
                return;
            }
        }
    }

    @Operation(summary = "Delete book by id", description = "Deletes book by provided id")
    @ApiResponse(responseCode = "200", description = "Successfully deleted")
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable @Parameter(name = "id", description = "Book's id", example = "1") long id)
    {
        books.removeIf(book -> book.getId() == id);
    }
}
