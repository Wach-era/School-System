package pages;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/academic-years/*")
public class AcademicYearServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AcademicYearDAO academicYearDAO;

    public void init() {
        academicYearDAO = new AcademicYearDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertYear(request, response);
                    break;
                default:
                    listYears(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listYears(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<AcademicYear> listAcademicYear = academicYearDAO.selectAllYears();
        request.setAttribute("listAcademicYear", listAcademicYear);
        request.getRequestDispatcher("/academic-year-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/academic-year-form.jsp").forward(request, response);
    }

    private void insertYear(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int yearId = Integer.parseInt(request.getParameter("year_id"));
        Date startDate = Date.valueOf(request.getParameter("start_date"));
        Date endDate = Date.valueOf(request.getParameter("end_date"));
        AcademicYear newYear = new AcademicYear(yearId, startDate, endDate);
        academicYearDAO.insertYear(newYear);
        response.sendRedirect("list");
    }
}
