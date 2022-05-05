package BusinessLogic;

/**
 * Contains some random functions used for different checks/utilities
 */
public class Utility {

    public static int getNumberFrom(String stringNumber) {
        if (stringNumber.isEmpty()) {
            return -1;
        }

        int number = -1;

        try{
            number = Integer.parseInt(stringNumber);
        }
        catch (NumberFormatException ex){
            return -1;
        }

        return number;
    }

}
