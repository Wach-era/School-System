package pages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentClassEnrollmentDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_ENROLLMENT_SQL = "INSERT INTO StudentClassEnrollment (student_id, class_id, year_id, status, retake) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_ENROLLMENT_BY_ID = "SELECT * FROM StudentClassEnrollment WHERE enrollment_id = ?;";
    private static final String SELECT_ALL_ENROLLMENTS = "SELECT * FROM StudentClassEnrollment;";
    private static final String DELETE_ENROLLMENT_SQL = "DELETE FROM StudentClassEnrollment WHERE enrollment_id = ?;";
    private static final String UPDATE_ENROLLMENT_SQL = "UPDATE StudentClassEnrollment SET student_id = ?, class_id = ?, year_id = ?, status = ?, retake = ? WHERE enrollment_id = ?;";
    private static final String CHECK_CLASS_EXISTS_SQL = "SELECT 1 FROM Classes WHERE class_id = ?";
    private static final String CHECK_STUDENT_EXISTS_SQL = "SELECT 1 FROM Students WHERE student_id = ?";
    private static final String CHECK_YEAR_EXISTS_SQL = "SELECT 1 FROM AcademicYear WHERE year_id = ?";
    private static final String SELECT_MEMBERS_BY_CLASS_ID = 
    	    "SELECT e.student_id, CONCAT(s.first_name, ' ', IFNULL(s.middle_name, ''), ' ', s.last_name) AS student_name " +
    	    "FROM StudentClassEnrollment e " +
    	    "JOIN Students s ON e.student_id = s.student_id " +
    	    "WHERE e.class_id = ? AND e.year_id = (SELECT MAX(year_id) FROM AcademicYear);";

    private static final String NEXT_YEAR_ID_SQL = "SELECT MAX(year_id) + 1 FROM AcademicYear;";
    private static final String SELECT_CLASS_CAPACITY_SQL = "SELECT COUNT(*) AS enrolled, c.max_students FROM StudentClassEnrollment e JOIN Classes c ON e.class_id = c.class_id WHERE e.class_id = ? AND e.year_id = ?;";


    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertEnrollment(StudentClassEnrollment enrollment) throws SQLException, ClassFullException {
    	 if (isClassFull(enrollment.getClassId(), enrollment.getYearId())) {
             throw new ClassFullException("Class " + enrollment.getClassId() + " is full.");
         }
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ENROLLMENT_SQL)) {
            preparedStatement.setInt(1, enrollment.getStudentId());
            preparedStatement.setString(2, enrollment.getClassId());
            preparedStatement.setInt(3, enrollment.getYearId());
            preparedStatement.setString(4, enrollment.getStatus());
            preparedStatement.setBoolean(5, enrollment.isRetake());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public StudentClassEnrollment selectEnrollmentById(int enrollmentId) {
        StudentClassEnrollment enrollment = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ENROLLMENT_BY_ID)) {
            preparedStatement.setInt(1, enrollmentId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                enrollment = new StudentClassEnrollment(
                    rs.getInt("enrollment_id"),
                    rs.getInt("student_id"),
                    rs.getString("class_id"),
                    rs.getInt("year_id"),
                    rs.getString("status"),
                    rs.getBoolean("retake")
                );
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return enrollment;
    }

    public List<StudentClassEnrollment> selectAllEnrollments() {
        List<StudentClassEnrollment> enrollments = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ENROLLMENTS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                enrollments.add(new StudentClassEnrollment(
                    rs.getInt("enrollment_id"),
                    rs.getInt("student_id"),
                    rs.getString("class_id"),
                    rs.getInt("year_id"),
                    rs.getString("status"),
                    rs.getBoolean("retake")
                ));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return enrollments;
    }

    public boolean deleteEnrollment(int enrollmentId) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ENROLLMENT_SQL)) {
            statement.setInt(1, enrollmentId);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateEnrollment(StudentClassEnrollment enrollment) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ENROLLMENT_SQL)) {
            statement.setInt(1, enrollment.getStudentId());
            statement.setString(2, enrollment.getClassId());
            statement.setInt(3, enrollment.getYearId());
            statement.setString(4, enrollment.getStatus());
            statement.setBoolean(5, enrollment.isRetake());
            statement.setInt(6, enrollment.getEnrollmentId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean checkClassExists(String classId) {
        return checkExists(CHECK_CLASS_EXISTS_SQL, classId);
    }

    public boolean checkStudentExists(int studentId) {
        return checkExists(CHECK_STUDENT_EXISTS_SQL, studentId);
    }

    public boolean checkYearExists(int yearId) {
        return checkExists(CHECK_YEAR_EXISTS_SQL, yearId);
    }

    private boolean checkExists(String sql, Object parameter) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (parameter instanceof Integer) {
                preparedStatement.setInt(1, (Integer) parameter);
            } else if (parameter instanceof String) {
                preparedStatement.setString(1, (String) parameter);
            }
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public List<StudentClassEnrollment> selectMembersByClassId(String classId) {
        List<StudentClassEnrollment> members = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MEMBERS_BY_CLASS_ID)) {
            preparedStatement.setString(1, classId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String studentName = rs.getString("student_name");
                members.add(new StudentClassEnrollment(studentId, studentName));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return members;
    }

    public void updateEnrollmentForNewYear() throws SQLException {
        String selectEnrollmentsSQL = "SELECT * FROM StudentClassEnrollment WHERE year_id = (SELECT MAX(year_id) FROM AcademicYear);";
        String insertEnrollmentSQL = "INSERT INTO StudentClassEnrollment (student_id, class_id, year_id, status, retake) VALUES (?, ?, ?, ?, ?);";

        try (Connection connection = getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectEnrollmentsSQL);
             PreparedStatement insertStatement = connection.prepareStatement(insertEnrollmentSQL);
             Statement nextYearIdStatement = connection.createStatement()) {

            ResultSet rs = selectStatement.executeQuery();
            ResultSet nextYearIdRS = nextYearIdStatement.executeQuery(NEXT_YEAR_ID_SQL);
            nextYearIdRS.next();
            int nextYearId = nextYearIdRS.getInt(1);

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String currentClassId = rs.getString("class_id");
                String nextClassId = ClassProgression.getNextClass(currentClassId);
                String status = rs.getString("status");
                boolean retake = rs.getBoolean("retake");

                if (retake) {
                    // If the student is retaking the class, re-enroll in the same class
                    insertStatement.setInt(1, studentId);
                    insertStatement.setString(2, currentClassId);
                    insertStatement.setInt(3, nextYearId);
                    insertStatement.setString(4, status);
                    insertStatement.setBoolean(5, retake);
                    insertStatement.addBatch();
                } else if (nextClassId != null) {
                    // Enroll in the next class
                    insertStatement.setInt(1, studentId);
                    insertStatement.setString(2, nextClassId);
                    insertStatement.setInt(3, nextYearId);
                    insertStatement.setString(4, status);
                    insertStatement.setBoolean(5, retake);
                    insertStatement.addBatch();
                }
            }
            insertStatement.executeBatch();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    
    public boolean isClassFull(String classId, int yearId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLASS_CAPACITY_SQL)) {
            preparedStatement.setString(1, classId);
            preparedStatement.setInt(2, yearId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int enrolled = rs.getInt("enrolled");
                int maxStudents = rs.getInt("max_students");
                return enrolled >= maxStudents;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }
    
    public class ClassFullException extends Exception {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ClassFullException(String message) {
            super(message);
        }
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
