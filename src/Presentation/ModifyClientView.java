package Presentation;

import Model.OperationType;

import javax.swing.*;

public class ModifyClientView extends JFrame {
    private JPanel mainPanel;
    private JPanel fieldsPanel;
    private JPanel addPanel;
    private JTextField nameTextField;
    private JTextField birthYearTextField;
    private JTextField phoneNumberTextField;
    private JTextField emailTextField;
    private JButton addButton;
    private ModifyClientController controller;

    public ModifyClientView(OperationType operationType, Integer clientToEditID) {
        if (operationType.equals(OperationType.edit)) {
            setTitle("Edit Client");
        } else {
            setTitle("New Client");
        }

        setSize(400, 250);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        controller = new ModifyClientController(this, operationType, clientToEditID);

        addButton.addActionListener(controller);
    }

    // Getters
    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getBirthYearTextField() {
        return birthYearTextField;
    }

    public JTextField getPhoneNumberTextField() {
        return phoneNumberTextField;
    }

    public JTextField getEmailTextField() {
        return emailTextField;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public ModifyClientController getController() {
        return controller;
    }

}
