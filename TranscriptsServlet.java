package pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@WebServlet("/transcripts")
public class TranscriptsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TranscriptDAO transcriptDAO;

    public void init() {
        transcriptDAO = new TranscriptDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentIdParam = request.getParameter("studentId");
        String trimesterIdParam = request.getParameter("trimesterId");
        
        int studentId = Integer.parseInt(studentIdParam);
        String trimesterId = trimesterIdParam != null ? trimesterIdParam : "1-2024"; // Default to trimester 1-2024

        try {
            List<ExamResult> results = transcriptDAO.getStudentResultsByTrimester(studentId, trimesterId);
            Map<String, List<ExamResult>> resultsBySubject = results.stream().collect(Collectors.groupingBy(ExamResult::getSubjectName));

            double totalScore = 0.0;
            int totalSubjects = resultsBySubject.size();
            String studentFullName = results.isEmpty() ? "" : results.get(0).getFirstName() + " " + results.get(0).getMiddleName() + " " + results.get(0).getLastName();
            Map<String, Double> averagedScores = new HashMap<>();

            for (Map.Entry<String, List<ExamResult>> entry : resultsBySubject.entrySet()) {
                List<ExamResult> subjectResults = entry.getValue();
                OptionalDouble averageScoreOpt = subjectResults.stream().mapToDouble(r -> (r.getScore() / r.getMaxScore()) * 100).average();
                double averageScore = averageScoreOpt.isPresent() ? averageScoreOpt.getAsDouble() : 0.0;
                averageScore = Math.min(averageScore, 100.0); // Ensure it doesn't exceed 100
                averagedScores.put(entry.getKey(), averageScore);
                totalScore += averageScore;
            }

            double gpa = calculateGPA(totalScore / totalSubjects);

            request.setAttribute("resultsBySubject", averagedScores);
            request.setAttribute("studentId", studentId);
            request.setAttribute("studentFullName", studentFullName);
            request.setAttribute("totalScore", Math.round(totalScore * 100.0) / 100.0);
            request.setAttribute("gpa", gpa);
            request.setAttribute("trimesters", transcriptDAO.getAllTrimesters());
            RequestDispatcher dispatcher = request.getRequestDispatcher("student-transcript.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error generating transcript.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private double calculateGPA(double averagePercentage) {
        if (averagePercentage >= 90) return 4.0;
        if (averagePercentage >= 80) return 3.5;
        if (averagePercentage >= 70) return 3.0;
        if (averagePercentage >= 60) return 2.5;
        if (averagePercentage >= 50) return 2.0;
        if (averagePercentage >= 40) return 1.5;
        return 1.0;
    }
}
