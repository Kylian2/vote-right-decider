package fr.voteright.view;

import fr.voteright.controller.CommunityController;
import fr.voteright.controller.NavigationManager;
import fr.voteright.controller.ProposalController;
import fr.voteright.model.Community;
import fr.voteright.model.Proposal;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;

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
        JLabel totalBudgetLabel = new JLabel("Budget total :");
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

        JPanel fixedFeesPanel = new JPanel();
        fixedFeesPanel.setBackground(Color.lightGray);
        fixedFeesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
        fixedFeesPanel.setLayout(new BoxLayout(fixedFeesPanel, BoxLayout.Y_AXIS));
        JLabel fixedFeesLabel = new JLabel("Frais fixes :");
        JLabel fixedFees = new JLabel(community.getFixedFees()+" €");
        fixedFees.setFont(new Font("Arial", Font.PLAIN, 20));
        fixedFees.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        fixedFeesLabel.setFont(new Font("Arial", Font.BOLD, 22));
        fixedFeesPanel.add(fixedFeesLabel);
        fixedFeesPanel.add(fixedFees);

        JButton edit = new JButton("Modifier");
        edit.setPreferredSize(new Dimension(90, 30));
        edit.setFont(new Font("Arial", Font.PLAIN, 15));
        HashMap<String, Object> paramEditBudget = new HashMap<>();
        paramEditBudget.put("themeBudgetEditor", community.getId());
        edit.addActionListener(e -> navigationManager.showView("themeBudgetEditor", paramEditBudget));

        JPanel editPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        editPanel.setBackground(Color.lightGray);
        editPanel.setBorder(BorderFactory.createEmptyBorder(300, 0, 0, 0));
        editPanel.add(edit);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);

        JLabel title = new JLabel(community.getName());
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Map<String, Integer> options = new LinkedHashMap<>();
        options.put("Tout", 0);
        options.put("Rapide - Maximiser la satisfaction", 1);
        options.put("Rapide - Maximiser la satisfaction (ratio)", 1);
        options.put("Optimal - Maximiser la satisfaction", 3);

        Set<String> keys = options.keySet();
        String[] optionsArray = keys.toArray(new String[0]);
        JComboBox<String> orderOptionsBox = new JComboBox<>(optionsArray);
        orderOptionsBox.setSelectedIndex(0);

        titlePanel.add(title, BorderLayout.WEST);
        titlePanel.add(orderOptionsBox, BorderLayout.EAST);

        JPanel proposalsPanel = new JPanel();
        proposalsPanel.setBackground(Color.WHITE);
        proposalsPanel.setLayout(new BoxLayout(proposalsPanel, BoxLayout.Y_AXIS));
        getProposalsPanel(proposals, proposalsPanel, proposals);

        JScrollPane scrollPane = new JScrollPane(proposalsPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        leftSide.add(totalBudgetPanel);
        leftSide.add(usedBudgetPanel);
        leftSide.add(fixedFeesPanel);
        leftSide.add(editPanel);

        content.add(titlePanel, BorderLayout.NORTH);
        content.add(scrollPane, BorderLayout.CENTER);

        main.add(leftSide, BorderLayout.WEST);
        main.add(content, BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);

        orderOptionsBox.addActionListener(e -> {
            String selectedOption = (String) orderOptionsBox.getSelectedItem();
            int criteria = options.get(selectedOption);
            if(selectedOption.equals("Trier")) {
                ArrayList<Proposal> pro = proposalController.getProposalsOf(community.getId());
                getProposalsPanel(proposals, proposalsPanel, pro);
            }else{
                ArrayList<Proposal> pro = proposalController.getProposalsOf(community.getId());
                try {
                    ArrayList<Proposal> filteredProposals = proposalController.orderBy(pro, new Community(community), criteria);
                    proposalsPanel.removeAll();
                    getProposalsPanel(proposals, proposalsPanel, filteredProposals);
                    proposalsPanel.revalidate();
                    proposalsPanel.repaint();
                } catch (Exception ex) {
                    return;
                }
            }
        });
    }

    private void getProposalsPanel(ArrayList<Proposal> proposals, JPanel proposalsPanel, ArrayList<Proposal> filteredProposals) {
        for (Proposal p : filteredProposals) {
            JPanel proposalPanel = getProposalPanel(p);
            proposalsPanel.add(proposalPanel);
            proposalsPanel.add(Box.createVerticalStrut(10));
        }
        if(proposals.isEmpty()) {
            JLabel message = new JLabel("Il n'y a aucune proposition en attente d'acceptation");
            message.setFont(new Font("Arial", Font.PLAIN, 20));
            proposalsPanel.add(message);
        }
    }

    private static JPanel getProposalPanel(Proposal p) {
        JPanel proposalPanel = new JPanel(new BorderLayout());
        proposalPanel.setBackground(Color.lightGray);
        proposalPanel.setMaximumSize(new Dimension(1200, 50));
        JLabel proposalTitle = new JLabel(p.getTitle());
        proposalPanel.setBorder(BorderFactory.createEmptyBorder(6, 10, 0, 0));
        proposalPanel.setFont(new Font("Arial", Font.PLAIN, 20));

        JPanel infos = new JPanel();
        infos.setBackground(Color.lightGray);
        infos.setLayout(new BoxLayout(infos, BoxLayout.X_AXIS));
        JLabel satisfaction = new JLabel(p.satisfiedUser()+"");
        JLabel budget = new JLabel(p.getBudget()+ " €");
        infos.add(budget);
        infos.add(Box.createHorizontalStrut(30));
        infos.add(satisfaction);
        infos.add(Box.createHorizontalStrut(10));
        proposalPanel.add(infos, BorderLayout.EAST);
        proposalPanel.add(proposalTitle, BorderLayout.WEST);
        return proposalPanel;
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
