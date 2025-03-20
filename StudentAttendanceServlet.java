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

@WebServlet("/student-attendance/*")
public class StudentAttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentAttendanceDAO attendanceDAO;

    @Override
    public void init() {
        attendanceDAO = new StudentAttendanceDAO();
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
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        List<StudentAttendance> attendanceList = attendanceDAO.getStudentAttendanceRecords(studentId);
        request.setAttribute("attendanceList", attendanceList);
        request.getRequestDispatcher("/student-attendance-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int attendanceId = Integer.parseInt(request.getParameter("attendanceId"));
        StudentAttendance existingAttendance = attendanceDAO.getStudentAttendanceById(attendanceId);
        request.setAttribute("attendance", existingAttendance);
        request.getRequestDispatcher("/student-attendance-form.jsp").forward(request, response);
    }

    private void addAttendance(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        Date date = Date.valueOf(request.getParameter("date"));
        String status = request.getParameter("status");

         new StudentAttendance(0, studentId, date, status);
        attendanceDAO.insertStudentAttendance(studentId, date, status);
        response.sendRedirect(request.getContextPath() + "/student-attendance/new?studentId=" + studentId);
    }

    private void updateAttendance(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int attendanceId = Integer.parseInt(request.getParameter("attendanceId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        Date date = Date.valueOf(request.getParameter("date"));
        String status = request.getParameter("status");

        StudentAttendance attendance = new StudentAttendance(attendanceId, studentId, date, status);
        attendanceDAO.updateStudentAttendance(attendance);
        response.sendRedirect(request.getContextPath() + "/student-attendance/new?studentId=" + studentId);
    }

    private void deleteAttendance(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int attendanceId = Integer.parseInt(request.getParameter("attendanceId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));

        attendanceDAO.deleteStudentAttendance(attendanceId);
        response.sendRedirect(request.getContextPath() + "/student-attendance/new?studentId=" + studentId);
    }
}
