import java.sql.*;

public class StudentDAO {

    // CREATE (Add Student)
    public void addStudent(String name, String email, String dept, String rollNo) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO Students(roll_no, name, email, department) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);

pst.setString(1, rollNo);
pst.setString(2, name);
pst.setString(3, email);
pst.setString(4, dept);

            pst.executeUpdate();

            System.out.println("Student added successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ (View All Students)
    public void getAllStudents() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM Students";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.println(
    "ID: " + rs.getInt("student_id") +
    " | Roll: " + rs.getString("roll_no") +
    " | Name: " + rs.getString("name") +
    " | Email: " + rs.getString("email") +
    " | Dept: " + rs.getString("department")
);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void updateStudent(int id, String rollNo, String name, String email, String dept) {
    try {
        Connection con = DBConnection.getConnection();

        String query = "UPDATE Students SET roll_no=?, name=?, email=?, department=? WHERE student_id=?";
        PreparedStatement pst = con.prepareStatement(query);

        pst.setString(1, rollNo);
        pst.setString(2, name);
        pst.setString(3, email);
        pst.setString(4, dept);
        pst.setInt(5, id);

        int rows = pst.executeUpdate();

        if (rows > 0) {
            System.out.println(" Student updated successfully");
        } else {
            System.out.println(" Student not found");
        }

    } catch (Exception e) {
        System.out.println(" Update failed (maybe duplicate roll/email)");
    }
}

    // DELETE
    public void deleteStudent(int id) {
    try {
        Connection con = DBConnection.getConnection();

        // Step 1: Delete from Attendance
        String q1 = "DELETE FROM Attendance WHERE student_id=?";
        PreparedStatement pst1 = con.prepareStatement(q1);
        pst1.setInt(1, id);
        pst1.executeUpdate();

        // Step 2: Delete from Enrollments
        String q2 = "DELETE FROM Enrollments WHERE student_id=?";
        PreparedStatement pst2 = con.prepareStatement(q2);
        pst2.setInt(1, id);
        pst2.executeUpdate();

        // Step 3: Delete from Students
        String q3 = "DELETE FROM Students WHERE student_id=?";
        PreparedStatement pst3 = con.prepareStatement(q3);
        pst3.setInt(1, id);

        int rows = pst3.executeUpdate();

        if (rows > 0) {
            System.out.println(" Student deleted successfully");
        } else {
            System.out.println(" Student not found");
        }

    } catch (Exception e) {
        System.out.println(" Delete failed");
    }
}

    public void searchByDepartment(String dept) {
    try {
        Connection con = DBConnection.getConnection();

        String query = "SELECT * FROM Students WHERE department=?";
        PreparedStatement pst = con.prepareStatement(query);

        pst.setString(1, dept);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            System.out.println(
                rs.getString("name") + " | " +
                rs.getString("department")
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public int getStudentIdByRoll(String rollNo) {
    try {
        Connection con = DBConnection.getConnection();

        String query = "SELECT student_id FROM Students WHERE roll_no=?";
        PreparedStatement pst = con.prepareStatement(query);

        pst.setString(1, rollNo);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getInt("student_id");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1;
}

public void countStudentsPerDepartment() {
    try {
        Connection con = DBConnection.getConnection();

        String query = "SELECT department, COUNT(*) AS total FROM Students GROUP BY department";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            System.out.println(
                rs.getString("department") + " → " +
                rs.getInt("total") + " students"
            );
        }

    } catch (Exception e) {
        System.out.println("Error fetching data");
    }
}

    // SEARCH (by name)
    public void searchStudentByName(String name) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM Students WHERE name LIKE ?";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, "%" + name + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                System.out.println(
                    rs.getInt("student_id") + " | " +
                    rs.getString("name") + " | " +
                    rs.getString("email") + " | " +
                    rs.getString("department")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}