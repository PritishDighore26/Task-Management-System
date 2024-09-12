package TaskManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskManagementSystemHomePage extends JFrame {

    public TaskManagementSystemHomePage() {
        setTitle("Task Management System");
        setSize(500, 400); // Increased the size for better UI layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window on the screen

        // Main panel setup
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(250, 219, 216)); // Updated background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title label
        JLabel lblTitle = new JLabel("Welcome to Task Management System", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24)); // Larger font size for the title
        lblTitle.setForeground(new Color(231, 76, 60)); // Dark green text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Ensure label is centered
        gbc.ipady = 20; // Increase padding for larger label size
        mainPanel.add(lblTitle, gbc);

        // Panel for buttons to align them horizontally
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // Horizontal layout with spacing
        buttonPanel.setBackground(new Color(250, 219, 216)); // Match background with the main panel

        // Registration Button
        JButton btnRegistration = createStyledButton("Registration");
        buttonPanel.add(btnRegistration);

        // Login Button
        JButton btnLogin = createStyledButton("Login");
        buttonPanel.add(btnLogin);

        // Add button panel to main panel
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.ipady = 0; // Reset padding for buttons
        mainPanel.add(buttonPanel, gbc);

        // Add main panel to frame
        add(mainPanel);

        // Action listener for Registration button
        btnRegistration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Registration UI
                new RegistrationUI().setVisible(true);
                dispose();  // Close the home page
            }
        });

        // Action listener for Login button
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Login UI
                new LoginUI().setVisible(true);
                dispose();  // Close the home page
            }
        });
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Increased font size
        button.setPreferredSize(new Dimension(160, 50)); // Larger button size
        button.setBackground(new Color(231, 76, 60)); // Updated button color
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Adjust padding
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255))); // White border
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskManagementSystemHomePage().setVisible(true);
            }
        });
    }
}
