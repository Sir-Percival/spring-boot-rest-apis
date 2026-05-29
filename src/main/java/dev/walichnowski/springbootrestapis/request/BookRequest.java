package dev.walichnowski.springbootrestapis.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRequest
{
    private String title;
    private String author;
    private String category;
    private int rating;
}
