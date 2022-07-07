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
import model.Inventory;
import model.Part;
import model.InHouse;
import model.OutSourced;
import static controller.mainController.getSelectedPart;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static model.Inventory.*;

/**
 * Modify part class opens the modify part form screen and loads FXML components including selected part's data.
 */
public class modifyPartController implements Initializable {
    private final int selectedPart = getSelectedPart();
    int partID;

    @FXML public TextField nameTF;
    @FXML public TextField invTF;
    @FXML public TextField priceTF;
    @FXML public TextField maxTF;
    @FXML public TextField companyTF;
    @FXML public TextField minTF;
    @FXML public Button saveButton;
    @FXML public Button cancelButton;
    @FXML public Label companyLabel;
    @FXML public RadioButton outsourcedRadioBtn;
    @FXML public RadioButton inHouseRadioBtn;
    @FXML public TextField partIdTF;

    /** method used to change the label and prompt text when inHouse Radio button is selected. */
    @FXML
    private void inHouseSelected(ActionEvent actionEvent) {
        companyLabel.setText("Machine ID");
        companyTF.setPromptText("Machine Id Number");
    }

    /** method used to change the label and prompt text when outsourced Radio button is selected. */
    @FXML
    private void outsourcedSelected(ActionEvent actionEvent) {
        companyLabel.setText("Company Name");
        companyTF.setPromptText("Company Name");
    }

    /**
     *
     * @param actionEvent Cancel button clicked.  Takes user to main screen.
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
     *
     * @param input checks if user input is an integer or string.
     * @return Returns true if input is an integer otherwise returns false.
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
     * Method that gets all data on selected part and data in tables when the screen open.
     * @param url The url/location used to resolve relative paths fot the root object, or null if the url/location is not known.
     * @param resourceBundle Instantiates a resource bundle for the given bundle name of the given format and locale, using the given class loader if necessary.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Part modifyPart = getAllParts().get(selectedPart);
        partID = getAllParts().get(selectedPart).getPartID();
        partIdTF.setText(Integer.toString(modifyPart.getPartID()));
        nameTF.setText(modifyPart.getName());
        invTF.setText(Integer.toString(modifyPart.getStock()));
        priceTF.setText(Double.toString(modifyPart.getPrice()));
        minTF.setText(Integer.toString(modifyPart.getMin()));
        maxTF.setText(Integer.toString(modifyPart.getMax()));

        if (modifyPart instanceof InHouse) {
            inHouseRadioBtn.setSelected(true);
            companyLabel.setText("Machine ID");
            companyTF.setText(Integer.toString(((InHouse)modifyPart).getMachineID()));
        }
        if (modifyPart instanceof OutSourced) {
            outsourcedRadioBtn.setSelected(true);
            companyLabel.setText("Company Name");
            companyTF.setText(((OutSourced) modifyPart).getCompanyName());
        }
    }

    /**
     *
     * @param actionEvent Saves/updates selected part once all checked conditions are met.
     *                    Then redirects user to main screen.
     * @throws IOException Dismisses any IO Exception and returns to the main screen.
     */
    public void onSave(ActionEvent actionEvent) throws IOException{
        try {
            /** Gets text field inputs and assigns it to appropriate variables. */
            int partID = Integer.parseInt(partIdTF.getText());
            String name = nameTF.getText();
            double price = Double.parseDouble(priceTF.getText());
            int inv = Integer.parseInt(invTF.getText());
            int min = Integer.parseInt(minTF.getText());
            int max = Integer.parseInt(maxTF.getText());
            String machineID = companyTF.getText();


            /** checks if the any part text fields are empty. */
            if (nameTF.getText().isEmpty() || invTF.getText().isEmpty() || minTF.getText().isEmpty() || priceTF.getText().isEmpty() || maxTF.getText().isEmpty() ) {
                errorMessage.emptyField();
                return;
            }

            /** checks if part name starts with number, if it does display error message to start with letters. */
            if(isInteger(nameTF.getText())) {
                errorMessage.errorWindow(1);
                return;
            }

            /** Checks if the inv input is not negative or zero. */
            if (inv < 1) {
                errorMessage.errorWindow(2);
                return;
            }

            /** checks if the inv input is in the range of the min and max. */
            if (inv < min || inv > max) {
                errorMessage.errorWindow(3);
                return;
            }

            /** checks if min value is not greater than max or max is not less than min. */
            if (min > max || max < min) {
                errorMessage.errorWindow(4);
                return;
            }

            /** checks if price is not negative or zero. */
            if (price <= 0) {
                errorMessage.errorWindow(6);
                return;
            }

            /** checks if the inHouse radio button is selected
              * and if the machine id is an integer or string
              * displays error if machine id is a string.
             */
            if (inHouseRadioBtn.isSelected() && !isInteger(companyTF.getText())) {
                errorMessage.errorWindow(7);
                return;
            }

            /** checks if the outsourced radio button is selected
              * and if the company name is an integer or string
              * displays error if company name is an integer.
             */
            if (outsourcedRadioBtn.isSelected() && isInteger(companyTF.getText())) {
                errorMessage.errorWindow(5);
                return;
            }

            /** if all condition checks pass and inHouse part is selected, save/updates part. */
            else if (inHouseRadioBtn.isSelected()) {
                Inventory.savePart(partID, new InHouse(partID, name, price, inv, min, max, Integer.parseInt(machineID)));
            }

            /** if all condition checks pass and outsourced part is selected, save/updates part. */
            else if (outsourcedRadioBtn.isSelected()) {
                Inventory.savePart(partID, new OutSourced(partID, name, price, inv, min, max, machineID));
            }
            /** redirects user to main screen. */
            toMainScreen(actionEvent);
        }
        /** checks all number formatted fields for errors. */
        catch (NumberFormatException e){
            errorMessage.numberFormatException(e.getMessage());
        }

    }
}
