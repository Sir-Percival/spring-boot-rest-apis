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
                new Book("Title 1", "Author 1", "fantasy"),
                new Book("Title 2", "Author 2", "science"),
                new Book("Title 3", "Author 3", "it"),
                new Book("Title 4", "Author 4", "fantasy"),
                new Book("Title 5", "Author 5", "it")
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

    @GetMapping("/{title}")
    public Book getBookByTitle(@PathVariable String title)
    {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
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

    @PutMapping("/{title}")
    public void updateBook(@PathVariable String title, @RequestBody Book updatedBook)
    {
        for(int i = 0; i < books.size(); i++)
        {
            if(books.get(i).getTitle().equalsIgnoreCase(title))
            {
                books.set(i, updatedBook);
                return;
            }
        }
    }

    @Operation(summary = "Delete book by title", description = "Deletes book by provided title")
    @ApiResponse(responseCode = "200", description = "Successfully deleted")
    @DeleteMapping("/{title}")
    public void deleteBook(@PathVariable @Parameter(name = "title", description = "Book's title", example = "Title 1") String title)
    {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }
}
