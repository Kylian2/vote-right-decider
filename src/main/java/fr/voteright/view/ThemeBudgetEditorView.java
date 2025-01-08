package fr.voteright.view;

import fr.voteright.controller.CommunityController;
import fr.voteright.controller.NavigationManager;
import fr.voteright.model.Community;
import fr.voteright.model.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author mathieuguiborat
 */
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

        LinkedHashMap<String, String> mapOfIncorrectValues = new LinkedHashMap<>();

        HashMap<Theme, JSpinner> themesSpinner = new HashMap<>();
        for (Theme theme : themes) {
            JSpinner spinner = new JSpinner(new SpinnerNumberModel((float) theme.getBudget(), 0.00, 999999999999.99, 100));
            themesSpinner.put(theme, spinner);
        }

        AtomicReference<Double> sumOfThemes = new AtomicReference<>(sum(themesSpinner));

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

            JLabel euro = new JLabel("€");
            euro.setFont(new Font("Arial", Font.PLAIN, 28));

            block.add(nameLabel);
            block.add(themesSpinner.get(thm));
            block.add(euro);
            scrollablePanel.add(block);
        }
        JScrollPane scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setPreferredSize(new Dimension(550, 360));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

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

        JSpinner spinnerTotal = new JSpinner(new SpinnerNumberModel(community.getBudget(), 0.00, 999999999999.99, 100));
        spinnerTotal.setPreferredSize(new Dimension(110, 50));
        JTextField textFieldTotal = ((JSpinner.DefaultEditor) spinnerTotal.getEditor()).getTextField();
        textFieldTotal.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel euroTotal = new JLabel("€");
        euroTotal.setFont(new Font("Arial", Font.PLAIN, 28));

        budgetTotalPanel.add(spinnerTotal);
        budgetTotalPanel.add(euroTotal);

        JLabel budgetUsedLabel = new JLabel("Budget utilisé :");
        budgetUsedLabel.setFont(new Font("Arial", Font.BOLD, 28));
        budgetUsedLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel budgetUsedValue = new JLabel(String.valueOf(community.getUsedBudget()) + " €");
        budgetUsedValue.setFont(new Font("Arial", Font.PLAIN, 24));
        budgetUsedValue.setHorizontalAlignment(SwingConstants.LEFT);
        budgetUsedValue.setBorder(new EmptyBorder(0, 15, 0, 0));


        JLabel fixedFeesLabel = new JLabel("Frais fixes :");
        fixedFeesLabel.setFont(new Font("Arial", Font.BOLD, 28));
        fixedFeesLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel fixedFeesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fixedFeesPanel.setBorder(new EmptyBorder(22, 10, 0, 0));
        fixedFeesPanel.setBackground(Color.WHITE);

        JSpinner spinnerFixedFees = new JSpinner(new SpinnerNumberModel(community.getFixedFees(), 0.00, 999999999999.99, 100));
        spinnerFixedFees.setPreferredSize(new Dimension(110, 50));
        JTextField textFieldFixedFees = ((JSpinner.DefaultEditor) spinnerFixedFees.getEditor()).getTextField();
        textFieldFixedFees.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel euroFixedfees = new JLabel("€");
        euroFixedfees.setFont(new Font("Arial", Font.PLAIN, 28));

        fixedFeesPanel.add(spinnerFixedFees);
        fixedFeesPanel.add(euroFixedfees);

        JLabel elementName = new JLabel("");
        elementName.setFont(new Font("Arial", Font.PLAIN, 22));
        elementName.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel incorrectValueLabel = new JLabel("");
        incorrectValueLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        incorrectValueLabel.setHorizontalAlignment(SwingConstants.LEFT);

        rightPanel.add(totalBudgetLabel);
        rightPanel.add(budgetTotalPanel);
        rightPanel.add(budgetUsedLabel);
        rightPanel.add(budgetUsedValue);
        rightPanel.add(fixedFeesLabel);
        rightPanel.add(fixedFeesPanel);
        rightPanel.add(elementName);
        rightPanel.add(incorrectValueLabel);

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
        validateButton.addActionListener(e ->confirmation(communityController, community, themesSpinner, spinnerTotal, spinnerFixedFees));
        validateButton.setPreferredSize(new Dimension(100, 30));
        validateButton.setBackground(Color.BLACK);
        validateButton.setForeground(Color.WHITE);

        southPanel.add(returnButton);
        southPanel.add(validateButton);

        spinnerTotal.addChangeListener(e -> {
            if ((double) spinnerTotal.getValue() < (double) spinnerFixedFees.getValue() + sumOfThemes.get()){
                mapOfIncorrectValues.put("Budget total", "budget trop bas");
            } else {
                mapOfIncorrectValues.remove("Budget total");
            }
            displayIncorrectValuesUpdate(mapOfIncorrectValues, elementName, incorrectValueLabel, validateButton);
        });

        spinnerFixedFees.addChangeListener(e -> {
            if ((double) spinnerFixedFees.getValue() > (double) spinnerTotal.getValue() - sumOfThemes.get()){
                mapOfIncorrectValues.put("Frais fixes", "budget trop haut");
            } else {
                mapOfIncorrectValues.remove("Frais fixes");
            }
            displayIncorrectValuesUpdate(mapOfIncorrectValues, elementName, incorrectValueLabel, validateButton);
        });

        for (Theme theme : themesSpinner.keySet()) {
            themesSpinner.get(theme).addChangeListener(e -> {
                sumOfThemes.set(sum(themesSpinner));
                if((double) theme.getUsedBudget() > (double) themesSpinner.get(theme).getValue()){
                    mapOfIncorrectValues.put(theme.getName(), "budget trop bas");
                } else if((double) spinnerTotal.getValue() - (double) spinnerFixedFees.getValue() - sumOfThemes.get() < 0 ){
                    mapOfIncorrectValues.put(theme.getName(), "budget trop haut");
                } else {
                    mapOfIncorrectValues.remove(theme.getName());
                }
                displayIncorrectValuesUpdate(mapOfIncorrectValues, elementName, incorrectValueLabel, validateButton);
            });
        }

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

    public double sum(HashMap<Theme, JSpinner> themesSpinner) {
        double result = 0;
        for (Theme thm : themesSpinner.keySet()) result += (double) themesSpinner.get(thm).getValue();
        return result;
    }

    public void displayIncorrectValuesUpdate(LinkedHashMap<String, String> mapOfIncorrectValues, JLabel elementName, JLabel incorrectValueLabel, JButton validateButton) {
        if (!mapOfIncorrectValues.isEmpty()) {
            String lastKey = null;
            lastKey = mapOfIncorrectValues.keySet().toArray(new String[0])[mapOfIncorrectValues.size() - 1];
            if(lastKey != null) {
                String lastValue = mapOfIncorrectValues.get(lastKey);
                elementName.setText("<html><span style='color:red;'>" + lastKey + " : </span></html>");
                incorrectValueLabel.setText("<html><span style='color:red;'>" + lastValue + "</span></html>");
            }
            if(validateButton.isEnabled()){validateButton.setEnabled(false);}
        } else {
            elementName.setText("");
            incorrectValueLabel.setText("");
            if(!validateButton.isEnabled()){validateButton.setEnabled(true);}
        }
    }

    public boolean validate(CommunityController communityController, Community community, HashMap<Theme, JSpinner> themesMap, JSpinner spinnerTotal, JSpinner spinnerFixedFees) {
        HashMap<Integer, Double> body = new HashMap<>();
        body.put(0, (Double) spinnerTotal.getValue());
        body.put(-1, (Double) spinnerFixedFees.getValue());
        for (Theme thm : themesMap.keySet()) {
            body.put(thm.getId(), (Double) themesMap.get(thm).getValue());
        }

        return communityController.patchBudget(community, body);
    }

    public void confirmation(CommunityController communityController, Community community, HashMap<Theme, JSpinner> themesMap, JSpinner spinnerTotal, JSpinner spinnerFixedFees) {
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
        cancelButton.addActionListener(e-> dialog.dispose());

        JButton validateButton = new JButton("Valider");
        validateButton.setPreferredSize(new Dimension(100, 40));
        validateButton.addActionListener(e -> {
            boolean success = validate(communityController, community, themesMap, spinnerTotal, spinnerFixedFees);

            label.setFont(new Font("Arial", Font.PLAIN, 22));
            if (success) {
                label.setText("<html><span style='color:green;'>Modifications effectuées.</span></html>");
            } else {
                label.setText("<html><span style='color:red;'>Modifications échouées.</span></html>");
            }

            validateButton.setEnabled(false);
            cancelButton.setText("Fermer");
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(validateButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}