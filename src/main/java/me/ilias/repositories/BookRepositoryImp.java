package me.ilias.repositories;

import me.ilias.config.DbConfig;
import me.ilias.domains.Book;

import java.sql.*;

public class BookRepositoryImp implements BookRepository {
    Connection conn;
    public BookRepositoryImp() {
        this.conn = DbConfig.startH2DB();
    }

    @Override
    public boolean isAvailable(String bookTitle) {
        boolean available = false;
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT available FROM books WHERE title = ?")) {
            stmt.setString(1, bookTitle);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    available = rs.getBoolean("available");
                }
            }
        } catch (SQLException e) {
            // Handle the exception
        }
        return available;
    }

    @Override
    public Book findByBookTitle(String bookTitle) {
        Book book = null;
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT title, available, available_date, min_price, max_price, default_tva " +
                        "FROM books WHERE title = ?")) {
            stmt.setString(1, bookTitle);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    book = new Book();
                    book.setTitle(rs.getString("title"));
                    book.setAvailable(rs.getBoolean("available"));
                    book.setAvailableDate(rs.getDate("available_date").toLocalDate());
                    book.setMinPrice(rs.getDouble("min_price"));
                    book.setMaxPrice(rs.getDouble("max_price"));
                    book.setDefaultTVA(rs.getDouble("default_tva"));
                }
            }
        } catch (SQLException e) {
            // Handle the exception
        }
        return book;
    }

    @Override
    public Book saveBook(Book book) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO books (title, available, available_date, min_price, max_price, default_tva) " +
                        "VALUES (?, ?, ?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE available = VALUES(available), available_date = VALUES(available_date), " +
                        "min_price = VALUES(min_price), max_price = VALUES(max_price), default_tva = VALUES(default_tva)")) {
            stmt.setString(1, book.getTitle());
            stmt.setBoolean(2, book.isAvailable());
            stmt.setDate(3, Date.valueOf(book.getAvailableDate()));
            stmt.setDouble(4, book.getMinPrice());
            stmt.setDouble(5, book.getMaxPrice());
            stmt.setDouble(6, book.getDefaultTVA());
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception
        }
        return book;
    }

    @Override
    public void deleteBook(String bookTitle) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM books WHERE title = ?")) {
            stmt.setString(1, bookTitle);
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception
        }
    }

    @Override
    public Book toggleAvailable(String bookTitle) {
        Book book = findByBookTitle(bookTitle);
        if (book != null) {
            book.setAvailable(!book.isAvailable());
            saveBook(book);
        }
        return book;
    }
}
