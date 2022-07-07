package controller;

/**
 * @author Christopher Miller - Inventory Management System - WGU C482
 */

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.Optional;

/**
 * Error Message Class.  Displays different error/confirmation messages depending on the error.
 */
public class errorMessage {

    /** Error message for empty fields. */
    public static void emptyField() {
        Alert alert = new Alert((Alert.AlertType.ERROR));
        alert.setTitle("Error Check Fields");
        alert.setContentText("Error: One or more fields may be empty.");
        alert.showAndWait();
    }

    /** Error message for number formatted errors. */
    public static void numberFormatException(String input) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Number Format Error");
        alert.setContentText("ERROR:  " + input + ": requires a number.");
        alert.showAndWait();
    }

    /** Verifies product/part to delete. */
    public static boolean deleteConfirmation(String itemToDelete) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("DELETE CONFORMATION");
        alert.setHeaderText("Delete: " + itemToDelete);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /** Error message product name already used. */
    public static boolean productNameUsed(String name) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR NAME CURRENTLY IN USE");
        alert.setHeaderText("Error: " + name + "\nThis product name is already used. \nPlease edit product name.");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /** Error message part name already used. */
    public static boolean partNameUsed(String name) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR NAME CURRENTLY IN USE");
        alert.setHeaderText("Error: " + name + "\nThis part name is already used. \nPlease edit part name.");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /** Error message when associated parts total is greater than the product price. */
    public static boolean partPrice(double totalPrice, double productCost) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR: PARTS TOTAL EXCEED PRODUCT PRICE");
        alert.setHeaderText("ERROR: PARTS TOTAL EXCEED PRODUCT PRICE " +
                "\nTotal: $" + totalPrice +
                "\nProduct price: $" + productCost +
                "\nPlease increase the product price or remove parts from the product.");;
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /** Not found message for search results unable to find search parameters. */
    public static void notFound() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Results");
        alert.setHeaderText("Unable to find a match");
        alert.showAndWait();
    }

    /** Error messages for part/product variable criteria conditions. */
    public static void errorWindow(int number) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR ALERT!");
        switch (number) {
            case 1:
            {
                alert.setContentText("Invalid format: Name must start with letters.");
                break;
            }
            case 2: {
                alert.setContentText("Inventory can not be less than 1 or a negative number");
                break;
            }
            case 3:
            {
                alert.setContentText("Inv can not be less than Min \n Inv can not be greater Max");
                break;
            }
            case 4: {
                alert.setContentText("Min can not be greater than Max \n Max can not be less than Min");
                break;
            }
            case 5: {
                alert.setContentText("Company name must include letters.");
                break;
            }
            case 6: {
                alert.setContentText("Price can not be a negative number");
                break;
            }
            case 7: {
                alert.setContentText("Invalid Format:  Machine ID can only contain numbers!");
                break;
            }
            case 8: {
                alert.setContentText("Please select a part to modify first.");
                break;
            }
            case 9: {
                alert.setContentText("Please select a product to modify first.");
                break;
            }
            case 10: {
                alert.setContentText("Please select a part to delete first.");
                break;
            }
            case 11: {
                alert.setContentText("Please select a product to delete first.");
                break;
            }
            case 12: {
                alert.setContentText("Please remove all associated parts in order to delete product.");
                break;
            }
            case 13: {
                alert.setContentText("This part is already associated to the product.");
                break;
            }
            case 14: {
                alert.setContentText("Please select a part to add first.");
                break;
            }
            default: {
                alert.setContentText("ERROR UNKNOWN");
                break;
            }
        }
        alert.showAndWait();
    }
}
