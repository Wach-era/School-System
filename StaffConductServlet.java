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

@WebServlet("/staff-conduct/*")
public class StaffConductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StaffConductDAO conductDAO;

    @Override
    public void init() {
        conductDAO = new StaffConductDAO();
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
        int staffId = Integer.parseInt(request.getParameter("staffId"));
    	 List<StaffConduct> listConduct = conductDAO.getStaffConductRecords(staffId);
         request.setAttribute("listConduct", listConduct);
         request.getRequestDispatcher("/staff-conduct-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int conductId = Integer.parseInt(request.getParameter("id"));
        StaffConduct existingConduct = conductDAO.getStaffConductById(conductId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/staff-conduct-form.jsp");
        request.setAttribute("conduct", existingConduct);
        dispatcher.forward(request, response);
    }

    private void addConduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int staffId = Integer.parseInt(request.getParameter("staffId"));
        Date date = Date.valueOf(request.getParameter("date"));
        String conductDescription = request.getParameter("conductDescription");
        String actionTaken = request.getParameter("actionTaken");

        StaffConduct newConduct = new StaffConduct(0, staffId, date, conductDescription, actionTaken);
        conductDAO.insertStaffConduct(newConduct);
        response.sendRedirect(request.getContextPath() + "/staff-conduct/new?staffId=" + staffId);
    }

    private void updateConduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int conductId = Integer.parseInt(request.getParameter("conductId"));
        int staffId = Integer.parseInt(request.getParameter("staffId"));
        Date date = Date.valueOf(request.getParameter("date"));
        String conductDescription = request.getParameter("conductDescription");
        String actionTaken = request.getParameter("actionTaken");

        StaffConduct updatedConduct = new StaffConduct(conductId, staffId, date, conductDescription, actionTaken);
        conductDAO.updateStaffConduct(updatedConduct);
        response.sendRedirect(request.getContextPath() + "/staff-conduct/new?staffId=" + staffId);
    }

    private void deleteConduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int conductId = Integer.parseInt(request.getParameter("id"));
        int staffId = Integer.parseInt(request.getParameter("staffId"));

        conductDAO.deleteStaffConduct(conductId);
        response.sendRedirect(request.getContextPath() + "/staff-conduct/new?staffId=" + staffId);
    }
}
