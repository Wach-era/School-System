package pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/trimesters/*")
public class TrimesterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TrimesterDAO trimesterDAO;

    public void init() {
        trimesterDAO = new TrimesterDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/new":
                    showNewForm(request, response);
                    break;
                default:
                    listTrimesters(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/update":
                    updateTrimester(request, response);
                    break;
                case "/insert":
                    insertTrimester(request, response);
                    break;
                default:
                    listTrimesters(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listTrimesters(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Trimester> trimesters = trimesterDAO.getAllTrimesters();
        request.setAttribute("trimesters", trimesters);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/trimester-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String trimesterId = request.getParameter("id");
        Trimester existingTrimester = trimesterDAO.getAllTrimesters().stream()
                .filter(t -> t.getTrimesterId().equals(trimesterId))
                .findFirst()
                .orElse(null);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/trimester-form.jsp");
        request.setAttribute("trimester", existingTrimester);
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/trimester-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertTrimester(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String trimesterId = request.getParameter("trimesterId");
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));

        Trimester newTrimester = new Trimester(trimesterId, startDate, endDate);
        trimesterDAO.addTrimester(newTrimester);
        response.sendRedirect("list");
    }

    private void updateTrimester(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String trimesterId = request.getParameter("trimesterId");
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));

        Trimester trimester = new Trimester(trimesterId, startDate, endDate);
        trimesterDAO.updateTrimester(trimester);
        response.sendRedirect("list");
    }
}
