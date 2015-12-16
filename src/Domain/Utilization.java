package Domain;

public class Utilization {
    private String sportName;
    private String resourceID;
    private float utilization;

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
     * @return the resourceID
     */
    public String getResourceID() {
        return resourceID;
    }

    /**
     * @param resourceID the resourceID to set
     */
    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    /**
     * @return the utilization
     */
    public float getUtilization() {
        return utilization;
    }

    /**
     * @param utilization the utilization to set
     */
    public void setUtilization(float utilization) {
        this.utilization = utilization;
    }
    
}
