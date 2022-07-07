package model;

/**
 *
 * @author Christopher Miller
 */

/**
 * OutSourced class that extends Part class
 */
public class OutSourced extends Part{
    /**
     * OutSourced uses companyName instead of machineID
     * variable for the companyName field
     */
    private String companyName;

    /**
     * Constructor to create a new outsourced part
     * @param partId part id
     * @param name part name
     * @param price part price
     * @param stock inventory in stock
     * @param min minimum inventory
     * @param max max inventory
     * @param companyName company name
     */
    public OutSourced(int partId, String name, double price, int stock, int min, int max, String companyName) {
        super(partId, name, price, stock, min, max);
        setCompanyName(companyName);
    }

    /**
     * Sets the companyName
     * @param companyName sets companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Returns the companyName when called
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

}
