package model;

/**
 *
 * @author Christopher Miller
 */

/**
 * InHouse class that extends Part class
 */
public class InHouse extends Part {
    /**
     * InHouse used machineID as an additional field
     * variable for machineID field
     */
    private int machineID;

    /**
     * Constructor used to create an InHouse Part
     * @param partId part id
     * @param name part name
     * @param price part price
     * @param stock inventory in stock
     * @param min minimum inventory
     * @param max max inventory
     * @param machineID machine id
     */
    public InHouse(int partId, String name, double price, int stock, int min, int max, int machineID) {
        super(partId, name, price, stock, min, max);
        setMachineID(machineID);
    }

    /**
     * Sets the machineID
     * @param machineID sets the machine id
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
     * Returns the machineID
     * @return machineID
     */
    public int getMachineID() {
        return machineID;
    }
}
