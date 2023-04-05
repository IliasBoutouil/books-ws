package me.ilias.config;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbConfig {

    private DbConfig() {
        //singleton
    }

    public static final String DB = "h2:mem:books-service";
    public static final String DB_URL = String.format("jdbc:%s", DB);
    public static final String PASSWORD = "root";
    public static final String DRIVER = "org.h2.Driver";
    public static final String QUERY = "CREATE TABLE Book (title VARCHAR(255),available BOOLEAN,availableDate DATE,minPrice DECIMAL(102), maxPrice DECIMAL(10,2), defaultTVA DECIMAL(10,2));";
    public static final String INSERT = "INSERT INTO Book (title, available, availableDate, minPrice, maxPrice, defaultTVA) VALUES ('The Great Gatsby', true, '2023-04-01', 12.99, 24.99, 0.2), ('To Kill a Mockingbird', false, NULL, 10.50, 19.99, 0.2), ('1984', true, '2023-04-03', 8.99, 15.99, 0.2), ('Pride and Prejudice', true, '2023-04-02', 7.50, 14.99, 0.2), ('The Catcher in the Rye', false, NULL, 9.99, 18.99, 0.2), ('Brave New World', true, '2023-04-04', 11.99, 22.99, 0.2), ('Animal Farm', true, '2023-04-05', 6.99, 12.99, 0.2), ('The Hobbit', true, '2023-04-06', 14.50, 29.99, 0.2), ('Lord of the Flies', false, NULL, 8.50, 16.99, 0.2), ('Fahrenheit 451', true, '2023-04-07', 9.99, 18.99, 0.2);";

    @SneakyThrows
    public static Connection startH2DB() {
        Class.forName(DRIVER);
        return DriverManager.getConnection(DB_URL, PASSWORD, PASSWORD);
    }

    @SneakyThrows
    public static void initDb() {
        Connection connection = startH2DB();
        try (Statement statement = connection.createStatement()) {
            statement.execute(QUERY);
            statement.execute(INSERT);
        }
        connection.close();
    }
}
