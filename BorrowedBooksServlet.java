//package pages;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//
//@WebServlet("/borrowed-books/*")
//public class BorrowedBooksServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//    private BorrowedBookDAO borrowedBookDAO;
//    private FinesDAO finesDAO;
//
//    public void init() {
//        borrowedBookDAO = new BorrowedBookDAO();
//        finesDAO = new FinesDAO();
//    }
//
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getPathInfo();
//        
//        if (action == null) {
//            action = "/list";
//        }
//
//        try {
//            switch (action) {
//                case "/new":
//                    showNewForm(request, response);
//                    break;
//                case "/insert":
//                    insertBorrowedBook(request, response);
//                    break;
//                case "/delete":
//                    deleteBorrowedBook(request, response);
//                    break;
//                case "/edit":
//                    showEditForm(request, response);
//                    break;
//                case "/update":
//                    updateBorrowedBook(request, response);
//                    break;
//                case "/markAsLost":
//                    markAsLost(request, response);
//                    break;
//                default:
//                    listBorrowedBooks(request, response);
//                    break;
//            }
//        } catch (SQLException e) {
//            throw new ServletException(e);
//        }
//    }
//
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }
//
//    private void listBorrowedBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<BorrowedBook> borrowedBooks = borrowedBookDAO.selectAllBorrowedBooks();
//        request.setAttribute("borrowedBooks", borrowedBooks);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/borrowed-books-list.jsp");
//        dispatcher.forward(request, response);
//    }
//
//    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/borrowed-books-form.jsp");
//        dispatcher.forward(request, response);
//    }
//
//    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int borrowId = Integer.parseInt(request.getParameter("id"));
//        BorrowedBook existingBorrowedBook = borrowedBookDAO.selectBorrowedBook(borrowId);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/borrowed-books-form.jsp");
//        request.setAttribute("borrowedBook", existingBorrowedBook);
//        dispatcher.forward(request, response);
//    }
//
//    private void insertBorrowedBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int borrowerId = Integer.parseInt(request.getParameter("borrowerId"));
//        int bookId = Integer.parseInt(request.getParameter("bookId"));
//        LocalDate borrowDate = LocalDate.parse(request.getParameter("borrowDate"));
//        LocalDate dueDate = LocalDate.parse(request.getParameter("dueDate"));
//
//        BorrowedBook newBorrowedBook = new BorrowedBook(0, borrowerId, bookId, borrowDate, dueDate, null);
//        try {
//            borrowedBookDAO.insertBorrowedBook(newBorrowedBook);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        response.sendRedirect(request.getContextPath() + "/borrowed-books/list");
//    }
//
//    private void updateBorrowedBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int borrowId = Integer.parseInt(request.getParameter("borrowId"));
//        int borrowerId = Integer.parseInt(request.getParameter("borrowerId"));
//        int bookId = Integer.parseInt(request.getParameter("bookId"));
//        LocalDate borrowDate = LocalDate.parse(request.getParameter("borrowDate"));
//        LocalDate dueDate = LocalDate.parse(request.getParameter("dueDate"));
//        LocalDate returnDate = request.getParameter("returnDate").isEmpty() ? null : LocalDate.parse(request.getParameter("returnDate"));
//
//        BorrowedBook borrowedBook = new BorrowedBook(borrowId, borrowerId, bookId, borrowDate, dueDate, returnDate);
//        try {
//            borrowedBookDAO.updateBorrowedBook(borrowedBook);
//            if (returnDate != null && returnDate.isAfter(dueDate)) {
//                // Calculate fine for overdue return
//                BigDecimal fineAmount = calculateFine(borrowedBook);
//                Fine fine = new Fine(0, borrowId, fineAmount, LocalDate.now(), false);
//                finesDAO.insertFine(fine);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        response.sendRedirect(request.getContextPath() + "/borrowed-books/list");
//    }
//
//    private BigDecimal calculateFine(BorrowedBook book) {
//        long daysOverdue = ChronoUnit.DAYS.between(book.getDueDate(), LocalDate.now());
//        return BigDecimal.valueOf(daysOverdue).multiply(FinesServlet.DAILY_FINE_RATE);
//    }
//
//    private void deleteBorrowedBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int borrowId = Integer.parseInt(request.getParameter("id"));
//        try {
//            borrowedBookDAO.deleteBorrowedBook(borrowId);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        response.sendRedirect(request.getContextPath() + "/borrowed-books/list");
//    }
//    private void markAsLost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
//        int borrowId = Integer.parseInt(request.getParameter("borrowId"));
//        borrowedBookDAO.markAsLost(borrowId);
//        response.sendRedirect(request.getContextPath() + "/borrowed-books/list");
//    }
//    
//}
