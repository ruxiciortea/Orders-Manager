package BusinessLogic;

import DataAccess.OrderDataAccess;
import Model.Client;
import Model.OrderItem;
import Model.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class used to communicate  with the OrderDataAccess class in order to perform the CRUD operations
 */
public class OrderBusinessLogic {

    private OrderDataAccess orderDataAccess = new OrderDataAccess();
    private ClientBusinessLogic clientBusinessLogic = new ClientBusinessLogic();
    private ProductBusinessLogic productBusinessLogic = new ProductBusinessLogic();

    public OrderItem insertOrder(OrderItem orderItem) {
        orderItem.setID(orderDataAccess.insert(orderItem));
        generateBillPdf(orderItem);
        return orderItem;
    }

    // Private Functions
    private boolean generateBillPdf(OrderItem order) {
        File outputFile;
        FileWriter fileWriter;

        Client client = clientBusinessLogic.findClientByID(order.getClientID());
        Product product = productBusinessLogic.findProductByID(order.getProductID());

        try {
            outputFile = new File("order" + order.getID());

            if(outputFile.createNewFile()) {
                System.out.println("File created\n");
            } else {
                System.out.println("File already exists\n");
            }

            fileWriter = new FileWriter("order" + order.getID());
            fileWriter.write("Order " + order.getID() + "\n");
            fileWriter.write("Client name: " + client.getName() + "\n");
            fileWriter.write("Product: " + product.getName() + "\n");
            fileWriter.write("Quantity:  " + order.getQuantity());

            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Could not create result file\n");
        }

        return true;
    }

}
