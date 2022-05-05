package Model;

public class Client {

    private int ID;
    private String name;
    private int birthYear;
    private String phoneNumber;
    private String email;

    public Client() {
    }

    public Client(String name, int birthYear, String phoneNumber, String email) {
        this.name = name;
        this.birthYear = birthYear;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Client(int ID, String name, int birthYear, String phoneNumber, String email) {
        this.ID = ID;
        this.name = name;
        this.birthYear = birthYear;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and Setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
