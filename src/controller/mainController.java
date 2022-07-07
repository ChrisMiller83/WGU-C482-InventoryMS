package controller;

/**
 *
 * @author Christopher Miller - Inventory Management System -WGU 482
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import static model.Inventory.partLookup;
import static model.Inventory.productLookup;
import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;
import static model.Inventory.deletePart;
import static model.Inventory.deleteProduct;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main Controller class
 * 1st scene when app is initiated
 */
public class mainController implements Initializable {
    private static Part partToModify;
    private static int selectedPart;
    private static Product productToModify;
    private static int selectedProduct;

    @FXML private TextField partSearchTxt;
    @FXML private TableView<Part> allPartsView;
    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvCol;
    @FXML private TableColumn<Part, Double> partCostCol;

    @FXML private TextField productSearchTxt;
    @FXML private TableView<Product> allProductsView;
    @FXML private TableColumn<Product, Integer> productIdCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Integer> productInvCol;
    @FXML private TableColumn<Product, Double> productCostCol;

    @FXML private Button addPartButton;
    @FXML private Button modifyPartButton;
    @FXML private Button deletePartButton;

    @FXML private Button addProductButton;
    @FXML private Button modifyProductButton;
    @FXML private Button deleteProductButton;

    /**
     * Auto-populates tables appropriate data when screen opens.
     * @param url The url/location used to resolve relative paths fot the root object, or null if the url/location is not known.
     * @param resourceBundle Instantiates a resource bundle for the given bundle name of the given format and locale, using the given class loader if necessary.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allPartsView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        allProductsView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** -------------------- Parts Section --------------------- */

    /**
     *
     * @param actionEvent Part Search TextField "Enter/Return" button pressed.
     *  Searches part inventory for matching part id's or matching part name/partial name.
     */
    public void getPartSearchResults(ActionEvent actionEvent) {
        String partialNameSearch;
        int idSearch;

        if (isInteger(partSearchTxt.getText())) {
            idSearch = Integer.parseInt(partSearchTxt.getText());
            partLookup(idSearch);
            partSearchTxt.setPromptText("Search by Part Name or Id");
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
            partialNameSearch = partSearchTxt.getText();
            partLookup(partialNameSearch);
            partSearchTxt.setPromptText("Search by Part Name or Id");
            if (partLookup(partialNameSearch) == null || partLookup(partialNameSearch).isEmpty()) {
                allPartsView.setItems(getAllParts());
                errorMessage.notFound();
                return;
            }
            allPartsView.setItems(partLookup(partialNameSearch));
        }
    }

    /**
     * @param actionEvent Add part button clicked.
     *                    Redirects user to add part screen.
     * @throws IOException Dismisses any IO Exception and opens the screen.
     */
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param actionEvent Modify part button clicked.
     *                    Redirects user to modify part screen with selected parts data.
     * @throws IOException Dismisses any IO Exception and opens the screen.
     */
    public void toModifyPart(ActionEvent actionEvent) throws IOException {
        partToModify = allPartsView.getSelectionModel().getSelectedItem();

        if (partToModify == null) {
            errorMessage.errorWindow(8);
            return;
        }
        selectedPart = getAllParts().indexOf(partToModify);
        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyPart.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param actionEvent Delete part button clicked.  Deletes selected part from parts inventory.
     */
    public void onDeletePart(ActionEvent actionEvent) {
        Part partToDelete = allPartsView.getSelectionModel().getSelectedItem();
        if (partToDelete == null) {
            errorMessage.errorWindow(10);
            return;
        } else {
            boolean deleteConfirm = errorMessage.deleteConfirmation(partToDelete.getName());
            if (deleteConfirm) {
                deletePart(partToDelete);
            }
        }
    }

    /**  --------------- Products Section ----------------------- */

    /**
     * @param actionEvent Product Search TextField "Enter/Return" button pressed.
     *                   Searches product inventory for matching product id's or matching product name/partial name.
     */
    public void getProductSearchResults(ActionEvent actionEvent) {
        String partialNameSearch;
        int idSearch;

        if (isInteger(productSearchTxt.getText())) {
            idSearch = Integer.parseInt(productSearchTxt.getText());
            productLookup(idSearch);
            productSearchTxt.setPromptText("Search by Product Name or Id");
            if (productLookup(idSearch) == null) {
                allProductsView.setItems(getAllProducts()); // repopulate product table if search is empty
                errorMessage.notFound();  // send error message if search is empty
                return;
            }
            ObservableList<Product> foundProducts = FXCollections.observableArrayList();
            foundProducts.add(productLookup(idSearch));
            allProductsView.setItems(foundProducts);
        }
        else {
            partialNameSearch = productSearchTxt.getText();
            productLookup(partialNameSearch);
            productSearchTxt.setPromptText("Search by Product Name or Id");
            if (productLookup(partialNameSearch) == null || productLookup(partialNameSearch).isEmpty()) {
                allProductsView.setItems(getAllProducts());
                errorMessage.notFound();
                return;
            }
            allProductsView.setItems(productLookup(partialNameSearch));
        }
    }

    /**
     *
     * @param actionEvent Add Product button clicked.  Directs user to add product screen.
     * @throws IOException Dismisses any IO Exception and opens the screen.
     */
    public void toAddProduct(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param actionEvent Modify Product button clicked. Directs user to modify product screen, if a product to modify is selected.
     * @throws IOException Dismisses any IO Exception and opens the screen.
     */
    public void toModifyProduct(ActionEvent actionEvent) throws IOException{
        productToModify = allProductsView.getSelectionModel().getSelectedItem();

        if (productToModify == null) {
            errorMessage.errorWindow(9);
            return;
        }
        selectedProduct = getAllProducts().indexOf(productToModify);
        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyProduct.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param actionEvent Delete product button clicked.
     *                    Deletes selected product if no associated parts are in the product.
     */
    public void onDeleteProduct(ActionEvent actionEvent) {
        Product productToDelete = allProductsView.getSelectionModel().getSelectedItem();
        if (productToDelete ==null) {
            errorMessage.errorWindow(11);
            return;
        }
        int i = productToDelete.getAllAssociatedParts().size();
        if (productToDelete != null || i == 0) {
            if (i > 0) {
                errorMessage.errorWindow(12);
                return;
            }
            boolean deleteConfirm = errorMessage.deleteConfirmation(productToDelete.getName());
            if (deleteConfirm) {
                deleteProduct(productToDelete);
            }
        }


    }

    /**  -------------------- Additional Methods ------------------ */


    /** Checks if search id is a string or integer.
     * @param id user input
     * @return Return true if user input is an integer, otherwise returns false.
     */
    private boolean isInteger(String id){
        try {
            Integer.parseInt(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /** Passes the selected part from main screen to modify part screen along with its data.
     * @return selected part.
     */
    public static int getSelectedPart() {
        return selectedPart;
    }


    /** Passes the selected product from main screen to modify product screen along with its data and associated parts.
     * @return selectedProduct
     */
    public static int getSelectedProduct() {
        return selectedProduct;
    }

    /** --------------- Close Program with Exit Button ------------------- */

    /**
     * @param actionEvent Exit button click.  Closes the program.
     * @throws IOException Dismisses any IO Exceptions and closes the program.
     */
    public void exitProgram(ActionEvent actionEvent) throws IOException{
        System.exit(0);
    }
}
