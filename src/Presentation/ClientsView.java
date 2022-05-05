package Presentation;

import javax.swing.*;

public class ClientsView extends JFrame {
    private JPanel mainPanel;
    private JPanel optionsPanel;
    private JPanel tablePanel;
    private JTable clientsTable;
    private JButton addClientButton;
    private JButton editClientButton;
    private JButton deleteClientButton;
    private JTextField editClientID;
    private JTextField deleteClientID;

    public ClientsView() {
        setTitle("Clients");
        setSize(650, 300);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        ClientsController controller = new ClientsController(this);

        addClientButton.addActionListener(controller);
        editClientButton.addActionListener(controller);
        deleteClientButton.addActionListener(controller);
    }

    // Getters
    public JTable getClientsTable() {
        return clientsTable;
    }

    public JButton getAddClientButton() {
        return addClientButton;
    }

    public JButton getEditClientButton() {
        return editClientButton;
    }

    public JButton getDeleteClientButton() {
        return deleteClientButton;
    }

    public JTextField getEditClientID() {
        return editClientID;
    }

    public JTextField getDeleteClientID() {
        return deleteClientID;
    }

}
