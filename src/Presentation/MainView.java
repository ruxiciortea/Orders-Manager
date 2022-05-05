package Presentation;

import javax.swing.*;

public class MainView extends JFrame {
    private JPanel mainPanel;
    private JPanel optionsPanel;
    private JButton clientsButton;
    private JButton productsButton;
    private JButton ordersButton;

    public MainView() {
        setTitle("Orders Manager");
        setSize(400, 120);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        MainController controller = new MainController(this);
        clientsButton.addActionListener(controller);
        productsButton.addActionListener(controller);
        ordersButton.addActionListener(controller);
    }

    // Getters
    public JButton getClientsButton() {
        return clientsButton;
    }

    public JButton getProductsButton() {
        return productsButton;
    }

    public JButton getOrdersButton() {
        return ordersButton;
    }
}
