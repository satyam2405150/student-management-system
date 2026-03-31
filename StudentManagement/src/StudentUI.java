import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StudentUI {

    static int selectedId = -1;

    public StudentUI() {

        JFrame frame = new JFrame("Student Management");
        frame.setSize(600, 450);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(30,30,30));

        int y = 50;

        JLabel l1 = label("Roll No:", y);
        JTextField t1 = field(y);

        y+=40;
        JLabel l2 = label("Name:", y);
        JTextField t2 = field(y);

        y+=40;
        JLabel l3 = label("Email:", y);
        JTextField t3 = field(y);

        y+=40;
        JLabel l4 = label("Department:", y);
        JTextField t4 = field(y);

        JButton add = btn("Add", 250);
        JButton update = btn("Update", 300);
        JButton del = btn("Delete", 350);
        JButton view = btn("View", 400);

        StudentDAO dao = new StudentDAO();

        add.addActionListener(e -> {
            dao.addStudent(t1.getText(), t2.getText(), t3.getText(), t4.getText());
            JOptionPane.showMessageDialog(frame,"✅ Added");
        });

        update.addActionListener(e -> {
            if(selectedId==-1) return;
            dao.updateStudent(selectedId,t1.getText(),t2.getText(),t3.getText(),t4.getText());
            JOptionPane.showMessageDialog(frame,"✅ Updated");
        });

        del.addActionListener(e -> {
            if(selectedId==-1) return;

            int confirm = JOptionPane.showConfirmDialog(frame,"Delete?");
            if(confirm!=JOptionPane.YES_OPTION) return;

            dao.deleteStudent(selectedId);
            JOptionPane.showMessageDialog(frame,"Deleted");
        });

        view.addActionListener(e -> {
            ViewStudentsUI v = new ViewStudentsUI();

            v.table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int r = v.table.getSelectedRow();
                    selectedId = (int)v.model.getValueAt(r,0);

                    t1.setText((String)v.model.getValueAt(r,1));
                    t2.setText((String)v.model.getValueAt(r,2));
                    t3.setText((String)v.model.getValueAt(r,3));
                    t4.setText((String)v.model.getValueAt(r,4));
                }
            });
        });

        frame.add(l1); frame.add(t1);
        frame.add(l2); frame.add(t2);
        frame.add(l3); frame.add(t3);
        frame.add(l4); frame.add(t4);

        frame.add(add);
        frame.add(update);
        frame.add(del);
        frame.add(view);

        frame.setVisible(true);
    }

    static JLabel label(String t,int y){
        JLabel l = new JLabel(t);
        l.setBounds(50,y,100,30);
        l.setForeground(Color.WHITE);
        return l;
    }

    static JTextField field(int y){
        JTextField t = new JTextField();
        t.setBounds(160,y,250,30);
        return t;
    }

    static JButton btn(String t,int y){
        JButton b = new JButton(t);
        b.setBounds(420,y,120,30);
        b.setBackground(new Color(70,130,180));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }
}