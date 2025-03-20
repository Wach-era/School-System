package pages;

import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/clubs/*")
public class ClubServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ClubDAO clubDAO;

    public void init() {
        clubDAO = new ClubDAO();
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
                    insertClub(request, response);
                    break;
                case "/delete":
                    deleteClub(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateClub(request, response);
                    break;
                case "/view-members":
                    viewMembers(request, response);
                    break;
                case "/listByStudent":
                    listClubsByStudent(request, response);
                    break;
                default:
                    listClubs(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listClubs(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Club> listClub = clubDAO.selectAllClubsWithPatronDetails();
        request.setAttribute("listClub", listClub);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/club-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/club-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String clubName = request.getParameter("club_name");
        Club existingClub = clubDAO.selectClubByName(clubName);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/club-form.jsp");
        request.setAttribute("club", existingClub);
        dispatcher.forward(request, response);
    }

    private void insertClub(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String clubName = request.getParameter("club_name");
        Club newClub = new Club(clubName);
        clubDAO.insertClub(newClub);
        response.sendRedirect(request.getContextPath() + "/clubs/list");
    }

    private void updateClub(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String oldClubName = request.getParameter("old_club_name");
        String newClubName = request.getParameter("club_name");

        Club club = new Club(newClubName);
        clubDAO.updateClub(club, oldClubName);
        response.sendRedirect(request.getContextPath() + "/clubs/list");
    }

    private void deleteClub(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String clubName = request.getParameter("club_name");
        clubDAO.deleteClub(clubName);
        response.sendRedirect(request.getContextPath() + "/clubs/list");
    }
    
    private void viewMembers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String clubName = request.getParameter("club_name");
        List<StudentClub> members = clubDAO.selectMembersByClubName(clubName);
        request.setAttribute("members", members);
        request.setAttribute("club_name", clubName); // This will allow you to display the club name in the JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/club-members.jsp");
        dispatcher.forward(request, response);
    }
    
    private void listClubsByStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        List<StudentClub> clubs = clubDAO.selectClubsByStudentId(studentId);

        // Generate HTML for the clubs
        StringBuilder html = new StringBuilder();
        html.append("<h4>Clubs</h4>");
        if (clubs.isEmpty()) {
            html.append("<p>No clubs found.</p>");
        } else {
            html.append("<table class='table table-bordered'>");
            html.append("<thead><tr><th>Club Name</th><th>Join Date</th></tr></thead>");
            html.append("<tbody>");
            for (StudentClub club : clubs) {
                html.append("<tr>");
                html.append("<td>").append(club.getClubName()).append("</td>");
                html.append("<td>").append(club.getJoinDate()).append("</td>");
                html.append("</tr>");
            }
            html.append("</tbody></table>");
        }

        // Set the response content type to HTML
        response.setContentType("text/html");
        response.getWriter().write(html.toString());
    }

}
