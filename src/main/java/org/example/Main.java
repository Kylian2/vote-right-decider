package org.example;

import org.example.controller.NavigationManager;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("VoteRight");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1280, 720);
            frame.setLayout(new BorderLayout());
            frame.setResizable(false);

            NavigationManager navigationManager = new NavigationManager();
            navigationManager.start(frame);
        });
    }
}