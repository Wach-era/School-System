//package pages;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class BookDAO {
//    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
//    private String jdbcUsername = "root";
//    private String jdbcPassword = "Bhillary@2021";
//
//    private static final String INSERT_BOOK_SQL = "INSERT INTO Books (title, author, genre, isbn, publication_year, publisher, language, num_copies, shelf_location, book_cover_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//    private static final String SELECT_BOOK_BY_ID = "SELECT * FROM Books WHERE book_id = ?";
//    private static final String SELECT_ALL_BOOKS = "SELECT * FROM Books";
//    private static final String DELETE_BOOK_SQL = "DELETE FROM Books WHERE book_id = ?";
//    private static final String UPDATE_BOOK_SQL = "UPDATE Books SET title = ?, author = ?, genre = ?, isbn = ?, publication_year = ?, publisher = ?, language = ?, num_copies = ?, shelf_location = ?, book_cover_url = ? WHERE book_id = ?";
//
//    public BookDAO() {}
//
//    protected Connection getConnection() {
//        Connection connection = null;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//    public void insertBook(Book book) throws SQLException {
//        try (Connection connection = getConnection(); 
//             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_SQL)) {
//            preparedStatement.setString(1, book.getTitle());
//            preparedStatement.setString(2, book.getAuthor());
//            preparedStatement.setString(3, book.getGenre());
//            preparedStatement.setString(4, book.getIsbn());
//            preparedStatement.setInt(5, book.getPublicationYear());
//            preparedStatement.setString(6, book.getPublisher());
//            preparedStatement.setString(7, book.getLanguage());
//            preparedStatement.setInt(8, book.getNumCopies());
//            preparedStatement.setString(9, book.getShelfLocation());
//            preparedStatement.setString(10, book.getBookCoverUrl());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//    }
//
//    public Book selectBook(int bookId) {
//        Book book = null;
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID)) {
//            preparedStatement.setInt(1, bookId);
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                String title = rs.getString("title");
//                String author = rs.getString("author");
//                String genre = rs.getString("genre");
//                String isbn = rs.getString("isbn");
//                int publicationYear = rs.getInt("publication_year");
//                String publisher = rs.getString("publisher");
//                String language = rs.getString("language");
//                int numCopies = rs.getInt("num_copies");
//                String shelfLocation = rs.getString("shelf_location");
//                String bookCoverUrl = rs.getString("book_cover_url");
//                book = new Book(bookId, title, author, genre, isbn, publicationYear, publisher, language, numCopies, shelfLocation, bookCoverUrl);
//            }
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//        return book;
//    }
//
//    public List<Book> selectAllBooks() {
//        List<Book> books = new ArrayList<>();
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKS)) {
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                int bookId = rs.getInt("book_id");
//                String title = rs.getString("title");
//                String author = rs.getString("author");
//                String genre = rs.getString("genre");
//                String isbn = rs.getString("isbn");
//                int publicationYear = rs.getInt("publication_year");
//                String publisher = rs.getString("publisher");
//                String language = rs.getString("language");
//                int numCopies = rs.getInt("num_copies");
//                String shelfLocation = rs.getString("shelf_location");
//                String bookCoverUrl = rs.getString("book_cover_url");
//                books.add(new Book(bookId, title, author, genre, isbn, publicationYear, publisher, language, numCopies, shelfLocation, bookCoverUrl));
//            }
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//        return books;
//    }
//
//    public boolean deleteBook(int bookId) throws SQLException {
//        boolean rowDeleted;
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_SQL)) {
//            statement.setInt(1, bookId);
//            rowDeleted = statement.executeUpdate() > 0;
//        }
//        return rowDeleted;
//    }
//
//    public boolean updateBook(Book book) throws SQLException {
//        boolean rowUpdated;
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK_SQL)) {
//            statement.setString(1, book.getTitle());
//            statement.setString(2, book.getAuthor());
//            statement.setString(3, book.getGenre());
//            statement.setString(4, book.getIsbn());
//            statement.setInt(5, book.getPublicationYear());
//            statement.setString(6, book.getPublisher());
//            statement.setString(7, book.getLanguage());
//            statement.setInt(8, book.getNumCopies());
//            statement.setString(9, book.getShelfLocation());
//            statement.setString(10, book.getBookCoverUrl());
//            statement.setInt(11, book.getBookId());
//            rowUpdated = statement.executeUpdate() > 0;
//        }
//        return rowUpdated;
//    }
//
//    private void printSQLException(SQLException ex) {
//        for (Throwable e : ex) {
//            if (e instanceof SQLException) {
//                e.printStackTrace(System.err);
//                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
//                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
//                System.err.println("Message: " + e.getMessage());
//                Throwable t = ex.getCause();
//                while (t != null) {
//                    System.out.println("Cause: " + t);
//                    t = t.getCause();
//                }
//            }
//        }
//    }
//}
