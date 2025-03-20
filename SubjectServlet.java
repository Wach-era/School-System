package pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/subjects/*")
public class SubjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SubjectDAO subjectDAO;

    public void init() {
        subjectDAO = new SubjectDAO();
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
                    listSubjects(request, response);
                    break;
                case "/listByStudent":
                    listSubjectsByStudent(request, response);
                    break;
                default:
                    listSubjects(request, response);
                    break;
            }
        } catch (SQLException ex) {
            handleSQLException(ex, request, response);
        }
    }

    private void listSubjects(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Subject> listSubject = subjectDAO.selectAllSubjects();
        request.setAttribute("listSubject", listSubject);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/subject-list.jsp");
        dispatcher.forward(request, response);
    }

    private void handleSQLException(SQLException ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errorMessage", ex.getMessage());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
        dispatcher.forward(request, response);
    }
    
    private void listSubjectsByStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        List<Subject> subjects = subjectDAO.selectSubjectsByStudentId(studentId);

        // Generate HTML for the subjects
        StringBuilder html = new StringBuilder();
        html.append("<h4>Subjects</h4>");
        if (subjects.isEmpty()) {
            html.append("<p>No subjects found.</p>");
        } else {
            html.append("<table class='table table-bordered'>");
            html.append("<thead><tr><th>Subject Name</th><th>Section</th></tr></thead>");
            html.append("<tbody>");
            for (Subject subject : subjects) {
                html.append("<tr>");
                html.append("<td>").append(subject.getSubjectName()).append("</td>");
                html.append("<td>").append(subject.getSection()).append("</td>");
                html.append("</tr>");
            }
            html.append("</tbody></table>");
        }

        response.setContentType("text/html");
        response.getWriter().write(html.toString());
    }
}
