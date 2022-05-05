package DataAccess;

import Model.Client;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ClientDataAccess extends AbstractDataAccess<Client> {

    public ArrayList<Client> getAllClients() {
        return super.findAll();
    }

    public Client findById(int clientID) {
        return super.findById(clientID);
    }

    public int insert(Client client) {
        return super.insert(client);
    }

    public boolean update(int clientID, Client client) {
        return super.update(clientID, client);
    }

    public boolean delete(int clientID) {
        return super.delete(clientID);
    }

}
