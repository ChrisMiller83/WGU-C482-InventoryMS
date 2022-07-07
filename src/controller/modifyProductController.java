package controller;

/**
 *
 * @author Christopher Miller - Inventory Management System for WGU C482.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Product;
import model.Part;
import static controller.mainController.getSelectedProduct;
import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;
import static model.Inventory.partLookup;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Modify product class opens the modify product form screen and loads FXML components including selected product's data.
 */
public class modifyProductController  implements Initializable{
    private int selectedProduct = getSelectedProduct();
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    int uniqueProductId;

    /**  FXML TextFields. */
    @FXML private TextField productIdTF;
    @FXML private TextField nameTF;
    @FXML private TextField invTF;
    @FXML private TextField priceTF;
    @FXML private TextField maxTF;
    @FXML private TextField minTF;
    @FXML private TextField searchAssociatedPartsTF;

    /** FXML Part Table with assigned columns. */
    @FXML private TableView<Part> allPartsView;
    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part,String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvCol;
    @FXML private TableColumn<Part, Double> partCostCol;

    /** FXML Product Table with assigned columns. */
    @FXML private TableView<Part> associatedPartsView;
    @FXML private TableColumn<Part, Integer> associatedPartIdCol;
    @FXML private TableColumn<Part, String> associatedPartNameCol;
    @FXML private TableColumn<Part, Integer> associatedPartInvCol;
    @FXML private TableColumn<Part, Double> associatedPartCostCol;

    /**
     * Initialize the controller class.  Data is auto-populated when screen opens.
     * @param url The url/location used to resolve relative paths fot the root object, or null if the url/location is not known.
     * @param resourceBundle Instantiates a resource bundle for the given bundle name of the given format and locale, using the given class loader if necessary.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /** When opened the previous selected product's data is populated in appropriate tables and text fields. */
        Product product = getAllProducts().get(selectedProduct);
        uniqueProductId = getAllProducts().get(selectedProduct).getProductID();
        productIdTF.setText(Integer.toString(product.getProductID()));
        nameTF.setText(product.getName());
        invTF.setText(Integer.toString(product.getStock()));
        priceTF.setText(Double.toString(product.getPrice()));
        minTF.setText(Integer.toString(product.getMin()));
        maxTF.setText(Integer.toString(product.getMax()));

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
    }

    /**
     * Cancel method.  When cancel button is clicked the screen changes to the main screen.
     * @param actionEvent Cancel button clicked, returns to the main screen.
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
     * Search associated parts method.
     * Searches for part by part id or part name.
     * @param actionEvent Searches for associated parts by name, id or partial name.
     */
    public void associatedPartsSearch(ActionEvent actionEvent) {
        String partialNameSearch;
        int idSearch;

        /**
         * Checks if the search text is an integer or string.
         * If search text is an integer it checks if any parts in the all parts list match.
         * If search text is a string it checks if any parts in the all parts list match or contain the same letters.
         */
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
     * Method used for search criteria.
     * Checks if the search criteria (input) is an integer or not.
     * @param input Input is the search criteria.
     * @return Return true if input is an integer, otherwise returns false.
     */
    private boolean isInteger(String input){
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Adds associated parts to associated parts table.
     * Checks if the selected part from parts table is selected, if not gives an error message.
     * Then checks if that part is already in the associated parts table, if it is an error message is shown.
     * @param actionEvent Adds selected associated parts to the add parts table.
     * @throws IOException Dismisses any IO Exception.
     */
    public void addAssociatedParts(ActionEvent actionEvent) throws IOException{
        Part selectedPart = allPartsView.getSelectionModel().getSelectedItem();

        boolean addedAlready = false;

        if (selectedPart == null) {
            errorMessage.errorWindow(14);
            return;
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
     * Deletes associated parts.
     * When remove associated parts button is clicked, it deletes the selected part from associated parts table.
     * If no part is selected an error message is shown.
     * If a part is selected a confirmation to delete window opens and confirms deletion, then the part is deleted.
     * @param actionEvent Deletes/removes associated parts from the associated parts table.
     * @throws IOException Dismisses any IO Exception.
     */
    public void removeAssociatedPartsBtn(ActionEvent actionEvent) throws IOException{
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
     * Save button method.
     * When save button is clicked it checks text fields for conditions.
     * If conditions are not meet, an error message displays for that condition.
     * If all conditions are meet, the product and associated parts are saved.
     * @param actionEvent Saves/updates product and associated parts.
     * @throws IOException Dismisses any IO Exception and returns to the main screen.
     */
    public void onSaveProduct(ActionEvent actionEvent) throws IOException{
        try {
            /** Assigns the text field inputs to appropriate variables. */
            int productId = Integer.parseInt(productIdTF.getText());
            String name = nameTF.getText();
            int inv = Integer.parseInt(invTF.getText());
            double price = Double.parseDouble(priceTF.getText());
            int min = Integer.parseInt(minTF.getText());
            int max = Integer.parseInt(maxTF.getText());

            /** Gets the totalPrice of all products added in the associated products table. */
            double totalPrice = 0;
            for (Part partPrice : associatedPartsList){
                totalPrice = totalPrice + partPrice.getPrice();
            }
            /** Checks to make sure the totalPrice is not more than the product price. */
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

            /** checks if inv value is not negative or zero. */
            if (inv < 1) {
                errorMessage.errorWindow(2);
                return;
            }

            /** checks if inv value falls in the range of min and max. */
            if (inv < min || inv > max) {
                errorMessage.errorWindow(3);
                return;
            }

            /** checks if min value is greater than max value or if max value is less than min value. */
            if (min > max || max < min) {
                errorMessage.errorWindow(4);
                return;
            }

            /** checks if price value is not negative or zero. */
            if (price <= 0) {
                errorMessage.errorWindow(6);
                return;
            }
            else {
                /** If all conditions above are satisfied the product is updated and saved along with its associated parts. */
                Product product = new Product(uniqueProductId, name, price, inv, min, max);
                for (Part associated : associatedPartsList){
                    product.addAssociatedPart(associated);
                }
                Inventory.saveProduct(productId, product);

            }
            /** Closes modify product screen and opens the main screen. */
            toMainScreen(actionEvent);
        }
        /** checks the value in text field that require numbers are actual numbers. */
        catch (NumberFormatException e){
            errorMessage.numberFormatException(e.getMessage());
        }
    }


}
