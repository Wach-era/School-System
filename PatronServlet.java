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

@WebServlet("/patrons/*")
public class PatronServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PatronDAO patronDAO;

    public void init() {
        patronDAO = new PatronDAO();
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
                    insertPatron(request, response);
                    break;
                case "/delete":
                    deletePatron(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updatePatron(request, response);
                    break;
                default:
                    listPatrons(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listPatrons(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Patron> listPatron = patronDAO.selectAllPatrons();
        request.setAttribute("listPatrons", listPatron);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/patron-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/patron-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int patronId = Integer.parseInt(request.getParameter("patron_id"));
        Patron existingPatron = patronDAO.selectPatron(patronId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/patron-form.jsp");
        request.setAttribute("patron", existingPatron);
        dispatcher.forward(request, response);
    }

    private void insertPatron(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int nationalId = Integer.parseInt(request.getParameter("national_id"));
        String clubName = request.getParameter("club_name");

        if (!patronDAO.checkTeacherExists(nationalId)) {
            response.sendRedirect(request.getContextPath() + "/patron-form.jsp?error=National ID does not exist");
            return;
        }

        if (!patronDAO.checkClubExists(clubName)) {
            response.sendRedirect(request.getContextPath() + "/patron-form.jsp?error=Club Name does not exist");
            return;
        }

        Patron newPatron = new Patron(0, nationalId, clubName);
        patronDAO.insertPatron(newPatron);
        response.sendRedirect(request.getContextPath() + "/patrons/list");
    }

    private void updatePatron(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int patronId = Integer.parseInt(request.getParameter("patron_id"));
        int nationalId = Integer.parseInt(request.getParameter("national_id"));
        String clubName = request.getParameter("club_name");

        if (!patronDAO.checkTeacherExists(nationalId)) {
            response.sendRedirect(request.getContextPath() + "/patron-form.jsp?error=National ID does not exist");
            return;
        }

        if (!patronDAO.checkClubExists(clubName)) {
            response.sendRedirect(request.getContextPath() + "/patron-form.jsp?error=Club Name does not exist");
            return;
        }

        Patron updatedPatron = new Patron(patronId, nationalId, clubName);
        patronDAO.updatePatron(updatedPatron);
        response.sendRedirect(request.getContextPath() + "/patrons/list");
    }

    private void deletePatron(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int patronId = Integer.parseInt(request.getParameter("patron_id"));
        patronDAO.deletePatron(patronId);
        response.sendRedirect(request.getContextPath() + "/patrons/list");
    }
}
