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

@WebServlet("/students/*")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    public void init() {
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
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertStudent(request, response);
                    break;
                case "/delete":
                    deleteStudent(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateStudent(request, response);
                    break;
                case "/search":
                    searchStudents(request, response);
                    break;
                case "/list":
                    listStudent(request, response);
                    break;
                default:
                    listStudent(request, response); 
                    break;
            }
        } catch (SQLException ex) {
            handleSQLException(ex, request, response);
        }
    }

    private void handleSQLException(SQLException ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errorMessage", ex.getMessage());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
        dispatcher.forward(request, response);
    }

    private void listStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Student> listStudent = studentDAO.selectAllStudents();
        request.setAttribute("listStudent", listStudent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student existingStudent = studentDAO.selectStudent(id);
        request.setAttribute("student", existingStudent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String lastName = request.getParameter("lastName");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String gender = request.getParameter("gender");
        String religion = request.getParameter("religion");
        String medicalHistory = request.getParameter("medicalHistory");
        String emergencyContact = request.getParameter("emergencyContact");
        String learningDisabilities = request.getParameter("learningDisabilities");
        String dateOfEnrollment = request.getParameter("dateOfEnrollment");
        String disabilityDetails = request.getParameter("disabilityDetails");

        Student newStudent = new Student(id, firstName, middleName, lastName, LocalDate.parse(dateOfBirth), email, phoneNumber, gender, religion, medicalHistory, emergencyContact, learningDisabilities, LocalDate.parse(dateOfEnrollment), disabilityDetails);
        studentDAO.insertStudent(newStudent);
        response.sendRedirect(request.getContextPath() + "/students/list"); // Redirect to list after insertion
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String lastName = request.getParameter("lastName");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String gender = request.getParameter("gender");
        String religion = request.getParameter("religion");
        String medicalHistory = request.getParameter("medicalHistory");
        String emergencyContact = request.getParameter("emergencyContact");
        String learningDisabilities = request.getParameter("learningDisabilities");
        String dateOfEnrollment = request.getParameter("dateOfEnrollment");
        String disabilityDetails = request.getParameter("disabilityDetails");

        Student student = new Student(id, firstName, middleName, lastName, LocalDate.parse(dateOfBirth), email, phoneNumber, gender, religion, medicalHistory, emergencyContact, learningDisabilities, LocalDate.parse(dateOfEnrollment), disabilityDetails);
        studentDAO.updateStudent(student);
        response.sendRedirect(request.getContextPath() + "/students/list"); 
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);
        response.sendRedirect(request.getContextPath() + "/students/list"); 
    }

    private void searchStudents(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String keyword = request.getParameter("keyword");
        List<Student> listStudent = studentDAO.searchStudents(keyword);
        request.setAttribute("listStudent", listStudent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-list.jsp");
        dispatcher.forward(request, response);
    }
}
