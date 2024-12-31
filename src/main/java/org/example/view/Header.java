package org.example.view;

import org.example.controller.NavigationManager;

import javax.swing.*;
import java.awt.*;

public class Header extends JPanel {

    private NavigationManager navigationManager;

    public Header() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1280, 75));
        setBackground(Color.GRAY);

        JLabel logo = new JLabel("VoteRight");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 30));
        logo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        add(logo, BorderLayout.WEST);
    }

    public Header(Boolean isLoggedIn, NavigationManager navigationManager) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1280, 75));
        setBackground(Color.GRAY);

        JLabel logo = new JLabel("VoteRight");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 30));
        logo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        add(logo, BorderLayout.WEST);

        if(isLoggedIn) {
            JButton home = new JButton("Accueil");
            home.addActionListener(e -> navigationManager.showView("page1"));
            add(home, BorderLayout.EAST);
        }
    }
}
