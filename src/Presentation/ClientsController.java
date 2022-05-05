package Presentation;

import BusinessLogic.ClientBusinessLogic;
import BusinessLogic.Utility;
import Model.Client;
import Model.OperationType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientsController implements ActionListener {

    private final ClientsView clientsView;
    private ModifyClientController observant;
    private ClientBusinessLogic clientBusinessLogic;

    public ClientsController(ClientsView clientsView) {
        this.clientsView = clientsView;
        clientBusinessLogic = new ClientBusinessLogic();

        displayAllClients();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(clientsView.getAddClientButton())) {
            ModifyClientView modifyClientView = new ModifyClientView(OperationType.add, null);
            modifyClientView.setVisible(true);
            modifyClientView.getController().setObserver(this);
        } else if (e.getSource().equals(clientsView.getEditClientButton())) {
            String clientIDString = clientsView.getEditClientID().getText();
            int clientID = Utility.getNumberFrom(clientIDString);

            if (clientID < 0) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "Please provide the ID of the client you want to delete.");
            } else {
                ModifyClientView modifyClientView = new ModifyClientView(OperationType.edit, clientID);
                modifyClientView.setVisible(true);
                modifyClientView.getController().setObserver(this);
            }
        } else if (e.getSource().equals(clientsView.getDeleteClientButton())) {
            String clientIDString = clientsView.getDeleteClientID().getText();
            int clientID = Utility.getNumberFrom(clientIDString);

            if (clientID < 0) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "Please provide the ID of the client you want to delete.");
            } else {
                boolean deleteStatus = clientBusinessLogic.deleteClient(clientID);

                if (deleteStatus == true) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                            "Client removed successfully.");
                    displayAllClients();
                } else {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                            "Could not remove the desired client.");
                }
            }
        }
    }

    // Observer Functions
    public void didUpdateClientsList() {
        displayAllClients();
    }

    // Private Functions
    private void displayAllClients() {
        String[] columnNames = {"ID", "Name", "Birth year", "Phone number", "Email"};
        Object[][] tableData = getTableData();

        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames);

        clientsView.getClientsTable().setModel(tableModel);
    }

    private Object[][] getTableData() {
        Object[][] tableData = new Object[100][5];
        ArrayList<Client> clients = clientBusinessLogic.getAllClients();
        int currentRow = 0;

        for (Client client: clients) {
            Object[] rowData = {client.getID(),
                                client.getName(),
                                client.getBirthYear(),
                                client.getPhoneNumber(),
                                client.getEmail()};
            tableData[currentRow] = rowData.clone();
            currentRow++;
        }

        return tableData;
    }

}
