//package pages;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class BorrowedBookDAO {
//    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
//    private String jdbcUsername = "root";
//    private String jdbcPassword = "Bhillary@2021";
//
//    private static final String INSERT_BORROWED_BOOK_SQL = "INSERT INTO BorrowedBooks (borrower_ref_id, book_id, borrow_date, due_date, return_date, lost) VALUES (?, ?, ?, ?, ?, ?);";
//    private static final String SELECT_BORROWED_BOOK_BY_ID = "SELECT borrow_id, borrower_ref_id, book_id, borrow_date, due_date, return_date, lost FROM BorrowedBooks WHERE borrow_id = ?;";
//    private static final String SELECT_ALL_BORROWED_BOOKS = "SELECT * FROM BorrowedBooks;";
//    private static final String DELETE_BORROWED_BOOK_SQL = "DELETE FROM BorrowedBooks WHERE borrow_id = ?;";
//    private static final String UPDATE_BORROWED_BOOK_SQL = "UPDATE BorrowedBooks SET borrower_ref_id = ?, book_id = ?, borrow_date = ?, due_date = ?, return_date = ?, lost = ? WHERE borrow_id = ?;";
//
//    protected Connection getConnection() {
//        Connection connection = null;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//    public void insertBorrowedBook(BorrowedBook borrowedBook) throws SQLException {
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BORROWED_BOOK_SQL)) {
//            preparedStatement.setInt(1, borrowedBook.getBorrowerRefId());
//            preparedStatement.setInt(2, borrowedBook.getBookId());
//            preparedStatement.setDate(3, borrowedBook.getBorrowDate());
//            preparedStatement.setDate(4, borrowedBook.getDueDate());
//            preparedStatement.setDate(5, borrowedBook.getReturnDate());
//            preparedStatement.setBoolean(6, borrowedBook.isLost());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//    }
//
//    public BorrowedBook selectBorrowedBook(int id) {
//        BorrowedBook borrowedBook = null;
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BORROWED_BOOK_BY_ID)) {
//            preparedStatement.setInt(1, id);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            if (rs.next()) {
//                int borrowerRefId = rs.getInt("borrower_ref_id");
//                int bookId = rs.getInt("book_id");
//                Date borrowDate = rs.getDate("borrow_date");
//                Date dueDate = rs.getDate("due_date");
//                Date returnDate = rs.getDate("return_date");
//                boolean lost = rs.getBoolean("lost");
//                borrowedBook = new BorrowedBook(id, borrowerRefId, bookId, borrowDate, dueDate, returnDate, lost);
//            }
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//        return borrowedBook;
//    }
//
//    public List<BorrowedBook> selectAllBorrowedBooks() {
//        List<BorrowedBook> borrowedBooks = new ArrayList<>();
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BORROWED_BOOKS)) {
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                int borrowId = rs.getInt("borrow_id");
//                int borrowerRefId = rs.getInt("borrower_ref_id");
//                int bookId = rs.getInt("book_id");
//                Date borrowDate = rs.getDate("borrow_date");
//                Date dueDate = rs.getDate("due_date");
//                Date returnDate = rs.getDate("return_date");
//                boolean lost = rs.getBoolean("lost");
//                borrowedBooks.add(new BorrowedBook(borrowId, borrowerRefId, bookId, borrowDate, dueDate, returnDate, lost));
//            }
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//        return borrowedBooks;
//    }
//
//    public boolean deleteBorrowedBook(int id) throws SQLException {
//        boolean rowDeleted;
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(DELETE_BORROWED_BOOK_SQL)) {
//            statement.setInt(1, id);
//            rowDeleted = statement.executeUpdate() > 0;
//        }
//        return rowDeleted;
//    }
//
//    public boolean updateBorrowedBook(BorrowedBook borrowedBook) throws SQLException {
//        boolean rowUpdated;
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(UPDATE_BORROWED_BOOK_SQL)) {
//            statement.setInt(1, borrowedBook.getBorrowerRefId());
//            statement.setInt(2, borrowedBook.getBookId());
//            statement.setDate(3, borrowedBook.getBorrowDate());
//            statement.setDate(4, borrowedBook.getDueDate());
//            statement.setDate(5, borrowedBook.getReturnDate());
//            statement.setBoolean(6, borrowedBook.isLost());
//            statement.setInt(7, borrowedBook.getId());
//
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
