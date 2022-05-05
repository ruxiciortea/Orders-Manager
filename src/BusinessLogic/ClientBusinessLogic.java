package BusinessLogic;

import DataAccess.ClientDataAccess;
import Model.Client;

import java.util.ArrayList;

/**
 * Class used to communicate  with the ClientDataAccess class in order to perform the CRUD operations
 */
public class ClientBusinessLogic {

    private ClientDataAccess clientDataAccess;

    public ClientBusinessLogic() {
        clientDataAccess = new ClientDataAccess();
    }

    // Public Methods
    public ArrayList<Client> getAllClients() {
        ArrayList<Client> allClients = clientDataAccess.getAllClients();
        return allClients;
    }

    public Client findClientByID(int clientID) {
        Client client = clientDataAccess.findById(clientID);
        return client;
    }

    public Client insertClient(Client client) {
        client.setID(clientDataAccess.insert(client));
        return client;
    }

    public boolean deleteClient(int clientID) {
        boolean deleteStatus = clientDataAccess.delete(clientID);
        return deleteStatus;
    }

    public boolean updateClient(int clientID, Client newClientData) {
        boolean updateStatus = clientDataAccess.update(clientID, newClientData);
        return updateStatus;
    }

}
