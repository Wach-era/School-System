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
//@WebServlet("/fines/*")
//public class FinesServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//    protected static final BigDecimal DAILY_FINE_RATE = BigDecimal.valueOf(5);
//    private FinesDAO finesDAO;
//    private BorrowedBookDAO borrowedBookDAO;
//
//    public void init() {
//        finesDAO = new FinesDAO();
//        borrowedBookDAO = new BorrowedBookDAO();
//    }
//
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	String action = request.getPathInfo();
//
//        if (action == null) {
//            action = "list";
//        }
//
//        switch (action) {
//            case "/new":
//                showNewForm(request, response);
//                break;
//            case "/insert":
//                insertFine(request, response);
//                break;
//            case "/delete":
//                deleteFine(request, response);
//                break;
//            case "/edit":
//                showEditForm(request, response);
//                break;
//            case "/update":
//                updateFine(request, response);
//                break;
//            case "/pay":
//                payFine(request, response);
//                break;
//            default:
//                listFines(request, response);
//                break;
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }
//
//    private void listFines(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<Fine> fines = finesDAO.selectAllFines();
//        request.setAttribute("fines", fines);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/fines-list.jsp");
//        dispatcher.forward(request, response);
//    }
//
//    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/fines-form.jsp");
//        dispatcher.forward(request, response);
//    }
//
//    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int fineId = Integer.parseInt(request.getParameter("id"));
//        Fine existingFine = finesDAO.selectFine(fineId);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/fines-form.jsp");
//        request.setAttribute("fine", existingFine);
//        dispatcher.forward(request, response);
//    }
//
//    private void insertFine(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int borrowId = Integer.parseInt(request.getParameter("borrowId"));
//        BigDecimal fineAmount = new BigDecimal(request.getParameter("fineAmount"));
//        LocalDate fineDate = LocalDate.parse(request.getParameter("fineDate"));
//
//        Fine newFine = new Fine(0, borrowId, fineAmount, fineDate, false);
//        try {
//            finesDAO.insertFine(newFine);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        response.sendRedirect(request.getContextPath() + "/fines/list");
//    }
//
//    private void updateFine(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int fineId = Integer.parseInt(request.getParameter("fineId"));
//        int borrowId = Integer.parseInt(request.getParameter("borrowId"));
//        BigDecimal fineAmount = new BigDecimal(request.getParameter("fineAmount"));
//        LocalDate fineDate = LocalDate.parse(request.getParameter("fineDate"));
//        boolean paid = Boolean.parseBoolean(request.getParameter("paid"));
//
//        Fine fine = new Fine(fineId, borrowId, fineAmount, fineDate, paid);
//        try {
//            finesDAO.updateFine(fine);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        response.sendRedirect(request.getContextPath() + "/fines/list");
//    }
//
//    private void deleteFine(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int fineId = Integer.parseInt(request.getParameter("id"));
//        try {
//            finesDAO.deleteFine(fineId);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        response.sendRedirect(request.getContextPath() + "/fines/list");
//    }
//
//    private void payFine(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int fineId = Integer.parseInt(request.getParameter("id"));
//        try {
//            finesDAO.payFine(fineId);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        response.sendRedirect(request.getContextPath() + "/fines/list");
//    }
//    private void calculateFines() {
//        List<BorrowedBook> overdueBooks = borrowedBookDAO.findOverdueBooks();
//
//        for (BorrowedBook book : overdueBooks) {
//            BigDecimal fineAmount = calculateFine(book);
//            Fine fine = new Fine(0, book.getBorrowId(), fineAmount, LocalDate.now(), false);
//            try {
//                finesDAO.insertFine(fine);
//            } catch (SQLException e) {
//                e.printStackTrace();
//                // Handle exception appropriately
//            }
//        }
//    }
//
//    private BigDecimal calculateFine(BorrowedBook book) {
//        long daysOverdue = ChronoUnit.DAYS.between(book.getDueDate(), LocalDate.now());
//        return BigDecimal.valueOf(daysOverdue).multiply(DAILY_FINE_RATE);
//    }
//}