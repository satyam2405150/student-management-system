import java.sql.*;

public class AttendanceDAO {

    public void markAttendance(String rollNo, int courseId, String status) {
        try {
            StudentDAO studentDAO = new StudentDAO();
            int studentId = studentDAO.getStudentIdByRoll(rollNo);

            if (studentId == -1) {
                System.out.println(" Student not found");
                return;
            }

            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO Attendance(student_id, course_id, status) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, studentId);
            pst.setInt(2, courseId);
            pst.setString(3, status);

            pst.executeUpdate();

            System.out.println(" Attendance marked");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double calculateAttendance(int studentId, int courseId) {
    try {
        Connection con = DBConnection.getConnection();

        String totalQ = "SELECT COUNT(*) FROM Attendance WHERE student_id=? AND course_id=?";
        PreparedStatement pst1 = con.prepareStatement(totalQ);
        pst1.setInt(1, studentId);
        pst1.setInt(2, courseId);

        ResultSet rs1 = pst1.executeQuery();
        rs1.next();
        int total = rs1.getInt(1);

        String presentQ = "SELECT COUNT(*) FROM Attendance WHERE student_id=? AND course_id=? AND status='Present'";
        PreparedStatement pst2 = con.prepareStatement(presentQ);
        pst2.setInt(1, studentId);
        pst2.setInt(2, courseId);

        ResultSet rs2 = pst2.executeQuery();
        rs2.next();
        int present = rs2.getInt(1);

        if (total == 0) return 0;

        return (present * 100.0) / total;

    } catch (Exception e) {
        return -1;
    }
}
    public void viewAttendance() {
        try {
            Connection con = DBConnection.getConnection();

            String query =
                "SELECT s.name, c.course_name, a.status " +
                "FROM Attendance a " +
                "JOIN Students s ON a.student_id = s.student_id " +
                "JOIN Courses c ON a.course_id = c.course_id";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.println(
                    rs.getString("name") + " | " +
                    rs.getString("course_name") + " | " +
                    rs.getString("status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}