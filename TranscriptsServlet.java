package pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/transcripts")
public class TranscriptsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TranscriptDAO transcriptDAO;

    public void init() {
        transcriptDAO = new TranscriptDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentIdParam = request.getParameter("studentId");
        String trimesterIdParam = request.getParameter("trimesterId");

        int studentId = Integer.parseInt(studentIdParam);
        String trimesterId = trimesterIdParam != null ? trimesterIdParam : "1-2024";

        try {
            List<ExamResult> currentResults = transcriptDAO.getStudentResultsByTrimester(studentId, trimesterId);
            List<Trimester> allTrimesters = transcriptDAO.getAllTrimesters(); // Fetch trimesters only once

            if (currentResults.isEmpty()) {
                request.setAttribute("resultsBySubject", new HashMap<>());
                request.setAttribute("studentId", studentId);
                request.setAttribute("studentFullName", "");
                request.setAttribute("totalScore", 0.0);
                request.setAttribute("gpa", 0.0);
                request.setAttribute("trimesters", sortTrimesters(allTrimesters)); // Pass sorted trimesters
                request.setAttribute("historicalGPA", new TreeMap<>());
                request.setAttribute("performanceTrend", "Neutral");
                request.setAttribute("badges", new ArrayList<>());
                request.setAttribute("classAverages", new HashMap<>());
                request.setAttribute("currentTrimester", trimesterId);
                request.setAttribute("studentGrades", new HashMap<>());

                RequestDispatcher dispatcher = request.getRequestDispatcher("transcript.jsp");
                dispatcher.forward(request, response);
                return;
            }

            Map<String, List<ExamResult>> resultsBySubject = currentResults.stream()
                    .collect(Collectors.groupingBy(ExamResult::getSubjectName));

            Map<String, Double> averagedScores = new LinkedHashMap<>();
            double totalScore = 0.0;

            for (Map.Entry<String, List<ExamResult>> entry : resultsBySubject.entrySet()) {
                double averageScore = entry.getValue().stream()
                        .mapToDouble(r -> (r.getScore() / r.getMaxScore()) * 100)
                        .average()
                        .orElse(0.0);
                averagedScores.put(entry.getKey(), Math.min(averageScore, 100.0));
                totalScore += averageScore;
            }

            double gpa = calculateGPA(totalScore / resultsBySubject.size());
            String studentFullName = currentResults.get(0).getFirstName() + " " + currentResults.get(0).getLastName();

            Map<String, Double> historicalGPA = new LinkedHashMap<>();
            List<Trimester> sortedTrimesters = sortTrimesters(allTrimesters);

            for (Trimester t : sortedTrimesters) {
                List<ExamResult> historicalResults = transcriptDAO.getStudentResultsByTrimester(studentId, t.getTrimesterId());
                if (!historicalResults.isEmpty()) {
                    double trimesterAvg = historicalResults.stream()
                            .mapToDouble(r -> (r.getScore() / r.getMaxScore()) * 100)
                            .average()
                            .orElse(0.0);
                    historicalGPA.put("T" + t.getTrimesterId().split("-")[0] + " " + t.getTrimesterId().split("-")[1], calculateGPA(trimesterAvg));
                }
            }

            String performanceTrend = calculatePerformanceTrend(historicalGPA);
            List<String> badges = calculateBadges(averagedScores, gpa);
            Map<String, Double> classAverages = calculateClassAverages(trimesterId);

            Map<Integer, Map<String, Double>> studentAverageScores = new HashMap<>();
            Map<String, Double> studentScores = new HashMap<>();
            for(Map.Entry<String, Double> entry : averagedScores.entrySet()){
                studentScores.put(entry.getKey(), entry.getValue());
            }
            studentAverageScores.put(studentId, studentScores);
            Map<Integer, Map<String, String>> studentGrades = calculateGrades(studentAverageScores);

            request.setAttribute("resultsBySubject", averagedScores);
            request.setAttribute("studentId", studentId);
            request.setAttribute("studentFullName", studentFullName);
            request.setAttribute("totalScore", Math.round(totalScore * 100.0) / 100.0);
            request.setAttribute("gpa", gpa);
            request.setAttribute("trimesters", sortTrimesters(allTrimesters)); // Pass sorted trimesters
            request.setAttribute("historicalGPA", historicalGPA);
            request.setAttribute("performanceTrend", performanceTrend);
            request.setAttribute("badges", badges);
            request.setAttribute("classAverages", classAverages);
            request.setAttribute("currentTrimester", trimesterId);
            request.setAttribute("studentGrades", studentGrades);

            RequestDispatcher dispatcher = request.getRequestDispatcher("transcript.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error generating transcript: " + e.getMessage());
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

    private String calculatePerformanceTrend(Map<String, Double> historicalGPA) {
        if (historicalGPA.size() < 2) return "Neutral";

        List<Double> gpas = new ArrayList<>(historicalGPA.values());
        double current = gpas.get(0);
        double previous = gpas.get(1);

        if (current > previous + 0.3) return "Improving";
        if (current < previous - 0.3) return "Declining";
        return "Stable";
    }

    private List<String> calculateBadges(Map<String, Double> subjectScores, double gpa) {
        List<String> badges = new ArrayList<>();

        if (gpa >= 3.5) badges.add("Honor Roll");
        if (gpa >= 3.8) badges.add("High Honors");

        long excellentSubjects = subjectScores.values().stream()
                .filter(score -> score >= 90)
                .count();

        if (excellentSubjects >= 3) badges.add("Multi-Subject Expert");
        else if (excellentSubjects >= 1) badges.add("Subject Expert");

        if (subjectScores.values().stream().noneMatch(score -> score < 70)) {
            badges.add("Consistent Performer");
        }

        return badges;
    }

    private Map<String, Double> calculateClassAverages(String trimesterId) throws SQLException {
        Map<String, Double> averages = new LinkedHashMap<>();
        List<ExamResult> allClassResults = transcriptDAO.getResultsByTrimester(trimesterId);

        if (allClassResults == null || allClassResults.isEmpty()) {
            return averages;
        }

        allClassResults.stream()
                .collect(Collectors.groupingBy(
                        ExamResult::getSubjectName,
                        LinkedHashMap::new,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                results -> {
                                    double avg = results.stream()
                                            .mapToDouble(r -> (r.getScore() / r.getMaxScore()) * 100)
                                            .average()
                                            .orElse(0.0);
                                    return Math.round(avg * 10) / 10.0;
                                }
                        )
                ))
                .forEach(averages::put);

        return averages;
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
    
    
    private List<Trimester> sortTrimesters(List<Trimester> trimesters) {
        trimesters.sort((t1, t2) -> {
            String[] parts1 = t1.getTrimesterId().split("-");
            String[] parts2 = t2.getTrimesterId().split("-");

            int year1 = Integer.parseInt(parts1[1]);
            int year2 = Integer.parseInt(parts2[1]);
            int term1 = Integer.parseInt(parts1[0]);
            int term2 = Integer.parseInt(parts2[0]);

            // Sort by year descending, then by trimester ascending
            if (year1 != year2) {
                return Integer.compare(year2, year1); // Newest years first
            } else {
                return Integer.compare(term1, term2); // T1 before T2 before T3
            }
        });
        return trimesters;
    }
}