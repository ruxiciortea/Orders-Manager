package Presentation;

import Model.OperationType;

import javax.swing.*;

public class ModifyProductView extends JFrame {
    private JPanel mainPanel;
    private JButton addButton;
    private JTextField nameTextField;
    private JTextField quantityTextField;
    private JTextField unitPriceTextField;
    private ModifyProductController controller;

    public ModifyProductView(OperationType operationType, Integer productToEditID) {
        setTitle("New Client");
        setSize(400, 250);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        controller = new ModifyProductController(this, operationType, productToEditID);

        addButton.addActionListener(controller);
    }

    // Getters
    public JButton getAddButton() {
        return addButton;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getQuantityTextField() {
        return quantityTextField;
    }

    public JTextField getUnitPriceTextField() {
        return unitPriceTextField;
    }

    public ModifyProductController getController() {
        return controller;
    }

}
