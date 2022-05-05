package Presentation;

import BusinessLogic.ClientBusinessLogic;
import BusinessLogic.OrderBusinessLogic;
import BusinessLogic.ProductBusinessLogic;
import BusinessLogic.Utility;
import Model.Client;
import Model.OrderItem;
import Model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderController implements ActionListener {

    private OrderView orderView;
    private ClientBusinessLogic clientBusinessLogic;
    private ProductBusinessLogic productBusinessLogic;
    private OrderBusinessLogic orderBusinessLogic;

    private ArrayList<Client> clients;
    private ArrayList<Product> products;

    public OrderController(OrderView orderView) {
        this.orderView = orderView;

        clientBusinessLogic = new ClientBusinessLogic();
        productBusinessLogic = new ProductBusinessLogic();
        orderBusinessLogic = new OrderBusinessLogic();

        clients = clientBusinessLogic.getAllClients();
        products = productBusinessLogic.getAllProducts();

        populateClientsComboBox();
        populateProductsComboBox();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(orderView.getCreateOrderButton())) {
            String quantityString = orderView.getQuantityTextField().getText();
            int quantity = Utility.getNumberFrom(quantityString);

            if (quantity == -1) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "The quantity must be a numeric value.");
            } else {
                Client client = clients.get(orderView.getClientsComboBox().getSelectedIndex());
                Product product = products.get(orderView.getProductsComboBox().getSelectedIndex());

                if (product.getQuantity() < quantity) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                            "Not enough products in stock.");
                } else {
                    OrderItem newOrderItem = new OrderItem(client.getID(), product.getID(), quantity);
                    newOrderItem = orderBusinessLogic.insertOrder(newOrderItem);

                    if (newOrderItem.getID() != -1) {
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                "Order added.");
                        orderView.getQuantityTextField().setText("");
                    } else {
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                "Could not add the order, please try again.");
                    }

                }
            }
        }
    }

    // Private Functions
    private void populateClientsComboBox() {
        for (Client client: clients) {
            orderView.getClientsComboBox().addItem(client.getName());
        }
    }

    private void populateProductsComboBox() {
        for (Product product: products) {
            orderView.getProductsComboBox().addItem(product.getName());
        }
    }

}
