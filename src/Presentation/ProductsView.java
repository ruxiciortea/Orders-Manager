package Presentation;

import javax.swing.*;

public class ProductsView extends JFrame {
    private JPanel mainPanel;
    private JPanel optionsPanel;
    private JPanel productsPanel;
    private JButton addProductButton;
    private JButton editProductButton;
    private JButton deleteProductButton;
    private JTable productsTable;
    private JTextField editProductID;
    private JTextField deleteProductID;

    public ProductsView() {
        setTitle("Products");
        setSize(650, 300);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        ProductController controller = new ProductController(this);

        addProductButton.addActionListener(controller);
        editProductButton.addActionListener(controller);
        deleteProductButton.addActionListener(controller);
    }

    // Getters
    public JButton getAddProductButton() {
        return addProductButton;
    }

    public JButton getEditProductButton() {
        return editProductButton;
    }

    public JButton getDeleteProductButton() {
        return deleteProductButton;
    }

    public JTable getProductsTable() {
        return productsTable;
    }

    public JTextField getEditProductID() {
        return editProductID;
    }

    public JTextField getDeleteProductID() {
        return deleteProductID;
    }

}
