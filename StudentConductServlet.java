package pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/student-conduct/*")
public class StudentConductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentConductDAO conductDAO;

    @Override
    public void init() {
        conductDAO = new StudentConductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/delete":
                    deleteConduct(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/"); // Redirect to home or appropriate page
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/insert":
                    addConduct(request, response);
                    break;
                case "/update":
                    updateConduct(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/"); // Redirect to home or appropriate page
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
    	 List<StudentConduct> listConduct = conductDAO.getStudentConductRecords(studentId);
         request.setAttribute("listConduct", listConduct);
         request.getRequestDispatcher("/student-conduct-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int conductId = Integer.parseInt(request.getParameter("id"));
        StudentConduct existingConduct = conductDAO.getStudentConductById(conductId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-conduct-form.jsp");
        request.setAttribute("conduct", existingConduct);
        dispatcher.forward(request, response);
    }

    private void addConduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        Date date = Date.valueOf(request.getParameter("date"));
        String conductDescription = request.getParameter("conductDescription");
        String actionTaken = request.getParameter("actionTaken");

        StudentConduct newConduct = new StudentConduct(0, studentId, date, conductDescription, actionTaken);
        conductDAO.insertStudentConduct(newConduct);
        response.sendRedirect(request.getContextPath() + "/student-conduct/new?studentId=" + studentId);
    }

    private void updateConduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int conductId = Integer.parseInt(request.getParameter("conductId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        Date date = Date.valueOf(request.getParameter("date"));
        String conductDescription = request.getParameter("conductDescription");
        String actionTaken = request.getParameter("actionTaken");

        StudentConduct updatedConduct = new StudentConduct(conductId, studentId, date, conductDescription, actionTaken);
        conductDAO.updateStudentConduct(updatedConduct);
        response.sendRedirect(request.getContextPath() + "/student-conduct/new?studentId=" + studentId);
    }

    private void deleteConduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int conductId = Integer.parseInt(request.getParameter("id"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));

        conductDAO.deleteStudentConduct(conductId);
        response.sendRedirect(request.getContextPath() + "/student-conduct/new?studentId=" + studentId);
    }
}
