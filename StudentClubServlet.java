package pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/student-clubs/*")
public class StudentClubServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentClubDAO studentClubDAO;

    public void init() {
        studentClubDAO = new StudentClubDAO();
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
                    insertStudentClub(request, response);
                    break;
                case "/delete":
                    deleteStudentClub(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateStudentClub(request, response);
                    break;
                case "/remove":
                    removeMember(request, response);
                    break;
                default:
                    listStudentClubs(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listStudentClubs(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<StudentClub> listStudentClub = studentClubDAO.selectAllStudentClubs();
        request.setAttribute("listStudentClub", listStudentClub);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-club-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-club-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int studentClubId = Integer.parseInt(request.getParameter("student_club_id"));
        StudentClub existingStudentClub = studentClubDAO.selectStudentClub(studentClubId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-club-form.jsp");
        request.setAttribute("studentClub", existingStudentClub);
        dispatcher.forward(request, response);
    }

    private void insertStudentClub(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int studentId = Integer.parseInt(request.getParameter("student_id"));
        String clubName = request.getParameter("club_name");
        LocalDate joinDate = LocalDate.parse(request.getParameter("join_date"));

        if (!studentClubDAO.checkStudentExists(studentId)) {
            response.sendRedirect(request.getContextPath() + "/student-club-form.jsp?error=Student ID does not exist");
            return;
        }

        if (!studentClubDAO.checkClubExists(clubName)) {
            response.sendRedirect(request.getContextPath() + "/student-club-form.jsp?error=Club Name does not exist");
            return;
        }

        StudentClub newStudentClub = new StudentClub(0, studentId, clubName, joinDate);
        studentClubDAO.insertStudentClub(newStudentClub);
        response.sendRedirect(request.getContextPath() + "/student-clubs/list");
    }

    private void updateStudentClub(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int studentClubId = Integer.parseInt(request.getParameter("student_club_id"));
        int studentId = Integer.parseInt(request.getParameter("student_id"));
        String clubName = request.getParameter("club_name");
        LocalDate joinDate = LocalDate.parse(request.getParameter("join_date"));

        if (!studentClubDAO.checkStudentExists(studentId)) {
            response.sendRedirect(request.getContextPath() + "/student-club-form.jsp?error=Student ID does not exist");
            return;
        }

        if (!studentClubDAO.checkClubExists(clubName)) {
            response.sendRedirect(request.getContextPath() + "/student-club-form.jsp?error=Club Name does not exist");
            return;
        }

        StudentClub updatedStudentClub = new StudentClub(studentClubId, studentId, clubName, joinDate);
        studentClubDAO.updateStudentClub(updatedStudentClub);
        response.sendRedirect(request.getContextPath() + "/student-clubs/list");
    }

    private void deleteStudentClub(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int studentClubId = Integer.parseInt(request.getParameter("student_club_id"));
        studentClubDAO.deleteStudentClub(studentClubId);
        response.sendRedirect(request.getContextPath() + "/student-clubs/list");
    }
    
    private void removeMember(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int studentId = Integer.parseInt(request.getParameter("student_id"));
        String clubName = request.getParameter("club_name");
        studentClubDAO.removeMember(studentId, clubName);
        response.sendRedirect(request.getContextPath() + "/clubs/view-members?club_name=" + clubName);
    }
}
