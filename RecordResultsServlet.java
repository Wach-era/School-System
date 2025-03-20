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

@WebServlet("/submitResults/*")
public class RecordResultsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ExamResultDAO examResultDAO;

    public void init() {
        examResultDAO = new ExamResultDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String examId = request.getParameter("examId");
        if (examId == null || examId.isEmpty()) {
            request.setAttribute("errorMessage", "Exam ID is required.");
            response.sendRedirect(request.getContextPath() + "/exam/list"); // Redirect to exam list if no examId is provided
            return;
        }

        try {
            List<Student> students = examResultDAO.getStudentsByExamId(examId);
            request.setAttribute("students", students);
            request.setAttribute("examId", examId);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/record-results.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving students.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String examId = request.getParameter("examId");
        String[] studentIds = request.getParameterValues("studentIds");
        String[] scores = request.getParameterValues("scores");

        if (studentIds == null || scores == null || studentIds.length != scores.length) {
            request.setAttribute("errorMessage", "Invalid input.");
            request.setAttribute("examId", examId);
            try {
                List<Student> students = examResultDAO.getStudentsByExamId(examId);
                request.setAttribute("students", students);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("/record-results.jsp").forward(request, response);
            return;
        }

        try {
            double maxScore = examResultDAO.getMaxScore(examId);
            StringBuilder warnings = new StringBuilder();

            for (int i = 0; i < studentIds.length; i++) {
                int studentId = Integer.parseInt(studentIds[i]);
                double score = Double.parseDouble(scores[i]);

                if (score > maxScore) {
                    warnings.append("Score for student ID ").append(studentId).append(" exceeds the maximum score. ");
                    continue;
                }

                ExamResult result = new ExamResult();
                result.setExamId(examId);
                result.setStudentId(studentId);
                result.setScore(score);

                if (examResultDAO.studentHasResult(examId, studentId)) {
                    examResultDAO.updateResult(result);
                } else {
                    examResultDAO.addResult(result);
                }
            }

            if (warnings.length() > 0) {
                request.setAttribute("warningMessage", warnings.toString());
            } else {
                request.setAttribute("successMessage", "Results recorded successfully.");
            }
            doGet(request, response); 
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error recording exam results.");
            request.setAttribute("examId", examId);
            try {
                List<Student> students = examResultDAO.getStudentsByExamId(examId);
                request.setAttribute("students", students);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            request.getRequestDispatcher("/record-results.jsp").forward(request, response);
        }
    }
}
