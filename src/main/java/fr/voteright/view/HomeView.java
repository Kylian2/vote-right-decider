package fr.voteright.view;

import fr.voteright.controller.NavigationManager;

import javax.swing.*;
import java.awt.*;

public class HomeView extends JPanel {
    public HomeView(NavigationManager navigationManager) {
        setLayout(new BorderLayout());

        // Titre
        JLabel welcomeLabel = new JLabel("Bienvenue sur VoteRight !");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        add(welcomeLabel, BorderLayout.CENTER);

        // Bouton pour passer à l'écran de connexion
        JButton goToLoginButton = new JButton("Se connecter");
        goToLoginButton.addActionListener(e -> navigationManager.showView("login"));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(goToLoginButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}