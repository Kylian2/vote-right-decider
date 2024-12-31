package fr.voteright.view;

import fr.voteright.controller.NavigationManager;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CommunityView extends JPanel implements ParametrizedView{

    private int id;
    private NavigationManager navigationManager;

    public CommunityView(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    public void display(){
        setLayout(new BorderLayout());

        add(new Header(true, navigationManager), BorderLayout.NORTH);

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        main.setBackground(Color.WHITE);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Groupe " + id);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        main.add(title, BorderLayout.NORTH);

        add(main, BorderLayout.CENTER);
    }

    @Override
    public void setParams(Map<String, Object> params) {
        if (params != null && params.containsKey("community")) {
            this.removeAll();
            this.id = (int) params.get("community");
            this.display();
        }
    }
}
