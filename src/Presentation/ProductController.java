package Presentation;

import BusinessLogic.ProductBusinessLogic;
import Model.Client;
import Model.OperationType;
import Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProductController implements ActionListener {

    ProductsView productsView;
    ProductBusinessLogic productBusinessLogic;

    public ProductController(ProductsView productsView) {
        this.productsView = productsView;
        productBusinessLogic = new ProductBusinessLogic();

        displayAllProducts();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(productsView.getAddProductButton())) {
            ModifyProductView modifyProductView = new ModifyProductView(OperationType.add, null);
            modifyProductView.setVisible(true);
            modifyProductView.getController().setObserver(this);
        } else if (e.getSource().equals(productsView.getEditProductButton())) {
            String productIDString = productsView.getEditProductID().getText();
            int productID = getID(productIDString);

            if (productID < 0) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "Please provide the ID of the product you want to delete.");
            } else {
                ModifyProductView modifyProductView = new ModifyProductView(OperationType.edit, productID);
                modifyProductView.setVisible(true);
                modifyProductView.getController().setObserver(this);
            }
        } else if (e.getSource().equals(productsView.getDeleteProductButton())) {
            String productIDString = productsView.getDeleteProductID().getText();
            int productID = getID(productIDString);

            if (productID < 0) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "Please provide the ID of the product you want to delete.");
            } else {
                boolean deleteStatus = productBusinessLogic.deleteProduct(productID);

                if (deleteStatus == true) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                            "Product removed successfully.");
                    displayAllProducts();
                } else {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                            "Could not remove the desired product.");
                }
            }
        }
    }

    public void didUpdateProductsList() {
        displayAllProducts();
    }

    // Private Functions
    private int getID(String stringID) {
        if (stringID.isEmpty()) {
            return -1;
        }

        int clientID = -1;

        try{
            clientID = Integer.parseInt(stringID);
        }
        catch (NumberFormatException ex){
            return -1;
        }

        return clientID;
    }

    private void displayAllProducts() {
        String[] columnNames = {"ID", "Name", "Quantity", "Unit Price"};
        Object[][] tableData = getTableData();

        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames);

        productsView.getProductsTable().setModel(tableModel);
    }

    private Object[][] getTableData() {
        Object[][] tableData = new Object[100][5];
        ArrayList<Product> products = productBusinessLogic.getAllProducts();
        int currentRow = 0;

        for (Product product: products) {
            Object[] rowData = {product.getID(),
                    product.getName(),
                    product.getQuantity(),
                    product.getUnitPrice()};
            tableData[currentRow] = rowData.clone();
            currentRow++;
        }

        return tableData;
    }

}
