import java.sql.*;

public class CourseDAO {

    // ADD COURSE
    public void addCourse(String name, int credits) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO Courses(course_name, credits) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, name);
            pst.setInt(2, credits);

            pst.executeUpdate();

            System.out.println("Course added successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VIEW COURSES
    public void getAllCourses() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM Courses";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.println(
                    rs.getInt("course_id") + " | " +
                    rs.getString("course_name") + " | " +
                    rs.getInt("credits")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}