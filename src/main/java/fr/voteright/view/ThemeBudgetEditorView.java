package fr.voteright.view;

import fr.voteright.controller.CommunityController;
import fr.voteright.controller.NavigationManager;
import fr.voteright.model.Community;
import fr.voteright.model.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThemeBudgetEditorView extends View implements ParametrizedView{
    private int id;
    private NavigationManager navigationManager;

    public ThemeBudgetEditorView(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    public void display() {
        CommunityController communityController = new CommunityController();
        Community community = communityController.getCommunity(id);
        communityController.getBudget(community);
        ArrayList<Theme> themes = community.getThemes();

        HashMap<Theme, JSpinner> themesSpinner = new HashMap<>();
        for (Theme theme : themes) {
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(theme.getBudget(), 0, 1000000000, 100));
            themesSpinner.put(theme, spinner);
        }

        setLayout(new BorderLayout());
        add(new Header(true, navigationManager), BorderLayout.NORTH);

        JPanel main = new JPanel(new BorderLayout());

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Modification du budget");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBorder(new EmptyBorder(40, 40, 0, 0));
        northPanel.add(titleLabel);
        northPanel.setPreferredSize(new Dimension(1280, 125));
        northPanel.setBackground(Color.WHITE);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(640, 420));
        leftPanel.setBorder(new EmptyBorder(20, 60, 0, 0));

        JPanel scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));
        for (Theme thm : themes){
            JPanel block = new JPanel(new FlowLayout(FlowLayout.LEFT));
            block.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            block.setBackground(Color.WHITE);
            JLabel nameLabel = new JLabel(thm.getName() + " : ");
            nameLabel.setFont(new Font("Arial", Font.BOLD, 28));

            themesSpinner.get(thm).setPreferredSize(new Dimension(100, 50));
            JTextField textField = ((JSpinner.DefaultEditor) themesSpinner.get(thm).getEditor()).getTextField();
            textField.setFont(new Font("Arial", Font.PLAIN, 20));

            JLabel euro = new JLabel("€ /an");
            euro.setFont(new Font("Arial", Font.PLAIN, 28));

            block.add(nameLabel);
            block.add(themesSpinner.get(thm));
            block.add(euro);
            scrollablePanel.add(block);
        }
        JScrollPane scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setPreferredSize(new Dimension(550, 360));

        leftPanel.add(scrollPane);

        JPanel rightPanel = new JPanel(new GridLayout(4, 2));
        rightPanel.setPreferredSize(new Dimension(640, 420));
        rightPanel.setBackground(Color.WHITE);

        JLabel totalBudgetLabel = new JLabel("Budget total :");
        totalBudgetLabel.setFont(new Font("Arial", Font.BOLD, 28));
        totalBudgetLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel budgetTotalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        budgetTotalPanel.setBorder(new EmptyBorder(22, 10, 0, 0));
        budgetTotalPanel.setBackground(Color.WHITE);

        SpinnerNumberModel spinnerModelTotal = new SpinnerNumberModel(community.getBudget(), 0, 1000000000, 100);
        JSpinner spinnerTotal = new JSpinner(spinnerModelTotal);
        spinnerTotal.setPreferredSize(new Dimension(110, 50));
        JTextField textFieldTotal = ((JSpinner.DefaultEditor) spinnerTotal.getEditor()).getTextField();
        textFieldTotal.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel euroTotal = new JLabel("€ /an");
        euroTotal.setFont(new Font("Arial", Font.PLAIN, 28));

        budgetTotalPanel.add(spinnerTotal);
        budgetTotalPanel.add(euroTotal);

        JLabel budgetUsedLabel = new JLabel("Budget utilisé :");
        budgetUsedLabel.setFont(new Font("Arial", Font.BOLD, 28));
        budgetUsedLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel budgetUsedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        budgetUsedPanel.setBorder(new EmptyBorder(22, 10, 0, 0));
        budgetUsedPanel.setBackground(Color.WHITE);

        SpinnerNumberModel spinnerModelUsed= new SpinnerNumberModel(community.getUsedBudget(), 0, 1000000000, 100);
        JSpinner spinnerUsed = new JSpinner(spinnerModelUsed);
        spinnerUsed.setPreferredSize(new Dimension(110, 50));
        JTextField textFieldUsed = ((JSpinner.DefaultEditor) spinnerUsed.getEditor()).getTextField();
        textFieldUsed.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel euroUsed = new JLabel("€ /an");
        euroUsed.setFont(new Font("Arial", Font.PLAIN, 28));

        budgetUsedPanel.add(spinnerUsed);
        budgetUsedPanel.add(euroUsed);

        rightPanel.add(totalBudgetLabel);
        rightPanel.add(budgetTotalPanel);
        rightPanel.add(budgetUsedLabel);
        rightPanel.add(budgetUsedPanel);
        JPanel rightPanel5 = new JPanel();
        rightPanel5.setBackground(Color.WHITE);
        JPanel rightPanel6 = new JPanel();
        rightPanel6.setBackground(Color.WHITE);
        JPanel rightPanel7 = new JPanel();
        rightPanel7.setBackground(Color.WHITE);
        JPanel rightPanel8 = new JPanel();
        rightPanel8.setBackground(Color.WHITE);
        rightPanel.add(rightPanel5);
        rightPanel.add(rightPanel6);
        rightPanel.add(rightPanel7);
        rightPanel.add(rightPanel8);

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.setPreferredSize(new Dimension(1280, 100));
        southPanel.setBorder(new EmptyBorder(20, 0, 0, 40));
        southPanel.setBackground(Color.WHITE);

        JButton returnButton = new JButton("Retour");
        returnButton.setPreferredSize(new Dimension(100, 30));
        returnButton.setBackground(Color.LIGHT_GRAY);
        returnButton.setForeground(Color.WHITE);
        HashMap<String, Object> paramReturn = new HashMap<>();
        paramReturn.put("community", community.getId());
        returnButton.addActionListener(e -> navigationManager.showView("community", paramReturn));

        JButton validateButton = new JButton("Valider");
        validateButton.addActionListener(e ->confirmation(themesSpinner));
        validateButton.setPreferredSize(new Dimension(100, 30));
        validateButton.setBackground(Color.BLACK);
        validateButton.setForeground(Color.WHITE);

        southPanel.add(returnButton);
        southPanel.add(validateButton);

        main.add(northPanel, BorderLayout.NORTH);
        main.add(centerPanel, BorderLayout.CENTER);
        main.add(southPanel, BorderLayout.SOUTH);

        add(main);
    }

    @Override
    public void setParams(Map<String, Object> params) {
        if (params != null && params.containsKey("themeBudgetEditor")) {
            this.removeAll();
            this.id = (int) params.get("themeBudgetEditor");
            this.display();
        }
    }


    public void validate(HashMap<Theme, JSpinner> themesMap) {
        for (Theme thm : themesMap.keySet()) {
            System.out.println(thm.getName() + " : " + themesMap.get(thm).getValue() + " € /an");
        }
    }

    public void confirmation(HashMap<Theme, JSpinner> themesMap) {
        if (themesMap != null) {
            JDialog dialog = new JDialog();
            dialog.setTitle("Confirmation");
            dialog.setSize(550, 225);
            dialog.setModal(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setLayout(new BorderLayout());

            JLabel label = new JLabel("<html>Êtes-vous sûr de vouloir valider ce budget ?<br>Cette action sera effective immédiatement.</html>", SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            dialog.add(label, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

            JButton cancelButton = new JButton("Annuler");
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Fermer la fenêtre modale
                    dialog.dispose();
                }
            });

            JButton validateButton = new JButton("Valider");
            validateButton.setPreferredSize(new Dimension(100, 40));
            validateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    validate(themesMap);
                    dialog.dispose();
                }
            });

            buttonPanel.add(cancelButton);
            buttonPanel.add(validateButton);

            dialog.add(buttonPanel, BorderLayout.SOUTH);

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }
}
