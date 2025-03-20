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

@WebServlet("/teacher-conduct/*")
public class TeacherConductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TeacherConductDAO conductDAO;

    @Override
    public void init() {
        conductDAO = new TeacherConductDAO();
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
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));
    	 List<TeacherConduct> listConduct = conductDAO.getTeacherConductRecords(nationalId);
         request.setAttribute("listConduct", listConduct);
         request.getRequestDispatcher("/teacher-conduct-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int conductId = Integer.parseInt(request.getParameter("id"));
        TeacherConduct existingConduct = conductDAO.getTeacherConductById(conductId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-conduct-form.jsp");
        request.setAttribute("conduct", existingConduct);
        dispatcher.forward(request, response);
    }

    private void addConduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));
        Date date = Date.valueOf(request.getParameter("date"));
        String conductDescription = request.getParameter("conductDescription");
        String actionTaken = request.getParameter("actionTaken");

        TeacherConduct newConduct = new TeacherConduct(0, nationalId, date, conductDescription, actionTaken);
        conductDAO.insertTeacherConduct(newConduct);
        response.sendRedirect(request.getContextPath() + "/teacher-conduct/new?nationalId=" + nationalId);
    }

    private void updateConduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int conductId = Integer.parseInt(request.getParameter("conductId"));
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));
        Date date = Date.valueOf(request.getParameter("date"));
        String conductDescription = request.getParameter("conductDescription");
        String actionTaken = request.getParameter("actionTaken");

        TeacherConduct updatedConduct = new TeacherConduct(conductId, nationalId, date, conductDescription, actionTaken);
        conductDAO.updateTeacherConduct(updatedConduct);
        response.sendRedirect(request.getContextPath() + "/teacher-conduct/new?nationalId=" + nationalId);
    }

    private void deleteConduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int conductId = Integer.parseInt(request.getParameter("id"));
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));

        conductDAO.deleteTeacherConduct(conductId);
        response.sendRedirect(request.getContextPath() + "/teacher-conduct/new?nationalId=" + nationalId);
    }
}
