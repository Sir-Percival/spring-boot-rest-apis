package dev.walichnowski.springbootrestapis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book
{
    private long id;
    private String title;
    private String author;
    private String category;
    private int rating;
}
