package pages;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class LibraryDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_BOOK_SQL = "INSERT INTO Books (book_id, title, author, genre, isbn, publication_year, publisher, language, num_copies, shelf_location, book_cover_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_BOOK_BY_ID = "SELECT * FROM Books WHERE book_id = ?;";
    private static final String SELECT_ALL_BOOKS = "SELECT * FROM Books;";
    private static final String DELETE_BOOK_SQL = "DELETE FROM Books WHERE book_id = ?;";
    private static final String UPDATE_BOOK_SQL = "UPDATE Books SET title = ?, author = ?, genre = ?, isbn = ?, publication_year = ?, publisher = ?, language = ?, num_copies = ?, shelf_location = ?, book_cover_url = ? WHERE book_id = ?;";

    private static final String INSERT_BORROWED_BOOK_SQL = "INSERT INTO BorrowedBooks (borrower_ref_id, book_id, borrow_date, due_date, lost, overdue_fine) VALUES (?, ?, ?, ?, ?, ?);";
   // private static final String SELECT_BORROWED_BOOK_BY_ID = "SELECT * FROM BorrowedBooks WHERE borrow_id = ?;";
    //private static final String SELECT_ALL_BORROWED_BOOKS = "SELECT * FROM BorrowedBooks;";
    private static final String DELETE_BORROWED_BOOK_SQL = "DELETE FROM BorrowedBooks WHERE borrow_id = ?;";
    private static final String UPDATE_BORROWED_BOOK_SQL = "UPDATE BorrowedBooks SET borrower_ref_id = ?, book_id = ?, borrow_date = ?, due_date = ?, lost = ?, overdue_fine = ? WHERE borrow_id = ?;";

   // private static final String INSERT_RETURN_SQL = "INSERT INTO Returns (borrow_id, return_date, fine_amount) VALUES (?, ?, ?);";
    private static final String INSERT_FINE_SQL = "INSERT INTO Fines (borrow_id, fine_amount, fine_date, paid) VALUES (?, ?, ?, ?);";
    //private static final String UPDATE_BORROWED_BOOK_RETURN_SQL = "UPDATE BorrowedBooks SET return_date = ?, lost = ? WHERE borrow_id = ?;";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Books CRUD operations
    public void insertBook(Book book) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_SQL)) {
            preparedStatement.setString(1, book.getBookId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getGenre());
            preparedStatement.setString(5, book.getIsbn());
            preparedStatement.setInt(6, book.getPublicationYear());
            preparedStatement.setString(7, book.getPublisher());
            preparedStatement.setString(8, book.getLanguage());
            preparedStatement.setInt(9, book.getNumCopies());
            preparedStatement.setString(10, book.getShelfLocation());
            preparedStatement.setString(11, book.getBookCoverUrl());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Book selectBook(String id) {
        Book book = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID)) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                String isbn = rs.getString("isbn");
                int publicationYear = rs.getInt("publication_year");
                String publisher = rs.getString("publisher");
                String language = rs.getString("language");
                int numCopies = rs.getInt("num_copies");
                String shelfLocation = rs.getString("shelf_location");
                String bookCoverUrl = rs.getString("book_cover_url");
                book = new Book(id, title, author, genre, isbn, publicationYear, publisher, language, numCopies, shelfLocation, bookCoverUrl);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return book;
    }

    public List<Book> selectAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String id = rs.getString("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                String isbn = rs.getString("isbn");
                int publicationYear = rs.getInt("publication_year");
                String publisher = rs.getString("publisher");
                String language = rs.getString("language");
                int numCopies = rs.getInt("num_copies");
                String shelfLocation = rs.getString("shelf_location");
                String bookCoverUrl = rs.getString("book_cover_url");
                books.add(new Book(id, title, author, genre, isbn, publicationYear, publisher, language, numCopies, shelfLocation, bookCoverUrl));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return books;
    }

    public boolean deleteBook(String id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_SQL)) {
            statement.setString(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateBook(Book book) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK_SQL)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.setString(4, book.getIsbn());
            statement.setInt(5, book.getPublicationYear());
            statement.setString(6, book.getPublisher());
            statement.setString(7, book.getLanguage());
            statement.setInt(8, book.getNumCopies());
            statement.setString(9, book.getShelfLocation());
            statement.setString(10, book.getBookCoverUrl());
            statement.setString(11, book.getBookId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // BorrowedBooks CRUD operations
    public void insertBorrowedBook(BorrowedBook borrowedBook) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BORROWED_BOOK_SQL)) {
            preparedStatement.setInt(1, borrowedBook.getBorrowerRefId());
            preparedStatement.setString(2, borrowedBook.getBookId());
            preparedStatement.setDate(3, Date.valueOf(borrowedBook.getBorrowDate()));
            preparedStatement.setDate(4, Date.valueOf(borrowedBook.getDueDate()));
            preparedStatement.setBoolean(5, borrowedBook.isLost());
            preparedStatement.setBigDecimal(6, borrowedBook.getOverdueFine());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

 // Updated selectBorrowedBook method
    public BorrowedBook selectBorrowedBook(int borrowId) {
        BorrowedBook borrowedBook = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT b.borrow_id, b.borrower_ref_id, b.book_id, b.borrow_date, b.due_date, " +
                 "r.return_date, b.lost, b.overdue_fine " +
                 "FROM BorrowedBooks b " +
                 "LEFT JOIN Returns r ON b.borrow_id = r.borrow_id " +
                 "WHERE b.borrow_id = ?")) {
            preparedStatement.setInt(1, borrowId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int borrowerRefId = rs.getInt("borrower_ref_id");
                String bookId = rs.getString("book_id");
                LocalDate borrowDate = rs.getDate("borrow_date").toLocalDate();
                LocalDate dueDate = rs.getDate("due_date").toLocalDate();
                LocalDate returnDate = rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null;
                boolean lost = rs.getBoolean("lost");
                BigDecimal overdueFine = rs.getBigDecimal("overdue_fine");
                borrowedBook = new BorrowedBook(borrowId, borrowerRefId, bookId, borrowDate, dueDate, returnDate, lost, overdueFine);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return borrowedBook;
    }

    // Updated selectAllBorrowedBooks method
    public List<BorrowedBook> selectAllBorrowedBooks() {
        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT b.borrow_id, b.borrower_ref_id, b.book_id, b.borrow_date, b.due_date, " +
                 "r.return_date, b.lost, b.overdue_fine " +
                 "FROM BorrowedBooks b " +
                 "LEFT JOIN Returns r ON b.borrow_id = r.borrow_id")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int borrowId = rs.getInt("borrow_id");
                int borrowerRefId = rs.getInt("borrower_ref_id");
                String bookId = rs.getString("book_id");
                LocalDate borrowDate = rs.getDate("borrow_date").toLocalDate();
                LocalDate dueDate = rs.getDate("due_date").toLocalDate();
                LocalDate returnDate = rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null;
                boolean lost = rs.getBoolean("lost");
                BigDecimal overdueFine = rs.getBigDecimal("overdue_fine");
                borrowedBooks.add(new BorrowedBook(borrowId, borrowerRefId, bookId, borrowDate, dueDate, returnDate, lost, overdueFine));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return borrowedBooks;
    }


    public boolean deleteBorrowedBook(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BORROWED_BOOK_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateBorrowedBook(BorrowedBook borrowedBook) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BORROWED_BOOK_SQL)) {
            statement.setInt(1, borrowedBook.getBorrowerRefId());
            statement.setString(2, borrowedBook.getBookId());
            statement.setDate(3, Date.valueOf(borrowedBook.getBorrowDate()));
            statement.setDate(4, Date.valueOf(borrowedBook.getDueDate()));
            statement.setBoolean(5, borrowedBook.isLost());
            statement.setBigDecimal(6, borrowedBook.getOverdueFine());
            statement.setInt(7, borrowedBook.getBorrowId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // Returns and Fines operations
    public boolean insertReturn(Return returnRecord) throws SQLException {
        boolean rowInserted;
        String INSERT_RETURN_SQL = "INSERT INTO Returns (borrow_id, return_date) VALUES (?, ?);";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RETURN_SQL)) {
            preparedStatement.setInt(1, returnRecord.getBorrowId());
            preparedStatement.setDate(2, Date.valueOf(returnRecord.getReturnDate()));
            rowInserted = preparedStatement.executeUpdate() > 0;
        }
        return rowInserted;
    }

    

    public void insertFine(Fine fine) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_FINE_SQL)) {
            statement.setInt(1, fine.getBorrowId());
            statement.setBigDecimal(2, fine.getFineAmount());
            statement.setDate(3, java.sql.Date.valueOf(fine.getFineDate()));
            statement.setBoolean(4, fine.isPaid());
            statement.executeUpdate();
        }
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = e.getCause();
                while (t != null) {
                    System.err.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public boolean updateBorrowedBookLostStatus(BorrowedBook borrowedBook) throws SQLException {
        boolean rowUpdated;
        String UPDATE_LOST_STATUS_SQL = "UPDATE BorrowedBooks SET lost = ? WHERE borrow_id = ?;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LOST_STATUS_SQL)) {
            preparedStatement.setBoolean(1, borrowedBook.isLost());
            preparedStatement.setInt(2, borrowedBook.getBorrowId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    
    public void updateBorrowedBookOverdueFine(int id, BigDecimal overdueFine) throws SQLException {
        String sql = "UPDATE BorrowedBooks SET overdueFine = ? WHERE borrow_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, overdueFine);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Fine> selectFinesByBorrowId(int borrowId) throws SQLException {
        List<Fine> fines = new ArrayList<>();
        String sql = "SELECT * FROM Fines WHERE borrow_id = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, borrowId);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                int fineId = rs.getInt("fine_id");
                BigDecimal fineAmount = rs.getBigDecimal("fine_amount");
                LocalDate fineDate = rs.getDate("fine_date").toLocalDate();
                boolean paid = rs.getBoolean("paid");
                
                Fine fine = new Fine(fineId, borrowId, fineAmount, fineDate, paid);
                fines.add(fine);
            }
        }
        return fines;
    }

    public List<BorrowedBook> selectBorrowedBooksByStudentAndDateRange(int studentId, String startDate, String endDate) {
        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT b.borrow_id, b.borrower_ref_id, b.book_id, b.borrow_date, b.due_date, " +
                 "r.return_date, b.lost, b.overdue_fine " +
                 "FROM BorrowedBooks b " +
                 "LEFT JOIN Returns r ON b.borrow_id = r.borrow_id " +
                 "INNER JOIN Borrowers br ON b.borrower_ref_id = br.borrower_ref_id " +
                 "WHERE br.borrower_type = 'Student' AND br.borrower_ref_id = ? " +
                 "AND b.borrow_date BETWEEN ? AND ?")) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setDate(2, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(3, java.sql.Date.valueOf(endDate));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int borrowId = rs.getInt("borrow_id");
                int borrowerRefId = rs.getInt("borrower_ref_id");
                String bookId = rs.getString("book_id");
                LocalDate borrowDate = rs.getDate("borrow_date").toLocalDate();
                LocalDate dueDate = rs.getDate("due_date").toLocalDate();
                LocalDate returnDate = rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null;
                boolean lost = rs.getBoolean("lost");
                BigDecimal overdueFine = rs.getBigDecimal("overdue_fine");
                borrowedBooks.add(new BorrowedBook(borrowId, borrowerRefId, bookId, borrowDate, dueDate, returnDate, lost, overdueFine));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return borrowedBooks;
    }
    
    public List<BorrowedBook> selectBorrowedBooksByTeacherAndDateRange(int nationalId, String startDate, String endDate) {
        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT b.borrow_id, b.borrower_ref_id, b.book_id, b.borrow_date, b.due_date, " +
                 "r.return_date, b.lost, b.overdue_fine " +
                 "FROM BorrowedBooks b " +
                 "LEFT JOIN Returns r ON b.borrow_id = r.borrow_id " +
                 "INNER JOIN Borrowers br ON b.borrower_ref_id = br.borrower_ref_id " +
                 "WHERE br.borrower_type = 'Teacher' AND br.borrower_ref_id = ? " +
                 "AND b.borrow_date BETWEEN ? AND ?")) {
            preparedStatement.setInt(1, nationalId);
            preparedStatement.setDate(2, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(3, java.sql.Date.valueOf(endDate));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int borrowId = rs.getInt("borrow_id");
                int borrowerRefId = rs.getInt("borrower_ref_id");
                String bookId = rs.getString("book_id");
                LocalDate borrowDate = rs.getDate("borrow_date").toLocalDate();
                LocalDate dueDate = rs.getDate("due_date").toLocalDate();
                LocalDate returnDate = rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null;
                boolean lost = rs.getBoolean("lost");
                BigDecimal overdueFine = rs.getBigDecimal("overdue_fine");
                borrowedBooks.add(new BorrowedBook(borrowId, borrowerRefId, bookId, borrowDate, dueDate, returnDate, lost, overdueFine));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return borrowedBooks;
    }
    
    public List<BorrowedBook> selectBorrowedBooksByStaffAndDateRange(int staffId, String startDate, String endDate) {
        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT b.borrow_id, b.borrower_ref_id, b.book_id, b.borrow_date, b.due_date, " +
                 "r.return_date, b.lost, b.overdue_fine " +
                 "FROM BorrowedBooks b " +
                 "LEFT JOIN Returns r ON b.borrow_id = r.borrow_id " +
                 "INNER JOIN Borrowers br ON b.borrower_ref_id = br.borrower_ref_id " +
                 "WHERE br.borrower_type = 'Staff' AND br.borrower_ref_id = ? " +
                 "AND b.borrow_date BETWEEN ? AND ?")) {
            preparedStatement.setInt(1, staffId);
            preparedStatement.setDate(2, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(3, java.sql.Date.valueOf(endDate));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int borrowId = rs.getInt("borrow_id");
                int borrowerRefId = rs.getInt("borrower_ref_id");
                String bookId = rs.getString("book_id");
                LocalDate borrowDate = rs.getDate("borrow_date").toLocalDate();
                LocalDate dueDate = rs.getDate("due_date").toLocalDate();
                LocalDate returnDate = rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null;
                boolean lost = rs.getBoolean("lost");
                BigDecimal overdueFine = rs.getBigDecimal("overdue_fine");
                borrowedBooks.add(new BorrowedBook(borrowId, borrowerRefId, bookId, borrowDate, dueDate, returnDate, lost, overdueFine));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return borrowedBooks;
    }



public List<BorrowedBook> selectBorrowedBooksByStudentId(int studentId) throws SQLException {
    List<BorrowedBook> borrowedBooks = new ArrayList<>();
    String sql = "SELECT b.borrow_id, b.book_id, b.borrow_date, b.due_date, r.return_date, b.lost, b.overdue_fine " +
            "FROM BorrowedBooks b " +
            "LEFT JOIN Returns r ON b.borrow_id = r.borrow_id " + 
            "WHERE b.borrower_ref_id = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, studentId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
        	int borrowId = rs.getInt("borrow_id");
            String bookId = rs.getString("book_id");
            LocalDate borrowDate = rs.getDate("borrow_date").toLocalDate();
            LocalDate dueDate = rs.getDate("due_date").toLocalDate();
            LocalDate returnDate = rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null;
            boolean lost = rs.getBoolean("lost");
            BigDecimal overdueFine = rs.getBigDecimal("overdue_fine");
            borrowedBooks.add(new BorrowedBook(borrowId, bookId, borrowDate, dueDate, returnDate, lost, overdueFine));
        }
    }
    return borrowedBooks;
}
}
