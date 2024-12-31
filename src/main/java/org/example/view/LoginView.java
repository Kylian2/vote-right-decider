package org.example.view;

import org.example.controller.NavigationManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LoginView extends JPanel {
    public LoginView(NavigationManager navigationManager) {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        add(new Header(), BorderLayout.NORTH);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.white);

        JPanel loginPanel = new JPanel(new GridLayout(4, 1, 10, 30));
        loginPanel.setBackground(Color.white);

        JPanel filler1 = new JPanel();
        filler1.setPreferredSize(new Dimension(400, 0));
        filler1.setBackground(Color.white);
        JPanel filler2 = new JPanel();
        filler2.setPreferredSize(new Dimension(400, 0));
        filler2.setBackground(Color.white);
        JPanel filler3 = new JPanel();
        filler3.setPreferredSize(new Dimension(0, 150));
        filler3.setBackground(Color.white);
        JPanel filler4 = new JPanel();
        filler4.setPreferredSize(new Dimension(0, 150));
        filler4.setBackground(Color.white);

        main.add(loginPanel, BorderLayout.CENTER);
        main.add(filler1, BorderLayout.WEST);
        main.add(filler2, BorderLayout.EAST);
        main.add(filler3, BorderLayout.NORTH);
        main.add(filler4, BorderLayout.SOUTH);

        JLabel title = new JLabel("Connexion");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        JPanel emailPanel = new JPanel(new BorderLayout());
        emailPanel.setBackground(Color.white);
        JLabel emailLabel = new JLabel("Email : ");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField emailField = new JTextField(20);
        emailField.setBackground(Color.lightGray);
        emailLabel.setLabelFor(emailField);
        emailPanel.add(emailLabel, BorderLayout.NORTH);
        emailPanel.add(emailField, BorderLayout.CENTER);

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Color.white);
        JLabel passwordLabel = new JLabel("Mot de passe : ");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBackground(Color.lightGray);
        passwordLabel.setLabelFor(passwordField);
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        JButton loginButton = new JButton("Se connecter");

        loginPanel.add(title);
        loginPanel.add(emailPanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(loginButton);

        add(main, BorderLayout.CENTER);

        loginButton.addActionListener(e -> navigationManager.showView("page1"));

    }
}