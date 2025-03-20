package pages;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/staff-attendance/*")
public class StaffAttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StaffAttendanceDAO attendanceDAO;

    @Override
    public void init() {
        attendanceDAO = new StaffAttendanceDAO();
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
                    deleteAttendance(request, response);
                    break;
                case "/list":
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
                    addAttendance(request, response);
                    break;
                case "/update":
                    updateAttendance(request, response);
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
        List<StaffAttendance> attendanceList = attendanceDAO.getStaffAttendanceRecords(staffId);
        request.setAttribute("attendanceList", attendanceList);
        request.getRequestDispatcher("/staff-attendance-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int attendanceId = Integer.parseInt(request.getParameter("attendanceId"));
        StaffAttendance existingAttendance = attendanceDAO.getStaffAttendanceById(attendanceId);
        request.setAttribute("attendance", existingAttendance);
        request.getRequestDispatcher("/staff-attendance-form.jsp").forward(request, response);
    }

    private void addAttendance(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int staffId = Integer.parseInt(request.getParameter("staffId"));
        Date date = Date.valueOf(request.getParameter("date"));
        String status = request.getParameter("status");

         new StaffAttendance(0, staffId, date, status);
        attendanceDAO.insertStaffAttendance(staffId, date, status);
        response.sendRedirect(request.getContextPath() + "/staff-attendance/new?staffId=" + staffId);
    }

    private void updateAttendance(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int attendanceId = Integer.parseInt(request.getParameter("attendanceId"));
        int staffId = Integer.parseInt(request.getParameter("staffId"));
        Date date = Date.valueOf(request.getParameter("date"));
        String status = request.getParameter("status");

        StaffAttendance attendance = new StaffAttendance(attendanceId, staffId, date, status);
        attendanceDAO.updateStaffAttendance(attendance);
        response.sendRedirect(request.getContextPath() + "/staff-attendance/new?staffId=" + staffId);
    }

    private void deleteAttendance(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int attendanceId = Integer.parseInt(request.getParameter("attendanceId"));
        int staffId = Integer.parseInt(request.getParameter("staffId"));

        attendanceDAO.deleteStaffAttendance(attendanceId);
        response.sendRedirect(request.getContextPath() + "/staff-attendance/new?staffId=" + staffId);
    }
}
