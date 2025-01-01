package fr.voteright.view;

import fr.voteright.controller.CommunitiesController;
import fr.voteright.controller.NavigationManager;
import fr.voteright.model.Community;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CommunitiesView extends View{

    private NavigationManager navigationManager;
    private CommunitiesController controller;

    public CommunitiesView(NavigationManager navigationManager, CommunitiesController controller) {
        this.navigationManager = navigationManager;
        this.controller = controller;
    }

    public void display(){
        setLayout(new BorderLayout());

        add(new Header(), BorderLayout.NORTH);

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        main.setBackground(Color.WHITE);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Selectionnez un groupe");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBorder(BorderFactory.createEmptyBorder(40, 20, 30, 0));
        main.add(title, BorderLayout.NORTH);

        ArrayList<Community> communities = controller.getCommunties();

        JPanel communitiesPanel = new JPanel();
        communitiesPanel.setBackground(Color.WHITE);
        communitiesPanel.setLayout(new BoxLayout(communitiesPanel, BoxLayout.Y_AXIS));

        for (Community cmy : communities) {
            JPanel community = new JPanel(new BorderLayout());
            community.setBackground(Color.LIGHT_GRAY);
            community.setPreferredSize(new Dimension(1200, 50));
            JLabel communityName = new JLabel(cmy.getName());
            communityName.setBorder(BorderFactory.createEmptyBorder(6, 10, 0, 0));
            communityName.setFont(new Font("Arial", Font.PLAIN, 20));
            community.add(communityName, BorderLayout.WEST);
            communitiesPanel.add(community);
            communitiesPanel.add(Box.createVerticalStrut(10));
            int finalI = cmy.getId();
            community.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("community", finalI);
                    navigationManager.showView("community", params);
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

        }

        JScrollPane scrollPane = new JScrollPane(communitiesPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        main.add(scrollPane, BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);
    }
}
