package pages;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/fees/*")
public class FeeStructureServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FeeStructureDAO feeStructureDAO;

    public void init() {
        feeStructureDAO = new FeeStructureDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        try {
            switch (action) {
                case "/view":
                    viewFeeStructure(request, response);
                    break;
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/update":
                    showUpdateForm(request, response);
                    break;
                case "/delete":
                    deleteFee(request, response);
                    break;
                case "/deleteAll":
                    deleteAllFees(request, response);
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
        if (action == null) {
            action = "/list";
        }

        try {
            switch (action) {
                case "/insert":
                    insertFee(request, response);
                    break;
                case "/update":
                    updateFee(request, response);
                    break;
                default:
                    listTrimesters(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listTrimesters(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        // Implementation for listing trimesters
    }

    private void viewFeeStructure(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String trimesterId = request.getParameter("trimesterId");
        List<FeeStructure> fees = feeStructureDAO.getFeesByTrimester(trimesterId);
        request.setAttribute("fees", fees);
        request.setAttribute("trimesterId", trimesterId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/viewFeeStructure.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/fees.jsp");
        dispatcher.forward(request, response);
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String trimesterId = request.getParameter("trimesterId");
        List<FeeStructure> fees = feeStructureDAO.getFeesByTrimester(trimesterId);
        request.setAttribute("fees", fees);
        request.setAttribute("trimesterId", trimesterId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/fees.jsp");
        dispatcher.forward(request, response);
    }

    private void insertFee(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String trimesterId = request.getParameter("trimesterId");
        String[] descriptions = request.getParameterValues("description");
        String[] amounts = request.getParameterValues("amount");

        for (int i = 0; i < descriptions.length; i++) {
            String description = descriptions[i];
            double amount = Double.parseDouble(amounts[i]);
            FeeStructure fee = new FeeStructure(trimesterId, description, amount);
            feeStructureDAO.addFee(fee);
        }
        response.sendRedirect(request.getContextPath() + "/fees/view?trimesterId=" + trimesterId);
    }

    private void updateFee(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String trimesterId = request.getParameter("trimesterId");
        String[] descriptions = request.getParameterValues("description");
        String[] amounts = request.getParameterValues("amount");

        List<FeeStructure> fees = new ArrayList<>();
        for (int i = 0; i < descriptions.length; i++) {
            String description = descriptions[i];
            double amount = Double.parseDouble(amounts[i]);
            FeeStructure fee = new FeeStructure(trimesterId, description, amount);
            fees.add(fee);
        }
        feeStructureDAO.updateFeesByTrimesterId(trimesterId, fees);
        response.sendRedirect(request.getContextPath() + "/fees/view?trimesterId=" + trimesterId);
    }

    private void deleteFee(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int feeId = Integer.parseInt(request.getParameter("feeId"));
        feeStructureDAO.deleteFee(feeId);
        String trimesterId = request.getParameter("trimesterId");
        response.sendRedirect(request.getContextPath() + "/fees/view?trimesterId=" + trimesterId);
    }

    private void deleteAllFees(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String trimesterId = request.getParameter("trimesterId");
        feeStructureDAO.deleteFeesByTrimesterId(trimesterId);
        response.sendRedirect(request.getContextPath() + "/fees");
    }
}
