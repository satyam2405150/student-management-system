import java.awt.*;
import javax.swing.*;

public class DashboardUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Dashboard");
        frame.setSize(600, 450);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(30,30,30));

        JLabel title = new JLabel("STUDENT MANAGEMENT SYSTEM");
        title.setBounds(100, 40, 400, 40);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JButton studentBtn = createBtn("Students", 120);
        JButton courseBtn = createBtn("Courses", 180);
        JButton enrollBtn = createBtn("Enrollment", 240);
        JButton attendanceBtn = createBtn("Attendance", 300);
        JButton exitBtn = createBtn("Exit", 360);

        studentBtn.addActionListener(e -> new StudentUI());
        courseBtn.addActionListener(e -> new CourseUI());
        enrollBtn.addActionListener(e -> new EnrollmentUI());
        attendanceBtn.addActionListener(e -> new AttendanceUI());
        exitBtn.addActionListener(e -> System.exit(0));

        frame.add(title);
        frame.add(studentBtn);
        frame.add(courseBtn);
        frame.add(enrollBtn);
        frame.add(attendanceBtn);
        frame.add(exitBtn);

        frame.setVisible(true);
    }

    static JButton createBtn(String text, int y){
        JButton btn = new JButton(text);
        btn.setBounds(200, y, 200, 40);
        btn.setBackground(new Color(70,130,180));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        return btn;
    }
}