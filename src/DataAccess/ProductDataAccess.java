package DataAccess;

import Model.Client;
import Model.Product;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ProductDataAccess extends AbstractDataAccess<Product> {

    public ArrayList<Product> getAllProducts() {
        return super.findAll();
    }

    public Product findById(int productID) {
        return super.findById(productID);
    }

    public int insert(Product product) {
        return super.insert(product);
    }

    public boolean update(int productID, Product product) {
        return super.update(productID, product);
    }

    public boolean delete(int productID) {
        return super.delete(productID);
    }

}
