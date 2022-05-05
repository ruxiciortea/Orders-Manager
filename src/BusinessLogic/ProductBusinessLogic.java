package BusinessLogic;

import DataAccess.ProductDataAccess;
import Model.*;

import java.util.ArrayList;

/**
 * Class used to communicate  with the ProductDataAccess class in order to perform the CRUD operations
 */
public class ProductBusinessLogic {

    private ProductDataAccess productDataAccess;

    public ProductBusinessLogic() {
        productDataAccess = new ProductDataAccess();
    }

    // Public Methods
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> allProducts = productDataAccess.getAllProducts();
        return allProducts;
    }

    public Product findProductByID(int productID) {
        Product product = productDataAccess.findById(productID);
        return product;
    }

    public Product insertProduct(Product product) {
        product.setID(productDataAccess.insert(product));
        return product;
    }

    public boolean deleteProduct(int productID) {
        boolean deleteStatus = productDataAccess.delete(productID);
        return deleteStatus;
    }

    public boolean updateProduct(int productID, Product newProductData) {
        boolean updateStatus = productDataAccess.update(productID, newProductData);
        return updateStatus;
    }

}
