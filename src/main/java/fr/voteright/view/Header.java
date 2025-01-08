package fr.voteright.view;

import fr.voteright.controller.LoginController;
import fr.voteright.controller.NavigationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author kylianrichard
 */
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
            LoginController controller = new LoginController();
            JPanel buttons = new JPanel();
            buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
            buttons.setBackground(Color.GRAY);
            add(buttons, BorderLayout.EAST);
            JButton home = new JButton("Accueil");
            home.addActionListener(e -> navigationManager.showView("communities"));
            buttons.add(home);
            JButton disconnect = new JButton("Déconnexion");
            disconnect.addActionListener(e -> {
                boolean disconnected = controller.disconnect();
                if(disconnected) {
                    navigationManager.showView("login");
                }
            });
            buttons.add(disconnect);
        }
    }
}
