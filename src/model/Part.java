package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Supplied class Part.java
 * @author Christopher Miller - Inventory Management System - WGU C482
 */

public abstract class Part {
    private int partID;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Empty default constructor.
     */
    public Part(){

    }

    /**
     *
     * @param partID part id
     * @param name part name
     * @param price part price
     * @param stock inventory in stock
     * @param min minimum inventory
     * @param max max inventory
     */
    public Part(int partID, String name, double price, int stock, int min, int max) {
        this.partID = partID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getPartID() {
        return partID;
    }

    /**
     * @param partID the partID to set
     */
    public void setPartID(int partID) {
        this.partID = partID;
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
     * @param stock the stock to set
     */
    public void setStock(int stock) {
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



}
