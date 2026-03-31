import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewStudentsUI {

    public JTable table;
    public DefaultTableModel model;

    public ViewStudentsUI(){

        JFrame frame = new JFrame("Students");
        frame.setSize(700,400);
        frame.getContentPane().setBackground(new Color(30,30,30));

        String[] cols = {"ID","Roll","Name","Email","Dept"};
        model = new DefaultTableModel(cols,0);

        table = new JTable(model);
        table.setRowHeight(25);

        load();

        frame.add(new JScrollPane(table));
        frame.setVisible(true);
    }

    void load(){
        try{
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Students");

            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getInt("student_id"),
                        rs.getString("roll_no"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("department")
                });
            }
        }catch(Exception e){}
    }
}