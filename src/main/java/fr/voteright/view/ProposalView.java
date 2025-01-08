package fr.voteright.view;

import fr.voteright.controller.NavigationManager;
import fr.voteright.controller.ProposalController;
import fr.voteright.model.Proposal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author estebanrodriguez
 */
public class ProposalView extends View implements ParametrizedView{

    private int id;
    private NavigationManager navigationManager;

    public ProposalView(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    public void display() {
        ProposalController proposalController = new ProposalController();
        Proposal proposal = proposalController.getProposal(id);

        setLayout(new BorderLayout());
        add(new Header(true, navigationManager), BorderLayout.NORTH);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Color.WHITE);
        add(main, BorderLayout.CENTER);

        JLabel title = new JLabel(proposal.getTitle());
        title.setBorder(new EmptyBorder(10, 0, 0, 0));
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(Box.createRigidArea(new Dimension(0, 20)));
        main.add(title);

        main.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 0, 0));
        centerPanel.setBackground(Color.WHITE);

        JTextArea description = new JTextArea(proposal.getDescription());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setBorder(BorderFactory.createTitledBorder("Description"));
        description.setFont(new Font("Arial", Font.PLAIN, 20));
        centerPanel.add(description);

        JPanel statsPanel = new JPanel(new GridLayout(2,2));
        statsPanel.setBackground(Color.WHITE);
        JLabel likeLabel = new JLabel("Pourcentage de j'aime :");
        likeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        JLabel valueLikeLabel = new JLabel(proposal.getSatisfaction()+ "%");
        valueLikeLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        statsPanel.add(likeLabel);
        statsPanel.add(valueLikeLabel);

        JLabel themeLabel = new JLabel("Thème de la proposition :");
        themeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        JLabel themeNameLabel = new JLabel(proposal.getThemeTextuel());
        themeNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        statsPanel.add(themeLabel);
        statsPanel.add(themeNameLabel);

        centerPanel.add(statsPanel);

        main.add(centerPanel);

        main.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel budgetPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        budgetPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
        budgetPanel.setBackground(Color.WHITE);
        JLabel budget = new JLabel("Budget de la proposition : " + proposal.getBudget() + " €");
        budget.setFont(new Font("Arial", Font.BOLD, 24));
        budget.setAlignmentX(Component.LEFT_ALIGNMENT);
        budget.setAlignmentY(Component.TOP_ALIGNMENT);
        budgetPanel.add(budget);

        main.add(budgetPanel);

        main.add(Box.createRigidArea(new Dimension(0, 60)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
        buttonPanel.setBackground(Color.WHITE);
        JButton returnButton = new JButton("Retour");
        returnButton.setPreferredSize(new Dimension(150, 50));
        JButton validateButton = new JButton("Valider");
        validateButton.setPreferredSize(new Dimension(150, 50));
        buttonPanel.add(returnButton);
        buttonPanel.add(validateButton);
        main.add(buttonPanel);

        returnButton.addActionListener(e->navigationManager.showView("community"));

        validateButton.addActionListener(e-> {
            int result = JOptionPane.showConfirmDialog(
                    ProposalView.this,
                    "Êtes-vous sûr de vouloir valider cette proposition ?\nCette action sera irréversible.",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                boolean success = proposalController.validateProposal(id);
                if (success) {
                    JOptionPane.showMessageDialog(
                            ProposalView.this,
                            "Proposition validée !",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("community", proposal.getCommunityId());
                    navigationManager.showView("community", params);
                } else {
                    JOptionPane.showMessageDialog(
                            ProposalView.this,
                            "Erreur lors de la validation de la proposition.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    @Override
    public void setParams(Map<String, Object> params) {
        if (params != null && params.containsKey("proposal")) {
            this.removeAll();
            this.id = (int) params.get("proposal");
            this.display();
        }
    }
}