package pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

@WebServlet("/exams/*")
public class ExamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ExamDAO examDAO;
    private SubjectDAO subjectDAO;
    private SchoolClassDAO classDAO;
    private ExamResultDAO examResultDAO;

    public void init() {
        examDAO = new ExamDAO();
        subjectDAO = new SubjectDAO();
        classDAO = new SchoolClassDAO();
        examResultDAO = new ExamResultDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
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
                    insertExam(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateExam(request, response);
                    break;
                case "/delete":
                    deleteExam(request, response);
                    break;
                case "/viewResults":
                    viewResults(request, response);
                    break;
                case "/updateResults":
                    updateResults(request, response);
                    break;
                case "/deleteResult":
                    deleteResult(request, response);
                    break;
                case "/list":
                default:
                    listExams(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Subject> subjects = subjectDAO.listAllSubjects();
        List<SchoolClass> classes = classDAO.selectAllClasses();
        request.setAttribute("subjects", subjects);
        request.setAttribute("classes", classes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/exam-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertExam(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String examId = request.getParameter("examId");
        String subjectName = request.getParameter("subjectName");
        Date examDate = Date.valueOf(request.getParameter("examDate"));
        double maxScore = Double.parseDouble(request.getParameter("maxScore"));
        String classId = request.getParameter("classId");

        Exam newExam = new Exam(examId, subjectName, examDate, maxScore, classId);
        examDAO.insertExam(newExam);
        response.sendRedirect(request.getContextPath() + "/exams/list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String examId = request.getParameter("examId");
        Exam existingExam = examDAO.selectExam(examId);
        List<Subject> subjects = subjectDAO.listAllSubjects();
        List<SchoolClass> classes = classDAO.selectAllClasses();
        request.setAttribute("exam", existingExam);
        request.setAttribute("subjects", subjects);
        request.setAttribute("classes", classes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/exam-form.jsp");
        dispatcher.forward(request, response);
    }

    private void updateExam(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String examId = request.getParameter("examId");
        String subjectName = request.getParameter("subjectName");
        Date examDate = Date.valueOf(request.getParameter("examDate"));
        double maxScore = Double.parseDouble(request.getParameter("maxScore"));
        String classId = request.getParameter("classId");

        Exam exam = new Exam(examId, subjectName, examDate, maxScore, classId);
        boolean isUpdated = examDAO.updateExam(exam);
        response.sendRedirect(request.getContextPath() + "/exams/list");
    }

    private void deleteExam(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String examId = request.getParameter("examId");
        examDAO.deleteExam(examId);
        response.sendRedirect(request.getContextPath() + "/exams/list");
    }

    private void listExams(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Exam> listExams = examDAO.listAllExams();
        request.setAttribute("listExams", listExams);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/exam-list.jsp");
        dispatcher.forward(request, response);
    }

    private void viewResults(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String examId = request.getParameter("examId");
        List<ExamResult> results = examResultDAO.getResultsByExamId(examId);
        request.setAttribute("results", results);
        request.setAttribute("examId", examId);
        request.setAttribute("successMessage", request.getParameter("successMessage"));
        request.setAttribute("errorMessage", request.getParameter("errorMessage"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view-results.jsp");
        dispatcher.forward(request, response);
    }

    private void updateResults(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String examId = request.getParameter("examId");
        String[] resultIds = request.getParameterValues("resultIds");
        String[] scores = request.getParameterValues("scores");

        // Fetch the max score for the exam
        double maxScore = examResultDAO.getMaxScore(examId);

        try {
            for (int i = 0; i < resultIds.length; i++) {
                double score = Double.parseDouble(scores[i]);

                // Check if the score is greater than the max score
                if (score > maxScore) {
                    response.sendRedirect(request.getContextPath() + "/exams/viewResults?examId=" + examId + "&errorMessage=Score cannot exceed max score of " + maxScore + ".");
                    return;
                }

                ExamResult result = new ExamResult();
                result.setResultId(Integer.parseInt(resultIds[i]));
                result.setScore(score);
                examResultDAO.updateResult(result);
            }
            response.sendRedirect(request.getContextPath() + "/exams/viewResults?examId=" + examId + "&successMessage=Results updated successfully.");
        } catch (SQLException e) {
            response.sendRedirect(request.getContextPath() + "/exams/viewResults?examId=" + examId + "&errorMessage=Error updating results.");
        }
    }


    private void deleteResult(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int resultId = Integer.parseInt(request.getParameter("resultId"));
        String examId = request.getParameter("examId");

        try {
            examResultDAO.deleteResult(resultId);
            response.sendRedirect(request.getContextPath() + "/exams/viewResults?examId=" + examId + "&successMessage=Result deleted successfully.");
        } catch (SQLException e) {
            response.sendRedirect(request.getContextPath() + "/exams/viewResults?examId=" + examId + "&errorMessage=Error deleting result.");
        }
    }
}
