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

@WebServlet("/teacher-subject/*")
public class TeacherSubjectsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TeacherSubjectDAO teacherSubjectDAO;
    private SubjectDAO subjectDAO;
    private TeacherDAO teacherDAO;

    public void init() {
        teacherSubjectDAO = new TeacherSubjectDAO();
        subjectDAO = new SubjectDAO();
        teacherDAO = new TeacherDAO();
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
                    assignSubjectToTeacher(request, response);
                    break;
                case "/view":
                    viewTeacherSubjects(request, response);
                    break;
                case "/deleteSubject":
                    deleteTeacherSubject(request, response);
                    break;
                case "/subject-teachers":
                    viewTeachersBySubject(request, response);
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-subject-list.jsp");
        dispatcher.forward(request, response);
    }

    private void assignSubjectToTeacher(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));

        // Check if the teacher exists
        if (!teacherDAO.checkTeacherExists(nationalId)) {
            request.setAttribute("errorMessage", "Teacher National ID does not exist.");
            showAssignForm(request, response);
            return;
        }

        String[] subjects = request.getParameterValues("subjects");
        if (subjects != null) {
            List<String> subjectList = new ArrayList<>();
            for (String subject : subjects) {
                subjectList.add(subject);
            }
            teacherSubjectDAO.assignSubjectsToTeacher(nationalId, subjectList);
            request.setAttribute("message", "Subjects assigned successfully.");
        } else {
            request.setAttribute("errorMessage", "No subjects selected.");
        }
        showAssignForm(request, response);
    }
    
    private void viewTeacherSubjects(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String nationalId = request.getParameter("nationalId");
        List<Subject> subjects = teacherSubjectDAO.getSubjectsByTeacherId(nationalId);
        request.setAttribute("subjects", subjects);
        request.setAttribute("nationalId", nationalId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-subjects-view.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteTeacherSubject(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String nationalId = request.getParameter("nationalId");
        String subjectName = request.getParameter("subjectName");
        teacherSubjectDAO.deleteSubjectForTeacher(nationalId, subjectName);
        response.sendRedirect(request.getContextPath() + "/teacher-subject/view?nationalId=" + nationalId);
    }

    private void handleSQLException(SQLException ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errorMessage", ex.getMessage());
        List<Subject> listSubjects = subjectDAO.selectAllSubjects();
        request.setAttribute("listSubjects", listSubjects);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-subject-list.jsp");
        dispatcher.forward(request, response);
    }
    
    private void viewTeachersBySubject(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String subjectName = request.getParameter("subjectName");
        List<Teacher> teachers = teacherSubjectDAO.getTeachersBySubjectName(subjectName);
        request.setAttribute("teachers", teachers);
        request.setAttribute("subjectName", subjectName);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/subject-teachers-view.jsp");
        dispatcher.forward(request, response);
    }
}
