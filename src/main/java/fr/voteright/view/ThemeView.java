package fr.voteright.view;

import fr.voteright.controller.CommunityController;
import fr.voteright.controller.NavigationManager;
import fr.voteright.controller.ProposalController;
import fr.voteright.model.Community;
import fr.voteright.model.Proposal;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ThemeView {
    private int id;
    private NavigationManager navigationManager;

    public ThemeView(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    public void display() {
        ThemeController themeController = new ThemeController();
        ArrayList<Theme> themes = themeController.getThemesOf(id);

        setLayout(new BorderLayout());

        add(new Header(true, navigationManager), BorderLayout.NORTH);

        JPanel main = new JPanel(new BorderLayout());

        JPanel leftSide = new JPanel();
        leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.Y_AXIS));
        leftSide.setBackground(Color.lightGray);
        leftSide.setPreferredSize(new Dimension(300, 720));

        JPanel totalBudgetPanel = new JPanel();
        totalBudgetPanel.setBackground(Color.lightGray);
        totalBudgetPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
        totalBudgetPanel.setLayout(new BoxLayout(totalBudgetPanel, BoxLayout.Y_AXIS));
        JLabel totalBudgetLabel = new JLabel("Budget Total :");
        JLabel totalBudget = new JLabel(" - - - "+"€");
        totalBudget.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        totalBudgetLabel.setFont(new Font("Arial", Font.BOLD, 22));
        totalBudgetPanel.add(totalBudgetLabel);
        totalBudgetPanel.add(totalBudget);

        JPanel usedBudgetPanel = new JPanel();
        usedBudgetPanel.setBackground(Color.lightGray);
        usedBudgetPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
        usedBudgetPanel.setLayout(new BoxLayout(usedBudgetPanel, BoxLayout.Y_AXIS));
        JLabel usedBudgetLabel = new JLabel("Budget utilisé :");
        JLabel usedBudget = new JLabel(" - - - "+"€");
        usedBudget.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        usedBudgetLabel.setFont(new Font("Arial", Font.BOLD, 22));
        usedBudgetPanel.add(usedBudgetLabel);
        usedBudgetPanel.add(usedBudget);

        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(community.getName());
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel proposalsPanel = new JPanel();
        proposalsPanel.setBackground(Color.WHITE);
        proposalsPanel.setLayout(new BoxLayout(proposalsPanel, BoxLayout.Y_AXIS));
        for (Proposal p : proposals) {
            JPanel proposalPanel = new JPanel(new BorderLayout());
            proposalPanel.setBackground(Color.lightGray);
            proposalPanel.setMaximumSize(new Dimension(1200, 50));
            JLabel proposalTitle = new JLabel(p.getTitle());
            proposalPanel.setBorder(BorderFactory.createEmptyBorder(6, 10, 0, 0));
            proposalPanel.setFont(new Font("Arial", Font.PLAIN, 20));
            proposalPanel.add(proposalTitle, BorderLayout.WEST);
            proposalsPanel.add(proposalPanel);
            proposalsPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(proposalsPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        leftSide.add(totalBudgetPanel);
        leftSide.add(usedBudgetPanel);

        content.add(title);
        content.add(scrollPane);

        main.add(leftSide, BorderLayout.WEST);
        main.add(content, BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);
    }
}
