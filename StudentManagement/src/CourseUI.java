import javax.swing.*;

public class CourseUI {

    public CourseUI() {

        JFrame frame = new JFrame("Course Management");
        frame.setSize(400, 300);
        frame.setLayout(null);

        JLabel l1 = new JLabel("Course Name:");
        l1.setBounds(30, 50, 120, 30);

        JLabel l2 = new JLabel("Credits:");
        l2.setBounds(30, 100, 120, 30);

        JTextField t1 = new JTextField();
        t1.setBounds(150, 50, 180, 30);

        JTextField t2 = new JTextField();
        t2.setBounds(150, 100, 180, 30);

        JButton addBtn = new JButton("Add Course");
        addBtn.setBounds(120, 170, 140, 40);

        CourseDAO dao = new CourseDAO();

        addBtn.addActionListener(e -> {
            try {
                String name = t1.getText();
                int credits = Integer.parseInt(t2.getText());

                dao.addCourse(name, credits);

                JOptionPane.showMessageDialog(frame, "Course Added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Input");
            }
        });

        frame.add(l1);
        frame.add(l2);
        frame.add(t1);
        frame.add(t2);
        frame.add(addBtn);

        frame.setVisible(true);
    }
}