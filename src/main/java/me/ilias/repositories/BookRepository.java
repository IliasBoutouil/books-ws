package me.ilias.repositories;

import me.ilias.domains.Book;

public interface BookRepository {
    boolean isAvailable(String bookTitle);
    Book findByBookTitle(String bookTitle);
    Book saveBook(Book book);
    void deleteBook(String bookTitle);
    Book toggleAvailable(String bookTitle);
}
