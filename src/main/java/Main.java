import javax.swing.*;
import com.example.Auth;
import com.example.Student_management_system;
import com.example.Students.StudentWindow;
import com.example.Teachers.TeacherWindow;

public class Main {
    //TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            Auth auth = new Auth();
//            JFrame frame = new JFrame("Authentication");
//            frame.add(auth.getPanel());

            Student_management_system dashboard= new Student_management_system();
            JFrame frame = new JFrame("Student Management System");
            frame.add(dashboard.getPanel1());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });

    }
}
