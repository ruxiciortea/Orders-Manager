package Presentation;

import BusinessLogic.ProductBusinessLogic;
import Model.Client;
import Model.OperationType;
import Model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyProductController implements ActionListener {

    private ModifyProductView modifyProductView;

    private String name;
    private int quantity;
    private int unitPrice;

    private OperationType operationType;
    private Integer productToEditID;
    private ProductController observer;
    private ProductBusinessLogic productBusinessLogic;


    public ModifyProductController(ModifyProductView modifyProductView, OperationType operationType, Integer productToEditID) {
        this.modifyProductView = modifyProductView;
        this.operationType = operationType;
        this.productToEditID = productToEditID;
        productBusinessLogic = new ProductBusinessLogic();

        if (operationType.equals(OperationType.add)) {
            modifyProductView.getAddButton().setText("Add");
        } else if (operationType.equals(OperationType.edit)) {
            modifyProductView.getAddButton().setText("Edit");

            if (productToEditID != null) {
                fillFieldsForProduct(productToEditID);
            }
        }
    }

    public void setObserver(ProductController observer) {
        this.observer = observer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(modifyProductView.getAddButton())) {
            String name = modifyProductView.getNameTextField().getText();
            String quantityString = modifyProductView.getQuantityTextField().getText();
            String unitPriceString = modifyProductView.getUnitPriceTextField().getText();

            if (checkInputData(name, quantityString, unitPriceString) == false) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "The input data is incorrect.");
            } else {
                if (operationType.equals(OperationType.add)) {
                    addProduct();
                } else if (operationType.equals(OperationType.edit)) {
                    editProduct();
                }
            }
        }
    }

    // Private Functions
    private void fillFieldsForProduct(int productToEditID) {
        Product productToEdit = getProductToEdit(productToEditID);

        if (productToEdit == null) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "This product does not exist.");
            modifyProductView.dispose();
        } else {
            modifyProductView.getNameTextField().setText(productToEdit.getName());
            modifyProductView.getQuantityTextField().setText(String.valueOf(productToEdit.getQuantity()));
            modifyProductView.getUnitPriceTextField().setText(String.valueOf(productToEdit.getUnitPrice()));
        }
    }

    private boolean checkInputData(String name, String quantityString, String unitPriceString) {
        try{
            this.quantity = Integer.parseInt(quantityString);
            this.unitPrice = Integer.parseInt(unitPriceString);
        }
        catch (NumberFormatException ex){
            return false;
        }

        this.name = name;

        return true;
    }

    private void addProduct() {
        Product newProduct = new Product(this.name, this.quantity, this.unitPrice);
        newProduct = (productBusinessLogic.insertProduct(newProduct));

        if (newProduct.getID() < 0) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Could not add new product.");
        } else {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "New product added successfully.");
            observer.didUpdateProductsList();
            modifyProductView.dispose();
        }
    }

    private Product getProductToEdit(int productID) {
        Product productToEdit;
        productToEdit = productBusinessLogic.findProductByID(productID);
        return productToEdit;
    }

    private void editProduct() {
        Product newProductData = new Product(this.name, this.quantity, this.unitPrice);
        boolean editStatus = productBusinessLogic.updateProduct(productToEditID, newProductData);

        if (editStatus == false) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Could not edit the product.");
        } else {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "The product was edited.");
            observer.didUpdateProductsList();
            modifyProductView.dispose();
        }
    }

}
