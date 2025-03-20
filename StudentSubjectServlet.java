package pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/student-subjects")
public class StudentSubjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentSubjectDAO studentSubjectDAO;
    private SubjectDAO subjectDAO;
    private StudentDAO studentDAO;

    public void init() {
        studentSubjectDAO = new StudentSubjectDAO();
        subjectDAO = new SubjectDAO();
        studentDAO = new StudentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "view":
                    viewStudentSubjects(request, response);
                    break;
                    case "deleteAll":
                        deleteAllSubjectsForStudent(request, response);
                        break;
                default:
                    listStudentSubjects(request, response);
                    break;
            }
        } catch (SQLException ex) {
            handleSQLException(ex, request, response);
        }
    }

    private void viewStudentSubjects(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        List<Subject> subjects = studentSubjectDAO.getSubjectsByStudentId(studentId);
        request.setAttribute("subjects", subjects);
        request.setAttribute("studentId", studentId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-subjects-list.jsp");
        dispatcher.forward(request, response);
    }
    

   private void deleteAllSubjectsForStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
    int studentId = Integer.parseInt(request.getParameter("studentId"));
    studentSubjectDAO.deleteAllSubjectsForStudent(studentId);
    response.sendRedirect(request.getContextPath() + "/student-subjects?action=view&studentId=" + studentId);
}
    private void listStudentSubjects(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Subject> listSubjects = subjectDAO.selectAllSubjects();
        request.setAttribute("listSubjects", listSubjects);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-subject-list.jsp");
        dispatcher.forward(request, response);
    }


    private void handleSQLException(SQLException ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errorMessage", ex.getMessage());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
        dispatcher.forward(request, response);
    }
}
