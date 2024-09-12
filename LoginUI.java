package TaskManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class LoginUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private static final Configuration CONFIGURATION = new Configuration().configure().addAnnotatedClass(User.class);
    private static final SessionFactory SESSION_FACTORY = CONFIGURATION.buildSessionFactory();

    public LoginUI() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define light green color used in TaskManagementSystemHomePage
        Color lightGreen = new Color(250, 219, 216 );

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(lightGreen); // Set background to light green
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username field
        JLabel lblUsername = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblUsername, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Password field
        JLabel lblPassword = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblPassword, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Login button
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16)); // Increase font size
        btnLogin.setPreferredSize(new Dimension(160, 50)); // Larger button size
        btnLogin.setBackground(new Color(231, 76, 60)); // Dark green background for button
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Adjust padding
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255))); // White border

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(btnLogin, gbc);

        add(panel);

        // Login button action
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Authenticate the user
                Session session = SESSION_FACTORY.openSession();
                User user = session.get(User.class, username);

                if (user != null && user.getPassword().equals(password)) {
                    String role = user.getRole();
                    if (role.equals("admin")) {
                        JOptionPane.showMessageDialog(null, "Admin login successful!");
                        AdminTaskManagement adminPanel = new AdminTaskManagement();
                        adminPanel.setVisible(true);
                        dispose();
                    } else if (role.equals("student")) {
                        JOptionPane.showMessageDialog(null, "Student login successful!");
                        StudentTaskView studentView = new StudentTaskView(username);
                        studentView.setVisible(true);
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }

                session.close();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
    }
}
