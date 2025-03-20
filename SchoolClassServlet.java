package pages;

import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/classes/*")
public class SchoolClassServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SchoolClassDAO classDAO;
    private StudentClassEnrollmentDAO enrollmentDAO;


    public void init() {
        classDAO = new SchoolClassDAO();
        enrollmentDAO = new StudentClassEnrollmentDAO();

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
                    insertClass(request, response);
                    break;
                case "/delete":
                    deleteClass(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateClass(request, response);
                    break;
                case "/members":
                    listMembers(request, response);
                    break;
                default:
                    listClasses(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listClasses(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<SchoolClass> listClass = classDAO.selectAllClasses();
        request.setAttribute("listClass", listClass);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/class-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/class-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String classId = request.getParameter("class_id");
        SchoolClass existingClass = classDAO.selectClassById(classId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/class-form.jsp");
        request.setAttribute("schoolClass", existingClass);
        dispatcher.forward(request, response);
    }

    private void insertClass(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String classId = request.getParameter("class_id");
        int classTeacher = Integer.parseInt(request.getParameter("class_teacher"));
        int maxStudents = Integer.parseInt(request.getParameter("max_students"));
        String roomNumber = request.getParameter("room_number");

        if (classDAO.checkTeacherExists(classTeacher)) {
            SchoolClass newClass = new SchoolClass(classId, classTeacher, maxStudents, roomNumber);
            classDAO.insertClass(newClass);
            response.sendRedirect(request.getContextPath() + "/classes/list");
        } else {
            request.setAttribute("errorMessage", "Teacher with National ID " + classTeacher + " does not exist.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/class-form.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void updateClass(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String classId = request.getParameter("class_id");
        int classTeacher = Integer.parseInt(request.getParameter("class_teacher"));
        int maxStudents = Integer.parseInt(request.getParameter("max_students"));
        String roomNumber = request.getParameter("room_number");

        if (classDAO.checkTeacherExists(classTeacher)) {
            SchoolClass cls = new SchoolClass(classId, classTeacher, maxStudents, roomNumber);
            classDAO.updateClass(cls);
            response.sendRedirect(request.getContextPath() + "/classes/list");
        } else {
            request.setAttribute("errorMessage", "Teacher with National ID " + classTeacher + " does not exist.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/class-form.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void deleteClass(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String classId = request.getParameter("class_id");
        classDAO.deleteClass(classId);
        response.sendRedirect(request.getContextPath() + "/classes/list");
    }
    
    private void listMembers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String classId = request.getParameter("class_id");
        List<StudentClassEnrollment> listMembers = enrollmentDAO.selectMembersByClassId(classId);
        request.setAttribute("listMembers", listMembers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/class-members.jsp");
        dispatcher.forward(request, response);
    }
}
