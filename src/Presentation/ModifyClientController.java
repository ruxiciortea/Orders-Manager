package Presentation;

import BusinessLogic.ClientBusinessLogic;
import Model.Client;
import Model.OperationType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyClientController implements ActionListener {

    ModifyClientView modifyClientView;

    private String name;
    private int birthYear;
    private String phoneNumber;
    private String email;

    private ClientsController observer;
    private OperationType currentOperationType;
    private Integer clientToEditID;
    private ClientBusinessLogic clientBusinessLogic;

    public ModifyClientController(ModifyClientView modifyClientView, OperationType operationType, Integer clientToEditID) {
        this.modifyClientView = modifyClientView;
        currentOperationType = operationType;
        this.clientToEditID = clientToEditID;
        clientBusinessLogic = new ClientBusinessLogic();

        if (operationType.equals(OperationType.add)) {
            modifyClientView.getAddButton().setText("Add");
        } else if (operationType.equals(OperationType.edit)) {
            modifyClientView.getAddButton().setText("Edit");

            if (clientToEditID != null) {
                fillFieldsForClient(clientToEditID);
            }
        }
    }

    public void setObserver(ClientsController observer) {
        this.observer = observer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(modifyClientView.getAddButton())) {
            String name = modifyClientView.getNameTextField().getText();
            String birthYearString = modifyClientView.getBirthYearTextField().getText();
            String phoneNumber = modifyClientView.getPhoneNumberTextField().getText();
            String email = modifyClientView.getEmailTextField().getText();

            if (checkInputData(name, birthYearString, phoneNumber, email) == false) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "The input data is incorrect.");
            } else {
                if (currentOperationType.equals(OperationType.add)) {
                    addClient();
                } else if (currentOperationType.equals(OperationType.edit)) {
                    editClient();
                }
            }
        }
    }

    // Private Functions
    private boolean checkInputData(String name, String birthYearString, String phoneNumber, String email) {
        if (name.isEmpty() || birthYearString.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
            return false;
        }

        try{
            this.birthYear = Integer.parseInt(birthYearString);
        }
        catch (NumberFormatException ex){
            return false;
        }

        if (!phoneNumber.matches("[0-9]+") || phoneNumber.length() != 10 || !email.contains("@")) {
            return false;
        }

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;

        return true;
    }

    private void addClient() {
        Client newClient = new Client(this.name, this.birthYear, this.phoneNumber, this.email);
        newClient = (clientBusinessLogic.insertClient(newClient));

        if (newClient.getID() < 0) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Could not add new client.");
        } else {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "New client added successfully.");
            observer.didUpdateClientsList();
            modifyClientView.dispose();
        }
    }

    private Client getClientToEdit(int clientID) {
        Client clientToEdit;
        clientToEdit = clientBusinessLogic.findClientByID(clientID);
        return clientToEdit;
    }

    private void fillFieldsForClient(Integer clientToEditID) {
        Client clientToEdit = getClientToEdit(clientToEditID);

        if (clientToEdit == null) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "This client does not exist.");
            modifyClientView.dispose();
        } else {
            modifyClientView.getNameTextField().setText(clientToEdit.getName());
            modifyClientView.getBirthYearTextField().setText(String.valueOf(clientToEdit.getBirthYear()));
            modifyClientView.getPhoneNumberTextField().setText(clientToEdit.getPhoneNumber());
            modifyClientView.getEmailTextField().setText(clientToEdit.getEmail());
        }
    }

    private void editClient() {
        Client newClientData = new Client(this.name, this.birthYear, this.phoneNumber, this.email);
        boolean editStatus = clientBusinessLogic.updateClient(clientToEditID, newClientData);

        if (editStatus == false) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Could not edit the client.");
        } else {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "The client was edited.");
            observer.didUpdateClientsList();
            modifyClientView.dispose();
        }
    }

}
