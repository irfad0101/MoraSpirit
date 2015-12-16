package Domain;

import java.sql.Date;

public class Equipment {
    private String itemNo;
    private String type;
    private Date purchaseDate;
    private boolean availability;
    private float purchasePrice;
    private String condition;
    private String sportName;

    /**
     * @return the itemNo
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * @param itemNo the itemNo to set
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the purchaseDate
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * @param purchaseDate the purchaseDate to set
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
   
    /**
     * @return the purchaseType
     */
    public float getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * @param purchaseType the purchaseType to set
     */
    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @return the sportName
     */
    public String getSportName() {
        return sportName;
    }

    /**
     * @param sportName the sportName to set
     */
    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    /**
     * @return the availability
     */
    public boolean isAvailable() {
        return availability;
    }

    /**
     * @param availability the availability to set
     */
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
    
}
