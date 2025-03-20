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

@WebServlet("/student-subject/*")
public class StudentSubjectsServlet extends HttpServlet {
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
        String action = request.getPathInfo();

        if (action == null) {
            action = "/list";
        }

        try {
            switch (action) {
                case "/list":
                    showAssignForm(request, response);
                    break;
                case "/assign":
                    assignSubjectToStudent(request, response);
                    break;
                default:
                    showAssignForm(request, response);
                    break;
            }
        } catch (SQLException ex) {
            handleSQLException(ex, request, response);
        }
    }

    private void showAssignForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Subject> listSubjects = subjectDAO.selectAllSubjects();
        request.setAttribute("listSubjects", listSubjects);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-subject-list.jsp");
        dispatcher.forward(request, response);
    }

    private void assignSubjectToStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));

        // Check if the student exists
        if (!studentDAO.studentExists(studentId)) {
            request.setAttribute("errorMessage", "Student ID does not exist.");
            showAssignForm(request, response);
            return;
        }

        String[] subjects = request.getParameterValues("subjects");
        if (subjects != null) {
            List<String> subjectList = new ArrayList<>();
            for (String subject : subjects) {
                subjectList.add(subject);
            }
            studentSubjectDAO.assignSubjectsToStudent(studentId, subjectList);
            request.setAttribute("message", "Subjects assigned successfully.");
        } else {
            request.setAttribute("errorMessage", "No subjects selected.");
        }
        showAssignForm(request, response);
    }

    private void handleSQLException(SQLException ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errorMessage", ex.getMessage());
        List<Subject> listSubjects = subjectDAO.selectAllSubjects();
        request.setAttribute("listSubjects", listSubjects);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-subject-list.jsp");
        dispatcher.forward(request, response);
    }
}
