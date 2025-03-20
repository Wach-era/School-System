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
import java.util.stream.Collectors;

@WebServlet("/transcript/generate")
public class TranscriptServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TranscriptDAO transcriptDAO;

    public void init() {
        transcriptDAO = new TranscriptDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String classId = request.getParameter("classId");
        String trimesterId = request.getParameter("trimesterId");

        try {
            List<ExamResult> results = transcriptDAO.getResultsByClassAndTrimester(classId, trimesterId);

            // Group results by student ID and then by subject name
            Map<Integer, Map<String, List<ExamResult>>> resultsByStudent = results.stream()
                    .collect(Collectors.groupingBy(
                            ExamResult::getStudentId,
                            Collectors.groupingBy(ExamResult::getSubjectName)
                    ));

            Map<Integer, Map<String, Double>> studentAverageScores = calculateAverageScores(resultsByStudent);
            Map<Integer, Map<String, String>> studentGrades = calculateGrades(studentAverageScores);

            request.setAttribute("studentAverageScores", studentAverageScores);
            request.setAttribute("studentGrades", studentGrades);
            request.setAttribute("classId", classId);
            request.setAttribute("trimesterId", trimesterId);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/transcript.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error generating transcript.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    private Map<Integer, Map<String, Double>> calculateAverageScores(Map<Integer, Map<String, List<ExamResult>>> resultsByStudent) {
        Map<Integer, Map<String, Double>> studentAverageScores = new HashMap<>();

        for (Map.Entry<Integer, Map<String, List<ExamResult>>> studentEntry : resultsByStudent.entrySet()) {
            int studentId = studentEntry.getKey();
            Map<String, List<ExamResult>> resultsBySubject = studentEntry.getValue();
            Map<String, Double> averageScores = new HashMap<>();

            for (Map.Entry<String, List<ExamResult>> subjectEntry : resultsBySubject.entrySet()) {
                String subjectName = subjectEntry.getKey();
                List<ExamResult> subjectResults = subjectEntry.getValue();
                double totalPercentage = 0;

                for (ExamResult result : subjectResults) {
                    totalPercentage += (result.getScore() / result.getMaxScore()) * 100;
                }

                double averagePercentage = totalPercentage / subjectResults.size();
                averageScores.put(subjectName, averagePercentage);
            }

            studentAverageScores.put(studentId, averageScores);
        }

        return studentAverageScores;
    }


    private Map<Integer, Map<String, String>> calculateGrades(Map<Integer, Map<String, Double>> studentAverageScores) {
        Map<Integer, Map<String, String>> studentGrades = new HashMap<>();

        for (Integer studentId : studentAverageScores.keySet()) {
            Map<String, Double> averageScores = studentAverageScores.get(studentId);
            Map<String, String> grades = new HashMap<>();

            for (String subject : averageScores.keySet()) {
                double score = averageScores.get(subject);

                if (score >= 90) {
                    grades.put(subject, "A");
                } else if (score >= 80) {
                    grades.put(subject, "B");
                } else if (score >= 70) {
                    grades.put(subject, "C");
                } else if (score >= 60) {
                    grades.put(subject, "D");
                } else {
                    grades.put(subject, "F");
                }
            }

            studentGrades.put(studentId, grades);
        }

        return studentGrades;
    }
}
