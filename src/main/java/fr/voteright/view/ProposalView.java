package fr.voteright.view;

import fr.voteright.controller.NavigationManager;
import fr.voteright.controller.ProposalController;
import fr.voteright.model.Proposal;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class ProposalView extends View {

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

        JPanel main = new JPanel(new BorderLayout());
    }
}
