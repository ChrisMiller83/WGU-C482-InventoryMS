package model;
/**
 *
 *
 * @author Christopher Miller
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Inventory class
 */

public class Inventory {
    /**
     * create allParts and allProducts list
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * Default inventory constructor
     */
    public Inventory() {
    }

    /**
     *  --------------------- PART METHODS ---------------------
     *
     */

    /**
     * Add new parts to allParts list
     * @param newPart creates a new part
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Creates a new partID starting at 13 and increments on each new part added
     */
    public static AtomicInteger getUniquePartId = new AtomicInteger(13);

    /**
     * Returns all the parts from the allParts list.
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Search allParts list for parts by ID
     * @param ID user input
     * @return part if found
     */
    public static Part partLookup(int ID){
        for (Part part : allParts) {
            if (part.getPartID() == ID) {
                return part;
            }
        }
        return null;
    }

    /**
     * Searches allParts list by name or letters
     * @param partialName user input
     * @return foundParts if found
     */
    public static ObservableList<Part> partLookup(String partialName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for (Part part: getAllParts()) {
            if (part.getName().toLowerCase().contains(partialName.toLowerCase())) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    /**
     * Save part/updates modified part
     * Allows iteration through allParts list to find and update selected part
     * If the part to update is not in the list it is added to the allParts list
     * @param partID part id
     * @param newPart creates new part if part id is not found, otherwise updates part matching id.
     */
    public static void savePart(int partID, Part newPart) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getPartID() == partID) {
                allParts.set(i, newPart);
            }
        }
    }

    /**
     * Deletes the selected part
     * @param selectedPart part selected by user
     * @return deleted part
     */
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }


    /**
     *
     * ---------------- PRODUCT METHODS -----------------------
     *
     **/

    /**
     * Adds a new product to the allProducts list.
     * @param newProduct creates a new product
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }


    /**
     *  Creates a unique product ID when creating new products
     */
    public static AtomicInteger getUniqueProductId = new AtomicInteger(7);

    /**
     * Returns all products in the allProducts list
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Search allProducts list for product by ID
     * @param ID user input
     * @return product if found
     */
    public static Product productLookup(int ID) {
        for (Product product: allProducts) {
            if (product.getProductID() == ID) {
                return product;
            }
        }
        return null;
    }

    /**
     * Search allProducts list for product by name or letters
     * @param partialName user input
     * @return product if found
     */
    public static ObservableList<Product> productLookup (String partialName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        for (Product product: getAllProducts()) {
            if (product.getName().toLowerCase().contains(partialName.toLowerCase())) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    /**
     * Save product/updates modified product
     * Allows iteration through allProducts list to find and update selected product
     * If the product to update is not in the list it is added to the allProducts list
     * @param productId product id
     * @param newProduct creates new product if no product exist matching the product id
     *                   otherwise updates existing product.
     */
    public static void saveProduct(int productId, Product newProduct) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductID() == productId) {
                allProducts.set(i, newProduct);
            }
        }
    }

    /**
     * Deletes selected product
     * @param selectedProduct user selected part
     * @return deleted product
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

}
