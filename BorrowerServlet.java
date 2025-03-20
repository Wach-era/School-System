package pages;

import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/borrower/*")
public class BorrowerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BorrowerDAO borrowerDAO;
    private StudentDAO studentDAO;
    private TeacherDAO teacherDAO;
    private StaffDAO staffDAO;

    public void init() {
        borrowerDAO = new BorrowerDAO();
        studentDAO = new StudentDAO();
        teacherDAO = new TeacherDAO();
        staffDAO = new StaffDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertBorrower(request, response);
                    break;
                case "/delete":
                    deleteBorrower(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateBorrower(request, response);
                    break;
                default:
                    listBorrowers(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listBorrowers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Borrower> listBorrower = borrowerDAO.selectAllBorrowers();
        request.setAttribute("listBorrower", listBorrower);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/borrower-list.jsp");
        dispatcher.forward(request, response);
    }


    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/borrower-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Borrower existingBorrower = borrowerDAO.selectBorrowerByRefId(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/borrower-form.jsp");
        request.setAttribute("borrower", existingBorrower);
        dispatcher.forward(request, response);
    }

    private void insertBorrower(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int borrowerRefId = Integer.parseInt(request.getParameter("borrowerRefId"));
        String borrowerType = request.getParameter("borrowerType");

        // Check if borrower exists in the corresponding table
        boolean exists = false;
        switch (borrowerType.toLowerCase()) {
            case "student":
                exists = studentDAO.selectStudent(borrowerRefId) != null;
                break;
            case "teacher":
                exists = teacherDAO.selectTeacher(borrowerRefId) != null;
                break;
            case "staff":
                exists = staffDAO.selectStaff(borrowerRefId) != null;
                break;
        }

        if (exists) {
            Borrower newBorrower = new Borrower(0, borrowerType, borrowerRefId);
            borrowerDAO.insertBorrower(newBorrower);
            response.sendRedirect(request.getContextPath() + "/borrower/list");
        } else {
            // Handle case where borrower does not exist
            response.sendRedirect(request.getContextPath() + "/borrower/new?error=not_found");
        }
    }

    private void updateBorrower(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String borrowerType = request.getParameter("borrowerType");
        int borrowerRefId = Integer.parseInt(request.getParameter("borrowerRefId"));

        Borrower borrower = new Borrower(id, borrowerType, borrowerRefId);
        borrowerDAO.updateBorrower(borrower);
        response.sendRedirect(request.getContextPath() + "/borrower/list");
    }

    private void deleteBorrower(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        borrowerDAO.deleteBorrower(id);
        response.sendRedirect(request.getContextPath() + "/borrower/list");
    }
}
