package pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import static java.time.temporal.ChronoUnit.DAYS;

@WebServlet("/library/*")
public class LibraryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LibraryDAO libraryDAO;

    public void init() {
        libraryDAO = new LibraryDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/listBooks";
        }

        try {
            switch (action) {
                case "/newBook":
                    showNewBookForm(request, response);
                    break;
                case "/insertBook":
                    insertBook(request, response);
                    break;
                case "/deleteBook":
                    deleteBook(request, response);
                    break;
                case "/editBook":
                    showEditBookForm(request, response);
                    break;
                case "/updateBook":
                    updateBook(request, response);
                    break;
                case "/listBooks":
                    listBooks(request, response);
                    break;
                case "/listBorrowedBooks":
                    listBorrowedBooks(request, response);
                    break;
                case "/newBorrowedBook":
                    showNewBorrowedBookForm(request, response);
                    break;
                case "/insertBorrowedBook":
                    insertBorrowedBook(request, response);
                    break;
                case "/deleteBorrowedBook":
                    deleteBorrowedBook(request, response);
                    break;
                case "/editBorrowedBook":
                    showEditBorrowedBookForm(request, response);
                    break;
                case "/updateBorrowedBook":
                    updateBorrowedBook(request, response);
                    break;
                case "/returnBook":
                    showReturnBookForm(request, response);
                    break;
                case "/processReturn":
                    processReturn(request, response);
                    break;
                case "/listBorrowedBooksByStudent":
                    listBorrowedBooksByStudent(request, response);
                    break;
                default:
                    listBooks(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Book> listBook = libraryDAO.selectAllBooks();
        request.setAttribute("listBook", listBook);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/book-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewBookForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/book-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditBookForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String id = request.getParameter("id");
        Book existingBook = libraryDAO.selectBook(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/book-form.jsp");
        request.setAttribute("book", existingBook);
        dispatcher.forward(request, response);
    }

    private void insertBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String bookId = request.getParameter("bookId");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String isbn = request.getParameter("isbn");
        int publicationYear = Integer.parseInt(request.getParameter("publicationYear"));
        String publisher = request.getParameter("publisher");
        String language = request.getParameter("language");
        int numCopies = Integer.parseInt(request.getParameter("numCopies"));
        String shelfLocation = request.getParameter("shelfLocation");
        String bookCoverUrl = request.getParameter("bookCoverUrl");

        Book newBook = new Book(bookId, title, author, genre, isbn, publicationYear, publisher, language, numCopies, shelfLocation, bookCoverUrl);
        libraryDAO.insertBook(newBook);
        response.sendRedirect(request.getContextPath() + "/library/listBooks");
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookId = request.getParameter("bookId");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String isbn = request.getParameter("isbn");
        int publicationYear = Integer.parseInt(request.getParameter("publicationYear"));
        String publisher = request.getParameter("publisher");
        String language = request.getParameter("language");
        int numCopies = Integer.parseInt(request.getParameter("numCopies"));
        String shelfLocation = request.getParameter("shelfLocation");
        String bookCoverUrl = request.getParameter("bookCoverUrl");

        Book book = new Book(bookId, title, author, genre, isbn, publicationYear, publisher, language, numCopies, shelfLocation, bookCoverUrl);
        try {
            libraryDAO.updateBook(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/library/listBooks");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String id = request.getParameter("id");
        libraryDAO.deleteBook(id);
        response.sendRedirect(request.getContextPath() + "/library/listBooks");
    }

    private void listBorrowedBooks(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<BorrowedBook> listBorrowedBook = libraryDAO.selectAllBorrowedBooks();
        LocalDate currentDate = LocalDate.now();
        
        for (BorrowedBook borrowedBook : listBorrowedBook) {
            if (currentDate.isAfter(borrowedBook.getDueDate())) {
                long daysOverdue = DAYS.between(borrowedBook.getDueDate(), currentDate);
                BigDecimal overdueFine = BigDecimal.valueOf(daysOverdue * 1.00); // Assume 1.00 per day overdue
                borrowedBook.setOverdueFine(overdueFine);
                libraryDAO.updateBorrowedBookOverdueFine(borrowedBook.getBorrowId(), overdueFine);
            }
            
            List<Fine> fines = libraryDAO.selectFinesByBorrowId(borrowedBook.getBorrowId());
            borrowedBook.setFines(fines); // Assuming you have a setFines method in BorrowedBook
        }
        
        request.setAttribute("listBorrowedBook", listBorrowedBook);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/borrowed-books-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewBorrowedBookForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/borrowed-books-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditBorrowedBookForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        BorrowedBook existingBorrowedBook = libraryDAO.selectBorrowedBook(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/borrowed-books-form.jsp");
        request.setAttribute("borrowedBook", existingBorrowedBook);
        dispatcher.forward(request, response);
    }

    private void insertBorrowedBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        try {
            int borrowerRefId = Integer.parseInt(request.getParameter("borrowerRefId"));
            String bookId = request.getParameter("bookId");
            LocalDate borrowDate = LocalDate.parse(request.getParameter("borrowDate"));
            LocalDate dueDate = LocalDate.parse(request.getParameter("dueDate"));
            boolean lost = Boolean.parseBoolean(request.getParameter("lost"));
            BigDecimal overdueFine = BigDecimal.ZERO;

            if (borrowDate.isAfter(dueDate)) {
                request.setAttribute("errorMessage", "Borrow Date cannot be after Due Date.");
                request.getRequestDispatcher("/borrowed-books-form.jsp").forward(request, response);
                return;
            }

            BorrowerDAO borrowerDAO = new BorrowerDAO();
            Borrower borrower = borrowerDAO.selectBorrowerByRefId(borrowerRefId);
            if (borrower == null) {
                request.setAttribute("errorMessage", "Invalid Borrower Reference ID.");
                request.getRequestDispatcher("/borrowed-books-form.jsp").forward(request, response);
                return;
            }
            
            Book book = libraryDAO.selectBook(bookId);
            if (book == null) {
                request.setAttribute("errorMessage", "Invalid Book ID.");
                request.getRequestDispatcher("/borrowed-books-form.jsp").forward(request, response);
                return;
            }

            BorrowedBook newBorrowedBook = new BorrowedBook(0, borrowerRefId, bookId, borrowDate, dueDate, lost, overdueFine);
            libraryDAO.insertBorrowedBook(newBorrowedBook);
            response.sendRedirect(request.getContextPath() + "/library/listBorrowedBooks");
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid number format.");
            request.getRequestDispatcher("/borrowed-books-form.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred while processing your request.");
            request.getRequestDispatcher("/borrowed-books-form.jsp").forward(request, response);
        }
    }

    

    private void updateBorrowedBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        int borrowerRefId = Integer.parseInt(request.getParameter("borrowerRefId"));
        String bookId = request.getParameter("bookId");
        LocalDate borrowDate = LocalDate.parse(request.getParameter("borrowDate"));
        LocalDate dueDate = LocalDate.parse(request.getParameter("dueDate"));
       // LocalDate returnDate = request.getParameter("returnDate").isEmpty() ? null : LocalDate.parse(request.getParameter("returnDate"));
        //LocalDate returnDate = LocalDate.parse(request.getParameter("returnDate"));
        boolean lost = Boolean.parseBoolean(request.getParameter("lost"));
        BigDecimal overdueFine = BigDecimal.ZERO;


        if (borrowDate.isAfter(dueDate)) {
            request.setAttribute("errorMessage", "Borrow Date cannot be after Due Date.");
            request.getRequestDispatcher("/borrowed-books-form.jsp").forward(request, response);
            return;
        }

//        if (returnDate != null && returnDate.isBefore(borrowDate)) {
//            request.setAttribute("errorMessage", "Return Date cannot be before Borrow Date.");
//            request.getRequestDispatcher("/borrowed-books-form.jsp").forward(request, response);
//            return;
//        }

        BorrowedBook borrowedBook = new BorrowedBook(id, borrowerRefId, bookId, borrowDate, dueDate, lost, overdueFine);
        libraryDAO.updateBorrowedBook(borrowedBook);
        response.sendRedirect(request.getContextPath() + "/library/listBorrowedBooks");
    }


    private void deleteBorrowedBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        libraryDAO.deleteBorrowedBook(id);
        response.sendRedirect(request.getContextPath() + "/library/listBorrowedBooks");
    }

    private void showReturnBookForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BorrowedBook> listBorrowedBook = libraryDAO.selectAllBorrowedBooks();
        request.setAttribute("listBorrowedBook", listBorrowedBook);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/return-book-form.jsp");
        dispatcher.forward(request, response);
    }


    private void processReturn(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int borrowId = Integer.parseInt(request.getParameter("borrowId"));
        LocalDate returnDate = LocalDate.parse(request.getParameter("returnDate"));
        boolean lost = request.getParameter("lost") != null;

        // Fetch the borrowed book to get the borrow date
        BorrowedBook borrowedBook = libraryDAO.selectBorrowedBook(borrowId);

        // Debugging: Print out borrowedBook details
        System.out.println("Borrowed Book: " + borrowedBook);
        
        // Validate return date
        if (returnDate.isBefore(borrowedBook.getBorrowDate())) {
            request.setAttribute("errorMessage", "Return date cannot be before the borrow date.");
            
            // Fetch the list of borrowed books again and set it as request attribute
            List<BorrowedBook> listBorrowedBook = libraryDAO.selectAllBorrowedBooks();
            request.setAttribute("listBorrowedBook", listBorrowedBook);
            
            request.getRequestDispatcher("/return-book-form.jsp").forward(request, response);
            return;
        }

        // Calculate overdue fine if the book is returned late
        BigDecimal overdueFine = BigDecimal.ZERO;
        if (returnDate.isAfter(borrowedBook.getDueDate()) && !lost) {
            long daysOverdue = DAYS.between(borrowedBook.getDueDate(), returnDate);
            overdueFine = BigDecimal.valueOf(daysOverdue * 1.00); // Assume 1.00 per day overdue
        }

        // Debugging: Print out overdueFine
        System.out.println("Overdue Fine: " + overdueFine);

        // Insert return record into the Returns table
        Return newReturn = new Return(0, borrowId, returnDate, overdueFine);
        libraryDAO.insertReturn(newReturn);

        // Update the borrowed book record with the lost status
        borrowedBook.setLost(lost);
        libraryDAO.updateBorrowedBookLostStatus(borrowedBook);

        // Handle fine for lost book
        if (lost) {
            BigDecimal lostBookFine = BigDecimal.valueOf(50.00);
            LocalDate fineDate = LocalDate.now();
            Fine fine = new Fine(0, borrowId, lostBookFine, fineDate, false);
            libraryDAO.insertFine(fine);
        } else if (overdueFine.compareTo(BigDecimal.ZERO) > 0) {
            // Insert fine for overdue book
            LocalDate fineDate = LocalDate.now();
            Fine fine = new Fine(0, borrowId, overdueFine, fineDate, false);
            libraryDAO.insertFine(fine);
        }

        response.sendRedirect(request.getContextPath() + "/library/listBorrowedBooks");
    }
    
    private void listBorrowedBooksByStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        List<BorrowedBook> borrowedBooks = libraryDAO.selectBorrowedBooksByStudentId(studentId);

        // Generate HTML for the borrowed books
        StringBuilder html = new StringBuilder();
        html.append("<h4>Borrowed Books</h4>");
        if (borrowedBooks.isEmpty()) {
            html.append("<p>No books borrowed.</p>");
        } else {
            html.append("<table class='table table-bordered'>");
            html.append("<thead><tr><th>Book ID</th><th>Borrow Date</th><th>Due Date</th><th>Overdue Fine</th><th>Status</th></tr></thead>");
            html.append("<tbody>");
            for (BorrowedBook book : borrowedBooks) {
                html.append("<tr>");
                html.append("<td>").append(book.getBookId()).append("</td>");
                html.append("<td>").append(book.getBorrowDate()).append("</td>");
                html.append("<td>").append(book.getDueDate()).append("</td>");
                html.append("<td>").append(book.getOverdueFine()).append("</td>");
                html.append("<td>").append(book.isLost() ? "Lost" : "Borrowed").append("</td>");
                html.append("</tr>");
            }
            html.append("</tbody></table>");
        }

        // Set the response content type to HTML
        response.setContentType("text/html");
        response.getWriter().write(html.toString());
    }

}