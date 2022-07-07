package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Product;
import model.Part;

import java.io.IOException;

/**
 * @author Christopher Miller - Inventory Management System _ WGU C482
 */

/**
 * Main class.
 *
 * FUTURE_ENHANCEMENT- Create a database to store data when the program closes.
 *
 * FUTURE_ENHANCEMENT - Do not allow parts to be deleted if they are associated parts of a product.
 *
 * FUTURE_ENHANCEMENT - Get rid of redundant code in the Add/Modify Part and Add/Modify Product controllers.
 *
 * RUNTIME_ERROR - Null-Pointer Errors on Add Product and Modify Product form have been fixed
 *                 by renaming ObservableList from associatedParts to associatedPartsList
 *                 and by adding selectedPart instead of associatedPart.
 *
 */
public class main extends Application {

    /**
      * @param stage Starts the program.
      * @throws IOException Dismisses any IO Exception and opens the screen.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Inventory inventory = new Inventory();
        /** When main screen opens test data is added. */
        addTestData(inventory);
        /** Builds the main screen. */
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** Create test data method. */
    void addTestData(Inventory inventory) {

        /** Create new OutSourced part. */
        Part chain = new OutSourced(1, "chain", 15.00, 40,20,60, "Acme");
        Part leather_seat = new OutSourced(2, "leather seat", 40.00, 10,5,20, "Leather Works");
        Part black_seat = new OutSourced(3, "black seat", 30.00, 10,5,20, "Huffy");
        Part handle_bars = new OutSourced(4, "handle bars", 50.00, 20,5,40, "Huffy");
        Part white_tires = new OutSourced(5, "white tires", 75.00, 10,6,40, "Goodyear");
        Part black_tires = new OutSourced(6, "black tires", 75.00, 15,6,60, "Goodyear");

        /** Add new OutSourced parts to the inventory. */
        Inventory.addPart(chain);
        Inventory.addPart(leather_seat);
        Inventory.addPart(black_seat);
        Inventory.addPart(handle_bars);
        Inventory.addPart(white_tires);
        Inventory.addPart(black_tires);

        /** Create new InHouse parts. */
        Part red_bike_frame = new InHouse(7, "red bike frame", 90.00, 3,1,5, 1102);
        Part blue_bike_frame = new InHouse(8, "blue bike frame", 95.00, 4,1,4, 1103);
        Part black_bike_frame = new InHouse(9, "black bike frame", 150.00, 2,1,7, 1104);
        Part silver_bike_frame = new InHouse(10, "silver bike frame", 110.00, 1,1,10, 1105);
        Part white_bike_frame = new InHouse(11, "white bike frame", 75.00, 5,1,10, 1106);
        Part pink_bike_frame = new InHouse(12, "pink bike frame", 125.00, 2,1,5, 1107);

        /** Add new InHouse parts to the inventory. */
        Inventory.addPart(red_bike_frame);
        Inventory.addPart(blue_bike_frame);
        Inventory.addPart(black_bike_frame);
        Inventory.addPart(silver_bike_frame);
        Inventory.addPart(white_bike_frame);
        Inventory.addPart(pink_bike_frame);

        /** Create new products and add their associated parts. */
        Product red_bike = new Product(1, "red bike", 350.00, 12, 1, 15 );
        red_bike.addAssociatedPart(chain);
        red_bike.addAssociatedPart(black_seat);
        red_bike.addAssociatedPart(black_tires);
        red_bike.addAssociatedPart(handle_bars);
        red_bike.addAssociatedPart(red_bike_frame);

        Product blue_bike = new Product(2, "blue bike", 335.00, 2, 1, 10 );
        blue_bike.addAssociatedPart(leather_seat);
        blue_bike.addAssociatedPart(chain);
        blue_bike.addAssociatedPart(handle_bars);
        blue_bike.addAssociatedPart(white_tires);
        blue_bike.addAssociatedPart(blue_bike_frame);

        Product black_bike = new Product(3, "black bike", 400.00, 10, 1, 15 );
        black_bike.addAssociatedPart(black_bike_frame);
        black_bike.addAssociatedPart(black_seat);
        black_bike.addAssociatedPart(black_tires);
        black_bike.addAssociatedPart(chain);
        black_bike.addAssociatedPart(handle_bars);

        Product silver_bike = new Product(4, "silver bike", 375.00, 3, 1, 5 );
        silver_bike.addAssociatedPart(chain);
        silver_bike.addAssociatedPart(handle_bars);
        silver_bike.addAssociatedPart(silver_bike_frame);
        silver_bike.addAssociatedPart(black_seat);
        silver_bike.addAssociatedPart(black_tires);

        Product white_bike = new Product(5, "white bike", 340.00, 4, 1, 30 );
        white_bike.addAssociatedPart(chain);
        white_bike.addAssociatedPart(handle_bars);
        white_bike.addAssociatedPart(leather_seat);
        white_bike.addAssociatedPart(white_tires);
        white_bike.addAssociatedPart(white_bike_frame);

        Product pink_bike = new Product(6, "pink bike", 500.00, 7, 1, 30 );
        pink_bike.addAssociatedPart(chain);
        pink_bike.addAssociatedPart(leather_seat);
        pink_bike.addAssociatedPart(pink_bike_frame);
        pink_bike.addAssociatedPart(white_tires);
        pink_bike.addAssociatedPart(handle_bars);

        /** Add products with their associated parts to the inventory. */
        Inventory.addProduct(red_bike);
        Inventory.addProduct(blue_bike);
        Inventory.addProduct(black_bike);
        Inventory.addProduct(silver_bike);
        Inventory.addProduct(white_bike);
        Inventory.addProduct(pink_bike);
    }

    /**
     * @param args launches the main program window.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
