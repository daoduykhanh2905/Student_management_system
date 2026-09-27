package com.example;
import com.example.student.Register;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Auth {
    private JPanel panel;
    private JTextField textUser;
    private JButton loginButton;
    private JButton registerButton;
    private JPasswordField passwordField1;

    public JPanel getPanel() {
        return panel;
    }

    public Auth() {
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textUser.getText();
                String password = new String(passwordField1.getPassword());
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Username and password can't be empty");
                    return;
                }

                Check check = new Check();
                Account account = check.login(username, password);
                if (account != null) {
                    JOptionPane.showMessageDialog(panel, "Login successful");
                    JFrame successFrame = new JFrame("Welcome");
                    LoginPage success = new LoginPage(account);
                    successFrame.setContentPane(success.getPanel());
                    successFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    successFrame.pack();
                    successFrame.setLocationRelativeTo(null);
                    successFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(panel, "Username or password is incorrect!");
                }
            }
        });
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame frame = new JFrame("Register");
                Register register = new Register();
                frame.setContentPane(register.getPanel());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                super.mouseClicked(e);
//                while(!register.isDone()){
//
//                }
//                frame.dispose();
            }
        });
    }

    {
        $$$setupUI$$$();
    }

    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(
                3,
                4,
                new Insets(0, 0, 0, 0),
                -1,
                -1
        ));

        final JLabel label1 = new JLabel();
        label1.setText("User");

        panel.add(
                label1,
                new GridConstraints(
                        0, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST,
                        GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false
                )
        );
        textUser = new JTextField();
        panel.add(
                textUser,
                new GridConstraints(
                        0, 1, 1, 3,
                        GridConstraints.ANCHOR_WEST,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        new Dimension(150, -1),
                        null,
                        0,
                        false
                )
        );
        registerButton = new JButton();
        registerButton.setText("Register");
        panel.add(
                registerButton,
                new GridConstraints(
                        2, 3, 1, 1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        new Dimension(80, -1),
                        new Dimension(200, -1),
                        0,
                        false
                )
        );
        final JLabel label2 = new JLabel();
        label2.setText("Password");
        panel.add(
                label2,
                new GridConstraints(
                        1, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST,
                        GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false
                )
        );

        loginButton = new JButton();
        loginButton.setHorizontalAlignment(0);
        loginButton.setText("Login");
        loginButton.setVerticalAlignment(0);
        panel.add(
                loginButton,
                new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(1, -1),
                        new Dimension(80, -1),
                        new Dimension(200, -1),
                        2,
                        false
                )
        );
        passwordField1 = new JPasswordField();
        panel.add(
                passwordField1,
                new GridConstraints(
                        1, 1, 1, 3,
                        GridConstraints.ANCHOR_WEST,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        new Dimension(150, -1),
                        null,
                        0,
                        false
                )
        );
    }
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}