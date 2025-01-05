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

        setLayout(new BorderLayout());
        add(new Header(true, navigationManager), BorderLayout.NORTH);

        JPanel main = new JPanel(new BorderLayout());

        // Créer le panel du nord
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Modification du budget");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBorder(new EmptyBorder(40, 40, 0, 0));
        northPanel.add(titleLabel);
        northPanel.setPreferredSize(new Dimension(1280, 125));
        northPanel.setBackground(Color.WHITE);


        // Créer le panel du centre
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));


        // Panel de gauche
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(640, 420));
        leftPanel.setBorder(new EmptyBorder(20, 60, 0, 0));
        // Créer le panel scrollable
        JPanel scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));
        // Remplir le panel scrollable
        for (Theme thm : themes){
            JPanel block = new JPanel(new FlowLayout(FlowLayout.LEFT));
            block.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            block.setBackground(Color.WHITE);
            JLabel nameLabel = new JLabel(thm.getName() + " : ");
            nameLabel.setFont(new Font("Arial", Font.BOLD, 28));

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(thm.getBudget(), 0, 1000000000, 100);
            JSpinner spinner = new JSpinner(spinnerModel);
            spinner.setPreferredSize(new Dimension(100, 50));
            JTextField textField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
            // Centre le texte à l'intérieur du JTextField
            textField.setFont(new Font("Arial", Font.PLAIN, 20));
            // Ajouter le symbole €
            JLabel euro = new JLabel("€");
            euro.setFont(new Font("Arial", Font.PLAIN, 28));
            // Ajouter le spinnerTotal au panel budget total
            block.add(nameLabel);
            block.add(spinner);
            block.add(euro);
            scrollablePanel.add(block);
        }
        // Ajoute le panel scrollable dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setPreferredSize(new Dimension(550, 360));
        // Ajouter le panel scrollable au panel de gauche
        leftPanel.add(scrollPane);

        // Panel de droite
        JPanel rightPanel = new JPanel(new GridLayout(4, 2));
        rightPanel.setPreferredSize(new Dimension(640, 420));
        rightPanel.setBackground(Color.WHITE);

        // Label "Budget Total :"
        JLabel totalBudgetLabel = new JLabel("Budget total :");
        totalBudgetLabel.setFont(new Font("Arial", Font.BOLD, 28));
        totalBudgetLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Panel budget total
        JPanel budgetTotalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        budgetTotalPanel.setBorder(new EmptyBorder(22, 10, 0, 0));
        budgetTotalPanel.setBackground(Color.WHITE);
        // Spinner budget total
        SpinnerNumberModel spinnerModelTotal = new SpinnerNumberModel(community.getBudget(), 0, 1000000000, 100);
        JSpinner spinnerTotal = new JSpinner(spinnerModelTotal);
        spinnerTotal.setPreferredSize(new Dimension(110, 50));
        JTextField textFieldTotal = ((JSpinner.DefaultEditor) spinnerTotal.getEditor()).getTextField();
        // Centre le texte à l'intérieur du JTextField
        textFieldTotal.setFont(new Font("Arial", Font.PLAIN, 20));
        // Ajouter le symbole €
        JLabel euroTotal = new JLabel("€");
        euroTotal.setFont(new Font("Arial", Font.PLAIN, 28));
        // Ajouter le spinnerTotal au panel budget total
        budgetTotalPanel.add(spinnerTotal);
        budgetTotalPanel.add(euroTotal);


        // Label "Budget utilisé :"
        JLabel budgetUsedLabel = new JLabel("Budget utilisé :");
        budgetUsedLabel.setFont(new Font("Arial", Font.BOLD, 28));
        budgetUsedLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Panel budget utilisé
        JPanel budgetUsedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        budgetUsedPanel.setBorder(new EmptyBorder(22, 10, 0, 0));
        budgetUsedPanel.setBackground(Color.WHITE);
        // Spinner budget utilisé
        SpinnerNumberModel spinnerModelUsed= new SpinnerNumberModel(community.getUsedBudget(), 0, 1000000000, 100);
        JSpinner spinnerUsed = new JSpinner(spinnerModelUsed);
        spinnerUsed.setPreferredSize(new Dimension(110, 50));
        JTextField textFieldUsed = ((JSpinner.DefaultEditor) spinnerUsed.getEditor()).getTextField();
        // Centre le texte à l'intérieur du JTextField
        textFieldUsed.setFont(new Font("Arial", Font.PLAIN, 20));
        // Ajouter le symbole €
        JLabel euroUsed = new JLabel("€");
        euroUsed.setFont(new Font("Arial", Font.PLAIN, 28));
        // Ajouter le spinnerUsed au panel budget utilisé
        budgetUsedPanel.add(spinnerUsed);
        budgetUsedPanel.add(euroUsed);

        // Ajouter les différents éléments au panel de droite
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


        // Ajouter le panel de gauche et le panel de droite au panel du centre
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);


        // Panel du sud
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.setPreferredSize(new Dimension(1280, 100));
        southPanel.setBorder(new EmptyBorder(20, 0, 0, 40));
        southPanel.setBackground(Color.WHITE);

        // Créer le bouton retour
        JButton returnButton = new JButton("Retour");
        returnButton.setPreferredSize(new Dimension(100, 30));
        returnButton.setBackground(Color.LIGHT_GRAY);
        returnButton.setForeground(Color.WHITE);
        // Ecouter le bouton retour
        HashMap<String, Object> paramReturn = new HashMap<>();
        paramReturn.put("community", community.getId());
        returnButton.addActionListener(e -> navigationManager.showView("community", paramReturn));
        // Créer le bouton valider
        JButton validateButton = new JButton("Valider");
        validateButton.setPreferredSize(new Dimension(100, 30));
        validateButton.setBackground(Color.BLACK);
        validateButton.setForeground(Color.WHITE);
        // AJouter les boutons au panel du sud
        southPanel.add(returnButton);
        southPanel.add(validateButton);

        // Ajouter les différents panels au panel main
        main.add(northPanel, BorderLayout.NORTH);
        main.add(centerPanel, BorderLayout.CENTER);
        main.add(southPanel, BorderLayout.SOUTH);

        // Ajouter le panel main à la page principal
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
}
