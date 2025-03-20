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

@WebServlet("/parents/*")
public class ParentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ParentDAO parentDAO;

    public void init() {
        parentDAO = new ParentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        System.out.println("Action: " + action);

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertParent(request, response);
                    break;
                case "/delete":
                    deleteParent(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateParent(request, response);
                    break;
                case "/search":
                    searchParents(request, response);
                    break;
                default:
                    listParent(request, response);
                    break;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    private void listParent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Parent> listParent = parentDAO.selectAllParents();
        request.setAttribute("listParent", listParent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/parent-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/parent-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Parent existingParent = parentDAO.selectParent(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/parent-form.jsp");
        request.setAttribute("parent", existingParent);
        dispatcher.forward(request, response);
    }

    private void insertParent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int parentId = Integer.parseInt(request.getParameter("parentId"));
        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String religion = request.getParameter("religion");

        Parent newParent = new Parent(parentId, name, phoneNumber, email, gender, religion);
        parentDAO.insertParent(newParent);
        response.sendRedirect(request.getContextPath() + "/parents/list");
    }

    private void updateParent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String religion = request.getParameter("religion");

        Parent parent = new Parent(id, name, phoneNumber, email, gender, religion);
        parentDAO.updateParent(parent);
        response.sendRedirect(request.getContextPath() + "/parents/list");
    }

    private void deleteParent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        parentDAO.deleteParent(id);
        response.sendRedirect(request.getContextPath() + "/parents/list");
    }
    
    private void searchParents(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String query = request.getParameter("query");
        String filter = request.getParameter("filter");
        List<Parent> listParent = parentDAO.searchParents(query, filter);
        request.setAttribute("listParent", listParent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/parent-list.jsp");
        dispatcher.forward(request, response);
    }
}
