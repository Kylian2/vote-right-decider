package fr.voteright.controller;

import fr.voteright.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class NavigationManager {
    private final CardLayout cardLayout;
    private final JPanel containerPanel;
    private final Map<String, JPanel> views;

    public NavigationManager() {
        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout); // Stocke les différentes vues
        views = new HashMap<>();
    }

    public void start(JFrame frame) {
        // Initialisation des vues
        addView("login", new LoginView(this, new LoginController()));
        addView("communities", new CommunitiesView(this));
        addView("community", new CommunityView(this));

        // Ajout du panel contenant toutes les vues au frame
        frame.add(containerPanel, BorderLayout.CENTER);

        // Afficher la vue initiale
        showView("login");

        // Afficher la fenêtre principale
        frame.setVisible(true);
    }

    public void addView(String name, JPanel view) {
        // Stocke la vue dans la HashMap et l'ajoute au CardLayout
        views.put(name, view);
        containerPanel.add(view, name);
    }

    public void showView(String name) {
        // Passe d'une vue à l'autre
        View view = (View) views.get(name);
        if (view != null) {
            view.display();
            cardLayout.show(containerPanel, name);
        } else {
            System.err.println("Erreur : vue inexistante - " + name);
        }
    }

    public void showView(String name, Map<String, Object> params) {
        if (views.containsKey(name)) {
            JPanel view = views.get(name);

            // Si la vue est "paramétrable", on lui transmet les données
            if (view instanceof ParametrizedView) {
                ((ParametrizedView) view).setParams(params);
            }

            cardLayout.show(containerPanel, name);
        } else {
            System.err.println("Vue introuvable : " + name);
        }
    }
}