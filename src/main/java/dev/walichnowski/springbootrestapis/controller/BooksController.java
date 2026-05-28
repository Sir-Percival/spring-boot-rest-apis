package dev.walichnowski.springbootrestapis.controller;

import dev.walichnowski.springbootrestapis.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
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

    @GetMapping("/api")
    public String helloAPI()
    {
        return "Hello from API!";
    }

    @GetMapping("/api/books")
    public List<Book> getBooks(@RequestParam(required = false) String category)
    {
        if(category == null)
            return books;

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @GetMapping("/api/books/{title}")
    public Book getBookByTitle(@PathVariable String title)
    {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }
}
