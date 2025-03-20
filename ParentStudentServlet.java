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

@WebServlet("/parentStudent/*")
public class ParentStudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ParentStudentDAO parentStudentDAO;

    public void init() {
        parentStudentDAO = new ParentStudentDAO();
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
                case "/assign":
                    showAssignForm(request, response);
                    break;
                case "/insert":
                    insertParentStudent(request, response);
                    break;
                case "/delete":
                    deleteParentStudent(request, response);
                    break;
                case "/listByStudent":
                    listParentStudentByStudent(request, response);
                    break;
                default:
                    showAssignForm(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void showAssignForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/parent-assign-student.jsp");
        dispatcher.forward(request, response);
    }

    private void insertParentStudent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int parentId = Integer.parseInt(request.getParameter("parentId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));

        if (parentStudentDAO.parentExists(parentId) && parentStudentDAO.studentExists(studentId)) {
            parentStudentDAO.insertParentStudent(parentId, studentId);
            response.sendRedirect(request.getContextPath() + "/parents/list");
        } else {
            request.setAttribute("errorMessage", "Parent ID or Student ID does not exist.");
            showAssignForm(request, response);
        }
    }

    private void deleteParentStudent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int parentId = Integer.parseInt(request.getParameter("parentId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));

        parentStudentDAO.deleteParentStudent(parentId, studentId);
        response.sendRedirect(request.getContextPath() + "/parents/list");
    }
    
    private void listParentStudentByStudent(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        List<ParentStudent> parentStudents = parentStudentDAO.selectParentStudentByStudentId(studentId);

        // Generate HTML for the parent-student relationships
        StringBuilder html = new StringBuilder();
        html.append("<h4>Parents</h4>");
        if (parentStudents.isEmpty()) {
            html.append("<p>No parents found.</p>");
        } else {
            html.append("<table class='table table-bordered'>");
            html.append("<thead><tr><th>Parent ID</th><th>Parent Name</th></tr></thead>");
            html.append("<tbody>");
            for (ParentStudent parentStudent : parentStudents) {
                html.append("<tr>");
                html.append("<td>").append(parentStudent.getParentId()).append("</td>");
                html.append("<td>").append(parentStudent.getParentName()).append("</td>"); // Fix: Use parentName
                html.append("</tr>");
            }
            html.append("</tbody></table>");
        }

        // Set the response content type to HTML
        response.setContentType("text/html");
        response.getWriter().write(html.toString());
    }

}
