package istudentmanagementsystem;

import java.sql.*;

public class JavaDb {
    
    private final Connection connection;

    public JavaDb(Connection connection) throws SQLException {
        this.connection = connection;
        initializeDatabase();
    }

     private void initializeDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS student (" +
                       "student_id INTEGER(6) PRIMARY KEY, " +
                       "name VARCHAR(100) NOT NULL, " +
                       "grade VARCHAR(1))");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS marks (" +
                       "record_id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                       "student_id INTEGER, " +
                       "marks int(3) NOT NULL, " +
                       "FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE)");
        }
    }

    public void addStudent(int id, String name, String mark) throws SQLException {
        String sql = "INSERT INTO student (student_id, name) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error");
        }
    }
    
    public void addStudent(int id, String name, double mark) throws SQLException {
        String sql = "INSERT INTO student (student_id, name) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error");
        }
    }

    public void updateGrade(int studentId, String grade) throws SQLException {
        String sql = "UPDATE student SET grade = ? WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, grade);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error");
        }
    }

   
    public void updateStudent(int id, String newName, String newMark) throws SQLException {
        String sql = "UPDATE student SET name = ? WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM student WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public double getMarks(int studentId) throws SQLException {
        String sql = "SELECT marks FROM marks WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("marks");
            }
            return 0; // Return 0 if no marks found
        }
    }
    
    public void setMarks(int studentId,double marks) throws SQLException {
        // First check if marks record exists for this student
        String checkSql = "SELECT record_id FROM marks WHERE student_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, studentId);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                // Update existing marks
                String updateSql = "UPDATE marks SET marks = ? WHERE student_id = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                    updateStmt.setDouble(1, (double) marks);
                    updateStmt.setInt(2, studentId);
                    updateStmt.executeUpdate();
                }
            } else {
                // Insert new marks record
                String insertSql = "INSERT INTO marks (student_id, marks) VALUES (?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, studentId);
                    insertStmt.setDouble(2, marks);
                    insertStmt.executeUpdate();
                }
            }
        }
    }

    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM student WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String grade = rs.getString("grade");
                return new Student(id, name, grade);
            }
            return null;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static class Student {
        private final int studentId;
        private final String name;
        private final String grade;

        public Student(int studentId, String name, String grade) {
            this.studentId = studentId;
            this.name = name;
            this.grade = grade;
        }

        public int getStudentId() {
            return studentId;
        }

        public String getName() {
            return name;
        }

        public String getGrade() {
            return grade;
        }
        

        @Override
        public String toString() {
            return "Student{" +
                   "studentId=" + studentId +
                   ", name='" + name + '\'' +
                   ", grade='" + grade + '\'' +
                   '}';
        }

        void setMarks(double marks) {
            throw new IllegalArgumentException("Error");
        }
    }
}

