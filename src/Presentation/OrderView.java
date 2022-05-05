package Presentation;

import javax.swing.*;

public class OrderView extends JFrame {

    private JPanel mainPanel;
    private JTextField quantityTextField;
    private JComboBox productsComboBox;
    private JComboBox clientsComboBox;
    private JButton createOrderButton;

    public OrderView() {
        setTitle("Create an order");
        setSize(600, 250);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        OrderController controller = new OrderController(this);
        createOrderButton.addActionListener(controller);
    }

    // Getters
    public JTextField getQuantityTextField() {
        return quantityTextField;
    }

    public JComboBox getProductsComboBox() {
        return productsComboBox;
    }

    public JComboBox getClientsComboBox() {
        return clientsComboBox;
    }

    public JButton getCreateOrderButton() {
        return createOrderButton;
    }

}
