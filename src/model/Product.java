package model;

/**
 *
 * @author Christopher Miller - Inventory Management System - WGU C482
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class
 */
public class Product  {
    /**
     * associatedParts is the list that holds the product parts
     */
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    /**
     * Product fields
     */
    private int productID;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor used to create a new product object
     * @param productID the product id.
     * @param name the product name.
     * @param price products price.
     * @param stock inventory in stock.
     * @param min minimum inventory.
     * @param max max inventory.
     */
    public Product(int productID, String name, double price, int stock, int min, int max) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Empty Default constructor
     */
    public Product(){
    }

    /**
     * @param productID the id to set
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     * @return the productID
     */
    public int getProductID() {
        return productID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param inStock the stock to set
     */
    public void setStock(int inStock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part Add parts to associated parts list.
     */
    public void addAssociatedPart (Part part){
        associatedPartsList.add(part);
    }

    /**
     *
     * @param selectedAssociatedPart part selected for deletion/removal.
     */
    public void deleteAssociatedPart (Part selectedAssociatedPart) {
        associatedPartsList.remove(selectedAssociatedPart);

    }

    /**
     * Gets all parts in associated parts list
     * @return associatedParts list
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedPartsList;
    }
}
