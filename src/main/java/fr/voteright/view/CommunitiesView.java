package fr.voteright.view;

import fr.voteright.controller.CommunityController;
import fr.voteright.controller.NavigationManager;
import fr.voteright.model.Community;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

public class CommunitiesView extends View{

    private NavigationManager navigationManager;

    public CommunitiesView(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    public void display(){
        CommunityController controller = new CommunityController();
        ArrayList<Community> communities = controller.getCommunities();

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
            community.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("community", finalI);
                    navigationManager.showView("community", params);
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
