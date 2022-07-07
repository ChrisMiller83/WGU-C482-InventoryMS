package controller;

/**
 * @author Christopher Miller - Inventory Management System - WGU C482
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Product;
import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;

/**
 * Add part class opens the add part screen and loads FXML components.
 */
public class addPartController implements Initializable {
    int uniquePartId;

    @FXML
    private RadioButton outsourcedRdbtn;
    @FXML
    private RadioButton inHouseRdbtn;
    @FXML
    private Label companyLabel;
    @FXML
    private TextField companyTF;
    @FXML
    private TextField priceTF;
    @FXML
    private TextField invTF;
    @FXML
    private TextField maxTF;
    @FXML
    private TextField minTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField idTF;

    /**
     * Cancel button clicked method.
     * @param actionEvent Cancel button is clicked and takes user back to the main screen.
     * @throws IOException Dismisses any IO Exception and returns to the main screen.
     */
    @FXML
    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * inHouse Radio button selected method.
     * @param actionEvent inHouse Radio button selected.  Changes the label text from Company Name to Machine Id and prompt text of Machine Id.
     */
    @FXML
    private void inHouseSelected(ActionEvent actionEvent) {
        companyLabel.setText("Machine ID");
        companyTF.setPromptText("Machine Id Number");
    }

    /**
     * outsourced Radio button selected method.
     * @param actionEvent outsourced radio button selected. Changes the label text from Machine Id to Company Name and text prompt to Company Name.
     */
    @FXML
    private void outsourcedSelected(ActionEvent actionEvent) {
        companyLabel.setText("Company Name");
        companyTF.setPromptText("Company Name");
    }

    /**
     * Save Button clicked method.
     * When the Save button is clicked the method takes data input from the text fields
     * and saves to appropriate variables.
     * The variables then go through a check process to check if various conditions are met.
     * If all check conditions are met the part is saved as an inHouse part or an outsourced part,
     * depending on which radio button is selected.
     * Once part is saved, the user is brought back to the main screen.
     * @param actionEvent Save Button clicked, saves part and returns to main screen.
     * @throws IOException Dismisses any IO Exception.
     */
    @FXML
    public void onSavePart(ActionEvent actionEvent) throws IOException {
        try {
            /** uniquePartId auto-generates a new part id each time a new part is created
             *  and increments the next part by one.
             */
            uniquePartId = Inventory.getUniquePartId.getAndIncrement();
            String name = nameTF.getText();
            double price = Double.parseDouble(priceTF.getText());
            int inv = Integer.parseInt(invTF.getText());
            int min = Integer.parseInt(minTF.getText());
            int max = Integer.parseInt(maxTF.getText());
            String machineID = companyTF.getText();

            /** checks to see if the part name is already used in the parts list,
              * displays error message if used already,
              * if not used return and begins next check in the save part method
              */
            boolean addedAlready = false;
            String checkName = nameTF.getText();
            for (Part partName : getAllParts()) {
                if (partName.getName().equalsIgnoreCase(checkName)){
                    errorMessage.partNameUsed(checkName);
                    addedAlready = true;
                    return;
                }
            }

            /** checks if the any part text fields are empty. */
            if (nameTF.getText().isEmpty() || invTF.getText().isEmpty() || minTF.getText().isEmpty() || priceTF.getText().isEmpty() || maxTF.getText().isEmpty() ) {
                errorMessage.emptyField();
                return;
            }

            /** checks if name starts with number, if it does display error message to start with letters. */
            if(isInteger(nameTF.getText())) {
                errorMessage.errorWindow(1);
                return;
            }

            /** checks the number entered into the inventory field if it is less than one,
             * displays error message if less than one. */
            if (inv < 1) {
                errorMessage.errorWindow(2);
                return;
            }

            /** checks if inventory is less than min value or inventory is more than max value.
             *  displays error message if either condition is true.*/
            if (inv < min || inv > max) {
                errorMessage.errorWindow(3);
                return;
            }

            /** checks if min value greater than max value or max value is less than min value.
             *  displays error message if either condition is true. */
            if (min > max || max < min) {
                errorMessage.errorWindow(4);
                return;
            }

            /** checks if price value is less than zero, displays error message if value is negative or zero. */
            if (price <= 0) {
                errorMessage.errorWindow(6);
                return;
            }

            /**
             *  if the inHouse radio button is selected
             *  and machine id is not a number displays error message. */
            if (inHouseRdbtn.isSelected() && !isInteger(companyTF.getText())) {
                errorMessage.errorWindow(7);
                return;
            }

            /**
             * if the outsourced radio button is selected
             * and the company name is only numbers no letters, error message displayed. */
            if (outsourcedRdbtn.isSelected() && isInteger(companyTF.getText())) {
                errorMessage.errorWindow(5);
                return;
            }

            /** if all conditions above are met and inHouse radio button is selected
             *  a new inHouse part is created and saved. */
            else if (inHouseRdbtn.isSelected()) {
                Inventory.addPart(new InHouse (uniquePartId, name, price, inv, min, max, Integer.parseInt(machineID)));
            }

            /** if all conditions above are met and outsourced radio button is selected
             *  a new outsourced part is created and saved. */
            else if (outsourcedRdbtn.isSelected()) {
                Inventory.addPart(new OutSourced(uniquePartId, name, price, inv, min, max, machineID));

            }
            /** takes user back to main screen. */
            toMainScreen(actionEvent);

        } catch (NumberFormatException e){
            errorMessage.numberFormatException(e.getMessage());

        }
    }

    /** method used in the save method to determine if user input is an integer or string. */
    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** method used to generate data when screen opens.  Not used on add part form. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
