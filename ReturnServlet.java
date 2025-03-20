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
//import java.util.List;
//
//@WebServlet("/returns/*")
//public class ReturnServlet extends HttpServlet {
//    /**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private ReturnDAO returnDAO;
//    private BorrowedBookDAO borrowedBookDAO;
//
//    public void init() {
//        returnDAO = new ReturnDAO();
//        borrowedBookDAO = new BorrowedBookDAO();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getPathInfo();
//        if (action == null) {
//            action = "/list";
//        }
//
//        switch (action) {
//            case "/new":
//                showNewForm(request, response);
//                break;
//            case "/insert":
//                insertReturn(request, response);
//                break;
//            case "/delete":
//                deleteReturn(request, response);
//                break;
//            case "/edit":
//                showEditForm(request, response);
//                break;
//            case "/update":
//                updateReturn(request, response);
//                break;
//            default:
//                listReturns(request, response);
//                break;
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }
//
//
//    private void listReturns(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<Return> returns = returnDAO.selectAllReturns();
//        request.setAttribute("returns", returns);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/return-list.jsp");
//        dispatcher.forward(request, response);
//    }
//
//
//    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/return-form.jsp");
//        dispatcher.forward(request, response);
//    }
//
//    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int returnId = Integer.parseInt(request.getParameter("id"));
//        Return existingReturn = returnDAO.selectReturn(returnId);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/return-form.jsp");
//        request.setAttribute("ret", existingReturn);
//        dispatcher.forward(request, response);
//    }
//
//
//    private void insertReturn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int borrowId = Integer.parseInt(request.getParameter("borrowId"));
//        LocalDate returnDate = LocalDate.parse(request.getParameter("returnDate"));
//        BigDecimal fineAmount = new BigDecimal(request.getParameter("fineAmount"));
//
//        Return newReturn = new Return(0, borrowId, returnDate, fineAmount);
//        try {
//            returnDAO.insertReturn(newReturn);
//            BorrowedBook borrowedBook = borrowedBookDAO.selectBorrowedBook(borrowId);
//            borrowedBook.setReturnDate(returnDate);
//            borrowedBookDAO.updateBorrowedBook(borrowedBook);
//	        response.sendRedirect(request.getContextPath() + "/returns/list");
//        } catch (SQLException e) {
//            throw new ServletException(e);
//        }
//    }
//
//    private void updateReturn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int returnId = Integer.parseInt(request.getParameter("returnId"));
//        int borrowId = Integer.parseInt(request.getParameter("borrowId"));
//        LocalDate returnDate = LocalDate.parse(request.getParameter("returnDate"));
//        BigDecimal fineAmount = new BigDecimal(request.getParameter("fineAmount"));
//
//        Return returnObj = new Return(returnId, borrowId, returnDate, fineAmount);
//        try {
//            returnDAO.updateReturn(returnObj);
//	        response.sendRedirect(request.getContextPath() + "/returns/list");
//        } catch (SQLException e) {
//            throw new ServletException(e);
//        }
//    }
//
//    private void deleteReturn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int returnId = Integer.parseInt(request.getParameter("id"));
//        try {
//            returnDAO.deleteReturn(returnId);
//	        response.sendRedirect(request.getContextPath() + "/returns/list");
//        } catch (SQLException e) {
//            throw new ServletException(e);
//        }
//    }
//}
