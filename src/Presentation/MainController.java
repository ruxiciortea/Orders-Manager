package Presentation;

import Presentation.ClientsView;
import Presentation.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener {

    private final MainView mainView;

    public MainController(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(mainView.getClientsButton())) {
            ClientsView clientsView = new ClientsView();
            clientsView.setVisible(true);
        } else if (e.getSource().equals(mainView.getProductsButton())) {
            ProductsView productsView = new ProductsView();
            productsView.setVisible(true);
        } else if (e.getSource().equals(mainView.getOrdersButton())) {
            OrderView orderView = new OrderView();
            orderView.setVisible(true);
        }
    }

}
