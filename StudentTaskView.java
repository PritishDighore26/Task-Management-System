package TaskManagement;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class StudentTaskView extends JFrame {

    private JTextArea taskDisplayArea;
    private static final Configuration CONFIGURATION = new Configuration().configure().addAnnotatedClass(Task.class);
    private static final SessionFactory SESSION_FACTORY = CONFIGURATION.buildSessionFactory();

    public StudentTaskView(String username) {
        setTitle("Task View - Student");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define light green color used in TaskManagementSystemHomePage
        Color lightGreen = new Color(250, 219, 216);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(lightGreen); // Set background to light green

        JLabel lblTitle = new JLabel("Tasks assigned to you:", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblTitle, BorderLayout.NORTH);

        taskDisplayArea = new JTextArea(20, 50);
        taskDisplayArea.setEditable(false);
        taskDisplayArea.setBackground(Color.WHITE); // Ensure the text area has a white background for better readability
        JScrollPane scrollPane = new JScrollPane(taskDisplayArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        loadStudentTasks(username);
    }

    private void loadStudentTasks(String username) {
        Session session = SESSION_FACTORY.openSession();
        List<Task> tasks = session.createQuery("from Task where assignedTo = :username", Task.class)
                                  .setParameter("username", username)
                                  .getResultList();

        for (Task task : tasks) {
            taskDisplayArea.append("Task ID: " + task.getTaskId() + "\n");
            taskDisplayArea.append("Task Name: " + task.getTaskName() + "\n");
            taskDisplayArea.append("Task Description: " + task.getTaskDescription() + "\n\n");
        }

        session.close();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentTaskView("studentUsername").setVisible(true));
    }
}
