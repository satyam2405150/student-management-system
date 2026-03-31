import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AttendanceUI {

    public AttendanceUI() {

        JFrame f = new JFrame("Attendance");
        f.setSize(600, 400);
        f.setLayout(null);

        // Student dropdown
        JLabel l1 = new JLabel("Student:");
        l1.setBounds(50, 30, 100, 30);

        JComboBox<String> student = new JComboBox<>();
        student.setBounds(150, 30, 200, 30);

        // Course dropdown
        JLabel l2 = new JLabel("Course:");
        l2.setBounds(50, 80, 100, 30);

        JComboBox<String> course = new JComboBox<>();
        course.setBounds(150, 80, 200, 30);

        // Status dropdown
        JLabel l3 = new JLabel("Status:");
        l3.setBounds(50, 130, 100, 30);

        JComboBox<String> status = new JComboBox<>(new String[]{"Present", "Absent"});
        status.setBounds(150, 130, 200, 30);

        // Buttons
        JButton mark = new JButton("Mark Attendance");
        mark.setBounds(400, 30, 150, 40);

        JButton view = new JButton("View Records");
        view.setBounds(400, 90, 150, 40);

        JButton percent = new JButton("Attendance %");
        percent.setBounds(400, 150, 150, 40);

        // ================= LOAD DATA =================
        try {
            Connection con = DBConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT roll_no FROM Students");
            while (rs.next()) {
                student.addItem(rs.getString("roll_no"));
            }

            rs = con.createStatement().executeQuery("SELECT course_id, course_name FROM Courses");
            while (rs.next()) {
                course.addItem(rs.getInt("course_id") + " - " + rs.getString("course_name"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "Error loading data");
        }

        // ================= MARK =================
        mark.addActionListener(e -> {
            try {
                String roll = (String) student.getSelectedItem();
                String courseStr = (String) course.getSelectedItem();

                int cid = Integer.parseInt(courseStr.split(" - ")[0]);
                String stat = (String) status.getSelectedItem();

                new AttendanceDAO().markAttendance(roll, cid, stat);

                JOptionPane.showMessageDialog(f, "✅ Attendance Marked");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "❌ Error marking attendance");
            }
        });

        // ================= VIEW =================
        view.addActionListener(e -> {

            JFrame tf = new JFrame("Attendance Records");
            tf.setSize(700, 400);

            String[] cols = {"Student", "Course", "Status"};
            DefaultTableModel model = new DefaultTableModel(cols, 0);

            JTable table = new JTable(model);
            table.setRowHeight(25);

            try {
                Connection con = DBConnection.getConnection();

                String query =
                        "SELECT s.name, c.course_name, a.status " +
                        "FROM Attendance a " +
                        "JOIN Students s ON a.student_id = s.student_id " +
                        "JOIN Courses c ON a.course_id = c.course_id";

                ResultSet rs = con.createStatement().executeQuery(query);

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getString("name"),
                            rs.getString("course_name"),
                            rs.getString("status")
                    });
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(tf, "Error loading records");
            }

            tf.add(new JScrollPane(table));
            tf.setVisible(true);
        });

        // ================= PERCENTAGE =================
        percent.addActionListener(e -> {

            try {
                String roll = (String) student.getSelectedItem();
                String courseStr = (String) course.getSelectedItem();

                int cid = Integer.parseInt(courseStr.split(" - ")[0]);

                int sid = new StudentDAO().getStudentIdByRoll(roll);

                double percentVal = new AttendanceDAO().calculateAttendance(sid, cid);

                if (percentVal == -1) {
                    JOptionPane.showMessageDialog(f, "❌ Error calculating");
                } else {
                    String formatted = String.format("%.2f", percentVal);
                    JOptionPane.showMessageDialog(f, "Attendance: " + formatted + "%");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "❌ Error");
            }
        });

        // ================= ADD COMPONENTS =================
        f.add(l1);
        f.add(student);
        f.add(l2);
        f.add(course);
        f.add(l3);
        f.add(status);

        f.add(mark);
        f.add(view);
        f.add(percent);

        f.setVisible(true);
    }
}