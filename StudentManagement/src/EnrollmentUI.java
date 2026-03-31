import java.sql.*;
import javax.swing.*;

public class EnrollmentUI {

    public EnrollmentUI() {

        JFrame frame = new JFrame("Enroll Student");
        frame.setSize(400, 300);
        frame.setLayout(null);

        JLabel l1 = new JLabel("Select Student:");
        l1.setBounds(30, 50, 120, 30);

        JLabel l2 = new JLabel("Select Course:");
        l2.setBounds(30, 100, 120, 30);

        JComboBox<String> studentBox = new JComboBox<>();
        studentBox.setBounds(160, 50, 180, 30);

        JComboBox<String> courseBox = new JComboBox<>();
        courseBox.setBounds(160, 100, 180, 30);

        JButton enrollBtn = new JButton("Enroll");
        enrollBtn.setBounds(120, 170, 120, 40);

        // Load students
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT roll_no FROM Students";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                studentBox.addItem(rs.getString("roll_no"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error loading students");
        }

        // Load courses
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT course_id, course_name FROM Courses";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                courseBox.addItem(
                        rs.getInt("course_id") + " - " + rs.getString("course_name")
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error loading courses");
        }

        // Button logic
        enrollBtn.addActionListener(e -> {
            try {
                String roll = (String) studentBox.getSelectedItem();

                String course = (String) courseBox.getSelectedItem();
                int courseId = Integer.parseInt(course.split(" - ")[0]);

                EnrollmentDAO dao = new EnrollmentDAO();
                dao.enrollStudentByRoll(roll, courseId);

                JOptionPane.showMessageDialog(frame, "Enrolled successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Enrollment failed");
            }
        });

        frame.add(l1);
        frame.add(l2);
        frame.add(studentBox);
        frame.add(courseBox);
        frame.add(enrollBtn);

        frame.setVisible(true);
    }
}