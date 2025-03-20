package pages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParentDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_PARENT_SQL = "INSERT INTO Parents (parent_id, name, phone_number, email, gender, religion) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_PARENT_BY_ID = "SELECT * FROM Parents WHERE parent_id = ?";
    private static final String SELECT_ALL_PARENTS = "SELECT * FROM Parents";
    private static final String DELETE_PARENT_SQL = "DELETE FROM Parents WHERE parent_id = ?";
    private static final String UPDATE_PARENT_SQL = "UPDATE Parents SET name = ?, phone_number = ?, email = ?, gender = ?, religion = ? WHERE parent_id = ?";
    
    private static final String SELECT_STUDENTS_BY_PARENT_ID = "SELECT student_id FROM ParentStudent WHERE parent_id = ?";
    // Other CRUD methods...
    public ParentDAO() {}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Insert parent
    public void insertParent(Parent parent) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PARENT_SQL)) {
            preparedStatement.setInt(1, parent.getParentId());
            preparedStatement.setString(2, parent.getName());
            preparedStatement.setString(3, parent.getPhoneNumber());
            preparedStatement.setString(4, parent.getEmail());
            preparedStatement.setString(5, parent.getGender());
            preparedStatement.setString(6, parent.getReligion());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    // Select parent by id
    public Parent selectParent(int parentId) {
        Parent parent = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PARENT_BY_ID)) {
            preparedStatement.setInt(1, parentId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                String gender = rs.getString("gender");
                String religion = rs.getString("religion");
                parent = new Parent(parentId, name, phoneNumber, email, gender, religion);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return parent;
    }

    // Select all parents
    
    public List<Parent> selectAllParents() {
        List<Parent> parents = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PARENTS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int parentId = rs.getInt("parent_id");
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                String gender = rs.getString("gender");
                String religion = rs.getString("religion");
                List<Integer> assignedStudents = selectStudentsByParentId(parentId);
                Parent parent = new Parent(parentId, name, phoneNumber, email, gender, religion, assignedStudents);
                parents.add(parent);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return parents;
    }


    // Delete parent
    public boolean deleteParent(int parentId) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PARENT_SQL)) {
            statement.setInt(1, parentId);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    // Update parent
    public boolean updateParent(Parent parent) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PARENT_SQL)) {
            statement.setString(1, parent.getName());
            statement.setString(2, parent.getPhoneNumber());
            statement.setString(3, parent.getEmail());
            statement.setString(4, parent.getGender());
            statement.setString(5, parent.getReligion());
            statement.setInt(6, parent.getParentId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
  
    private List<Integer> selectStudentsByParentId(int parentId) {
        List<Integer> studentIds = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENTS_BY_PARENT_ID)) {
            preparedStatement.setInt(1, parentId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                studentIds.add(rs.getInt("student_id"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return studentIds;
    }
    
    
    //private static final String SEARCH_PARENTS_SQL = "SELECT * FROM Parents WHERE ";

    public List<Parent> searchParents(String query, String filter) throws SQLException {
        List<Parent> parents = new ArrayList<>();
        String sql = buildSearchSQL(query, filter);
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             
            int parameterIndex = 1;
            if (query != null && !query.isEmpty()) {
                for (int i = 0; i < 5; i++) {
                    preparedStatement.setString(parameterIndex++, "%" + query + "%");
                }
            }

            if (filter != null && !filter.isEmpty()) {
                switch (filter) {
                    case "name":
                    case "phone_number":
                    case "email":
                    case "gender":
                    case "religion":
                        preparedStatement.setString(parameterIndex, "%" + query + "%");
                        break;
                }
            }

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("parent_id");
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                String gender = rs.getString("gender");
                String religion = rs.getString("religion");
                
                Parent parent = new Parent(id, name, phoneNumber, email, gender, religion);
                parent.setAssignedStudents(getAssignedStudents(id));
                
                parents.add(parent);
            }
        }
        return parents;
    }

    private List<Integer> getAssignedStudents(int parentId) throws SQLException {
        List<Integer> studentIds = new ArrayList<>();
        String sql = "SELECT student_id FROM ParentStudent WHERE parent_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, parentId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                studentIds.add(rs.getInt("student_id"));
            }
        }
        return studentIds;
    }

    private String buildSearchSQL(String query, String filter) {
        StringBuilder sql = new StringBuilder(SELECT_ALL_PARENTS);

        if ((query != null && !query.isEmpty()) || (filter != null && !filter.isEmpty())) {
            sql.append(" WHERE ");
        }

        if (query != null && !query.isEmpty()) {
            sql.append("name LIKE ? OR phone_number LIKE ? OR email LIKE ? OR gender LIKE ? OR religion LIKE ?");
        }

        if (filter != null && !filter.isEmpty()) {
            if (query != null && !query.isEmpty()) {
                sql.append(" AND ");
            }
            switch (filter) {
                case "name":
                    sql.append("name LIKE ?");
                    break;
                case "phoneNumber":
                    sql.append("phone_number LIKE ?");
                    break;
                case "email":
                    sql.append("email LIKE ?");
                    break;
                case "gender":
                    sql.append("gender LIKE ?");
                    break;
                case "religion":
                    sql.append("religion LIKE ?");
                    break;
            }
        }

        return sql.toString();
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
                    System.err.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
