import java.sql.*;

public class EnrollmentDAO {

    // ENROLL STUDENT
    public void enrollStudentByRoll(String rollNo, int courseId) {
    try {
        StudentDAO studentDAO = new StudentDAO();
        int studentId = studentDAO.getStudentIdByRoll(rollNo);

        if (studentId == -1) {
            System.out.println(" Student not found");
            return;
        }

        Connection con = DBConnection.getConnection();

        String query = "INSERT INTO Enrollments(student_id, course_id) VALUES (?, ?)";
        PreparedStatement pst = con.prepareStatement(query);

        pst.setInt(1, studentId);
        pst.setInt(2, courseId);

        pst.executeUpdate();

        System.out.println(" Enrollment successful");

    } catch (Exception e) {
        System.out.println(" Already enrolled or error");
    }
}
    
    public boolean studentExists(int studentId) {
    try {
        Connection con = DBConnection.getConnection();

        String query = "SELECT * FROM Students WHERE student_id=?";
        PreparedStatement pst = con.prepareStatement(query);

        pst.setInt(1, studentId);
        ResultSet rs = pst.executeQuery();

        return rs.next();

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    public void getCoursesOfStudent(int studentId) {
    try {
        Connection con = DBConnection.getConnection();

        String query =
            "SELECT c.course_name " +
            "FROM Courses c " +
            "JOIN Enrollments e ON c.course_id = e.course_id " +
            "WHERE e.student_id=?";

        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, studentId);

        ResultSet rs = pst.executeQuery();

        System.out.println("Courses:");
        while (rs.next()) {
            System.out.println("- " + rs.getString("course_name"));
        }

    } catch (Exception e) {
        System.out.println("Error fetching courses");
    }
}

    public void countStudentsPerCourse() {
    try {
        Connection con = DBConnection.getConnection();

        String query =
            "SELECT c.course_name, COUNT(e.student_id) AS total " +
            "FROM Courses c " +
            "LEFT JOIN Enrollments e ON c.course_id = e.course_id " +
            "GROUP BY c.course_name";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            System.out.println(
                rs.getString("course_name") + " → " +
                rs.getInt("total") + " students"
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    // VIEW ENROLLMENTS (JOIN)
    public void viewEnrollments() {
        try {
            Connection con = DBConnection.getConnection();

            String query =
                "SELECT s.name, c.course_name " +
                "FROM Students s " +
                "JOIN Enrollments e ON s.student_id = e.student_id " +
                "JOIN Courses c ON e.course_id = c.course_id";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.println(
                    rs.getString("name") + " → " +
                    rs.getString("course_name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}