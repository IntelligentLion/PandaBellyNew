import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class JTableExample {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Sample data for the table
        Object[][] data = {
            {"John Doe", 30, "Software Engineer"},
            {"Jane Smith", 25, "Data Analyst"},
            {"Mike Johnson", 45, "Project Manager"}
        };

        // Column names
        String[] columnNames = {"Name", "Age", "Occupation"};

        // Create a DefaultTableModel and JTable
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setRowHeight(30);

        // Wrap the table in a JScrollPane to display headers and enable scrolling
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame's content pane
        frame.add(scrollPane, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }
}