package controller;

/**
 * @author Christopher Miller - Inventory Management System - WGU C482
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static model.Inventory.*;

/**
 * Add product class opens the add product form screen and loads FXML components.
 */
public class addProductController implements Initializable {
    @FXML private TextField searchAssociatedPartsTF;

    @FXML private TableView<Part> allPartsView;
    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvCol;
    @FXML private TableColumn<Part, Double> partCostCol;

    @FXML private Button addAssociatedPartsBtn;

    @FXML private TableView<Part> associatedPartsView;
    @FXML private TableColumn<Part, Integer> associatedPartIdCol;
    @FXML private TableColumn<Part, String> associatedPartNameCol;
    @FXML private TableColumn<Part, Integer> associatedPartInvCol;
    @FXML private TableColumn<Part, Double> associatedPartCostCol;

    @FXML private Button removeAssociatedPartsBtn;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private TextField productIdTF;
    @FXML private TextField nameTF;
    @FXML private TextField invTF;
    @FXML private TextField priceTF;
    @FXML private TextField maxTF;
    @FXML private TextField minTF;

    Product product = new Product();

    ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    int uniqueProductId;
    Product associatedPart;
    Part selectedPart;

    /**
     * Initialize method to auto-populate data used in the tables when the screen is loaded.
     * @param url The url/location used to resolve relative paths fot the root object, or null if the url/location is not known.
     * @param resourceBundle Instantiates a resource bundle for the given bundle name of the given format and locale, using the given class loader if necessary.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allPartsView.setItems(getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsList = product.getAllAssociatedParts();
        associatedPartsView.setItems(associatedPartsList);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        uniqueProductId = Inventory.getUniqueProductId.getAndIncrement();
        productIdTF.setText("" + uniqueProductId);
    }

    /**
     * Cancel button method that takes user to main screen when cancel button is clicked.
     * @param actionEvent takes user to main screen when cancel button is clicked.
     * @throws IOException Dismisses any IO Exception and returns to the main screen.
     */
    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Searches the inventory of parts for the part id or part name or letters included in the part.
     * @param actionEvent searches parts list for part id or part name or letters included in the part.
     */
    public void associatedPartsSearch(ActionEvent actionEvent) {
        String partialNameSearch;
        int idSearch;

        /** checks if search is a number and if that number is in the part list as a part id. */
        if (isInteger(searchAssociatedPartsTF.getText())) {
            idSearch = Integer.parseInt(searchAssociatedPartsTF.getText());
            partLookup(idSearch);
            searchAssociatedPartsTF.setPromptText("Search by Part Name or Id");
            if (partLookup(idSearch) == null) {
                allPartsView.setItems(getAllParts()); // repopulate parts table search is empty
                errorMessage.notFound();
                return;
            }
            ObservableList<Part> foundParts = FXCollections.observableArrayList();
            foundParts.add(partLookup(idSearch));
            allPartsView.setItems(foundParts);
        }
        /** checks if search is a partial or full part name and if it is in the list. */
        else {
            partialNameSearch = searchAssociatedPartsTF.getText();
            partLookup(partialNameSearch);
            searchAssociatedPartsTF.setPromptText("Search by Part Name or Id");
            if (partLookup(partialNameSearch) == null || partLookup(partialNameSearch).isEmpty()) {
                allPartsView.setItems(getAllParts());
                errorMessage.notFound();
                return;
            }
            allPartsView.setItems(partLookup(partialNameSearch));
        }

    }

    /**
     * Add parts to the associated parts table method.
     * @param actionEvent Add parts to associated parts table.
     */
    public void addAssociatedParts(ActionEvent actionEvent) {
        /** selectedPart is the user selected part to add to the associated parts table. */
        Part selectedPart = allPartsView.getSelectionModel().getSelectedItem();

        /**
         *  checks if a part was selected, if no part selected, displays an error message.
         *  checks if the part is already in the associated parts table, displays error message if already in the table.
         *  if part is not already in the table, the associated part is added to the associated parts table.
         *  */

        boolean addedAlready = false;
        if (selectedPart == null) {
            errorMessage.errorWindow(14);
        }
        else {
            int checkId = selectedPart.getPartID();
            for (Part associatedPart : associatedPartsList) {
                if (associatedPart.getPartID() == checkId) {
                    errorMessage.errorWindow(13);
                    addedAlready = true;
                }
            }
            if (!addedAlready) {
                associatedPartsList.add(selectedPart);
                associatedPartsView.setItems(associatedPartsList);
            }
        }
    }

    /**
     * Remove associated parts method.
     * @param actionEvent Removes selected associated parts from the associated parts table.
     */
    public void removeAssociatedPartsBtn(ActionEvent actionEvent) {
        Part selectedPart = associatedPartsView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            errorMessage.errorWindow(10);
        }
        if (selectedPart != null) {
            boolean remove = errorMessage.deleteConfirmation(selectedPart.getName());
            if (remove) {
                associatedPartsList.remove(selectedPart);
            }
        }
    }

    /**
     * Save product and associated parts method.
     * Checks text fields conditions and gives error messages if text field condition is not met.
     * Creates and saves product and associated parts if conditions are met.
     * Redirects user back to main screen once product is saved.
     * @param actionEvent Saves product and associated parts.
     * @throws IOException Dismisses any IO Exception and returns to the main screen.
     */
    public void onSaveProduct(ActionEvent actionEvent) throws IOException {
        try {
            /** Assigns text field inputs to appropriate variables. */
            int productID = Integer.parseInt(productIdTF.getText());
            String name = nameTF.getText();
            int inv = Integer.parseInt(invTF.getText());
            double price = Double.parseDouble(priceTF.getText());
            int min = Integer.parseInt(minTF.getText());
            int max = Integer.parseInt(maxTF.getText());

            /** checks if product name is already used by another product. */
            boolean addedAlready = false;
            String checkName = nameTF.getText();
            for (Product productName : getAllProducts()) {
                if (productName.getName().equalsIgnoreCase(checkName)){
                    errorMessage.productNameUsed(checkName);
                    addedAlready = true;
                    return;
                }
            }

            /** creates a variable totalPrice to check against the product price. */
            double totalPrice = 0;
            for (Part partPrice : associatedPartsList){
                totalPrice = totalPrice + partPrice.getPrice();
            }

            /** checks that the price is more than the total of all add associated parts prices. */
            if (price < totalPrice) {
                errorMessage.partPrice(totalPrice, price);
                return;
            }

            /** checks if the any product text fields are empty. */
            if (nameTF.getText().isEmpty() || invTF.getText().isEmpty() || minTF.getText().isEmpty() || priceTF.getText().isEmpty() || maxTF.getText().isEmpty() ) {
                errorMessage.emptyField();
                return;
            }

            /** checks if name starts with number, if it does display error message to start with letters. */
            if(isInteger(nameTF.getText())) {
                errorMessage.errorWindow(1);
                return;
            }

            /** checks if the inv field is greater than one and not zero or negative. */
            if (inv < 1) {
                errorMessage.errorWindow(2);
                return;
            }

            /** checks if inv field is not less than min field and not greater than max field. */
            if (inv < min || inv > max) {
                errorMessage.errorWindow(3);
                return;
            }

            /** checks that min field is not greater than max field
             *  and max field is not less than min field. */
            if (min > max || max < min) {
                errorMessage.errorWindow(4);
                return;
            }

            /** checks price field is not zero or a negative number. */
            if (price <= 0) {
                errorMessage.errorWindow(6);
                return;
            }
            else {
                /** Saves/adds product and its associated parts if all conditional checks are satisfied. */
                Product product = new Product(uniqueProductId, name, price, inv, min, max);
                for (Part associated : associatedPartsList){
                    product.addAssociatedPart(associated);
                }
                addProduct(product);

            }
            /** Once product is added and saved, directs user to main screen. */
            toMainScreen(actionEvent);
        }
        /** checks the value in text field that require numbers are actual numbers. */
        catch (NumberFormatException e){
            errorMessage.numberFormatException(e.getMessage());
        }
    }

    /** method used to check if input is a number or string. */
    private boolean isInteger(String input){
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
