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

@WebServlet("/teachers/*")
public class TeacherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TeacherDAO teacherDAO;

    public void init() {
        teacherDAO = new TeacherDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertTeacher(request, response);
                    break;
                case "/delete":
                    deleteTeacher(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateTeacher(request, response);
                    break;
                default:
                    listTeachers(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listTeachers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Teacher> listTeachers = teacherDAO.selectAllTeachers();
        request.setAttribute("listTeachers", listTeachers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));
        Teacher existingTeacher = teacherDAO.selectTeacher(nationalId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-form.jsp");
        request.setAttribute("teacher", existingTeacher);
        dispatcher.forward(request, response);
    }

    private void insertTeacher(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));
        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String lastName = request.getParameter("lastName");
        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String gender = request.getParameter("gender");
        String religion = request.getParameter("religion");
        String degrees = request.getParameter("degrees");
        String majors = request.getParameter("majors");
        String institution = request.getParameter("institution");
        LocalDate dateOfGraduation = LocalDate.parse(request.getParameter("dateOfGraduation"));
        String position = request.getParameter("position");
        LocalDate dateOfHire = LocalDate.parse(request.getParameter("dateOfHire"));
        double salary = Double.parseDouble(request.getParameter("salary"));
        String emergencyContact = request.getParameter("emergencyContact");
        String healthInformation = request.getParameter("healthInformation");
        String specialAccommodation = request.getParameter("specialAccommodation");

        Teacher newTeacher = new Teacher(0, nationalId, firstName, middleName, lastName, dateOfBirth, email,
                phoneNumber, gender, religion, degrees, majors, institution, dateOfGraduation, position, dateOfHire,
                salary, emergencyContact, healthInformation, specialAccommodation);
        teacherDAO.insertTeacher(newTeacher);
        response.sendRedirect(request.getContextPath() + "/teachers/list");
    }

    private void updateTeacher(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));
        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String lastName = request.getParameter("lastName");
        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String gender = request.getParameter("gender");
        String religion = request.getParameter("religion");
        String degrees = request.getParameter("degrees");
        String majors = request.getParameter("majors");
        String institution = request.getParameter("institution");
        LocalDate dateOfGraduation = LocalDate.parse(request.getParameter("dateOfGraduation"));
        String position = request.getParameter("position");
        LocalDate dateOfHire = LocalDate.parse(request.getParameter("dateOfHire"));
        double salary = Double.parseDouble(request.getParameter("salary"));
        String emergencyContact = request.getParameter("emergencyContact");
        String healthInformation = request.getParameter("healthInformation");
        String specialAccommodation = request.getParameter("specialAccommodation");

        Teacher updatedTeacher = new Teacher(teacherId, nationalId, firstName, middleName, lastName, dateOfBirth,
                email, phoneNumber, gender, religion, degrees, majors, institution, dateOfGraduation, position,
                dateOfHire, salary, emergencyContact, healthInformation, specialAccommodation);
        teacherDAO.updateTeacher(updatedTeacher);
        response.sendRedirect(request.getContextPath() + "/teachers/list");
    }

    private void deleteTeacher(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));
        teacherDAO.deleteTeacher(nationalId);
        response.sendRedirect(request.getContextPath() + "/teachers/list");
    }
}
