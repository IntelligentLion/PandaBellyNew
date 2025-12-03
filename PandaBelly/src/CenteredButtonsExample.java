import javax.swing.*;
import java.awt.*;

public class CenteredButtonsExample {

    public static void main(String[] args) {
        // Ensure the Swing components are created on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        // 1. Create the main application frame
        JFrame frame = new JFrame("Centered Buttons");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // 2. Get the main content pane of the frame (uses BorderLayout by default)
        Container contentPane = frame.getContentPane();

        // 3. Create a panel that uses FlowLayout (centers components horizontally by default)
        // This panel will hold the actual buttons.
        JPanel buttonPanel = new JPanel();
        
        // Optional: Change FlowLayout alignment if needed (CENTER is default)
        // buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // 4. Add the buttons to the button panel
        buttonPanel.add(new JButton("Button 1"));
        buttonPanel.add(new JButton("Button 2"));

        // 5. Add the button panel to the CENTER of the main frame's content pane
        // BorderLayout's CENTER region attempts to fill available space, 
        // and FlowLayout centers within that space.
        contentPane.add(buttonPanel, BorderLayout.CENTER);

        // 6. Display the frame
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true);
    }
}