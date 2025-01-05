package fr.voteright.view;

import fr.voteright.controller.CommunityController;
import fr.voteright.controller.NavigationManager;
import fr.voteright.controller.ProposalController;
import fr.voteright.model.Community;
import fr.voteright.model.Proposal;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommunityView extends View implements ParametrizedView{

    private int id;
    private NavigationManager navigationManager;

    public CommunityView(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    public void display(){
        CommunityController communityController = new CommunityController();
        ProposalController proposalController = new ProposalController();
        Community community = communityController.getCommunity(id);
        communityController.getBudget(community);
        ArrayList<Proposal> proposals = proposalController.getProposalsOf(id);

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
        JLabel totalBudget = new JLabel(community.getBudget()+" €");
        totalBudget.setFont(new Font("Arial", Font.PLAIN, 20));
        totalBudget.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        totalBudgetLabel.setFont(new Font("Arial", Font.BOLD, 22));
        totalBudgetPanel.add(totalBudgetLabel);
        totalBudgetPanel.add(totalBudget);

        JPanel usedBudgetPanel = new JPanel();
        usedBudgetPanel.setBackground(Color.lightGray);
        usedBudgetPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
        usedBudgetPanel.setLayout(new BoxLayout(usedBudgetPanel, BoxLayout.Y_AXIS));
        JLabel usedBudgetLabel = new JLabel("Budget utilisé :");
        JLabel usedBudget = new JLabel(community.getUsedBudget()+" €");
        usedBudget.setFont(new Font("Arial", Font.PLAIN, 20));
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
            int finalI = p.getId();
            proposalPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("proposal", finalI);
                    navigationManager.showView("proposal", params);
                }
            });
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

    @Override
    public void setParams(Map<String, Object> params) {
        if (params != null && params.containsKey("community")) {
            this.removeAll();
            this.id = (int) params.get("community");
            this.display();
        }
    }
}
