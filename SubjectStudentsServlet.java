package pages;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/subject-students")
public class SubjectStudentsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentSubjectDAO studentSubjectDAO;

    public void init() {
        studentSubjectDAO = new StudentSubjectDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectName = request.getParameter("subjectName");

        try {
            List<Student> students = studentSubjectDAO.getStudentsBySubjectCode(subjectName);
            request.setAttribute("students", students);
            request.setAttribute("subjectCode", subjectName);
            RequestDispatcher dispatcher = request.getRequestDispatcher("subject-students-list.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
