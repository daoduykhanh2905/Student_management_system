package com.example.student;
import com.example.UserRole;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.example.Check;

public class Register {
    private JPanel panel;
    private JTextField textUsername;
    private JPasswordField textPassword;
    private JComboBox<UserRole> comboRole;
    private JButton registerButton;

    public Register() {
        comboRole.addItem(UserRole.STUDENT);
        comboRole.addItem(UserRole.TEACHER);
        comboRole.addItem(UserRole.ADMIN);
        registerButton.addActionListener(e -> {
            String username = textUsername.getText();
            String password = new String(textPassword.getPassword());
            UserRole role = (UserRole) comboRole.getSelectedItem();
            if (username.isEmpty() || password.isEmpty()) {JOptionPane.showMessageDialog(panel, "enter username and password!");
                return;
            }
            Check check = new Check();
            boolean result = check.register(username, password, role);
            if (result){JOptionPane.showMessageDialog(panel, "Register successful!");
                textUsername.setText("");
                textPassword.setText("");
            } else {JOptionPane.showMessageDialog(panel, "Username already exists!");}
        });
    }
    public JPanel getPanel(){
        return panel;
    }
}