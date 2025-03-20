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

@WebServlet("/teacher-attendance/*")
public class TeacherAttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TeacherAttendanceDAO attendanceDAO;

    @Override
    public void init() {
        attendanceDAO = new TeacherAttendanceDAO();
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
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));
        List<TeacherAttendance> attendanceList = attendanceDAO.getTeacherAttendanceRecords(nationalId);
        request.setAttribute("attendanceList", attendanceList);
        request.getRequestDispatcher("/teacher-attendance-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int attendanceId = Integer.parseInt(request.getParameter("attendanceId"));
        TeacherAttendance existingAttendance = attendanceDAO.getTeacherAttendanceById(attendanceId);
        request.setAttribute("attendance", existingAttendance);
        request.getRequestDispatcher("/teacher-attendance-form.jsp").forward(request, response);
    }

    private void addAttendance(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));
        Date date = Date.valueOf(request.getParameter("date"));
        String status = request.getParameter("status");

         new TeacherAttendance(0, nationalId, date, status);
        attendanceDAO.insertTeacherAttendance(nationalId, date, status);
        response.sendRedirect(request.getContextPath() + "/teacher-attendance/new?nationalId=" + nationalId);
    }

    private void updateAttendance(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int attendanceId = Integer.parseInt(request.getParameter("attendanceId"));
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));
        Date date = Date.valueOf(request.getParameter("date"));
        String status = request.getParameter("status");

        TeacherAttendance attendance = new TeacherAttendance(attendanceId, nationalId, date, status);
        attendanceDAO.updateTeacherAttendance(attendance);
        response.sendRedirect(request.getContextPath() + "/teacher-attendance/new?nationalId=" + nationalId);
    }

    private void deleteAttendance(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int attendanceId = Integer.parseInt(request.getParameter("attendanceId"));
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));

        attendanceDAO.deleteTeacherAttendance(attendanceId);
        response.sendRedirect(request.getContextPath() + "/teacher-attendance/new?nationalId=" + nationalId);
    }
}
