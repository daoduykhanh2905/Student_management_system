import javax.swing.*;
import com.example.Auth;

public class Main {
    //TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Auth auth = new Auth();
            JFrame frame = new JFrame("Authentication");
            frame.add(auth.getPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

    }
}
