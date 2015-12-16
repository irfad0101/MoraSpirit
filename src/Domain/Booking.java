package Domain;

import java.sql.Date;
import java.sql.Time;

public class Booking {
    private String resourceID;
    private String requesterName;
    private int requesterContactNo;
    private Date date;
    private Time startTime;
    private Time endTime;

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
     * @return the requesterName
     */
    public String getRequesterName() {
        return requesterName;
    }

    /**
     * @param requesterName the requesterName to set
     */
    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    /**
     * @return the requesterContactNo
     */
    public int getRequesterContactNo() {
        return requesterContactNo;
    }

    /**
     * @param requesterContactNo the requesterContactNo to set
     */
    public void setRequesterContactNo(int requesterContactNo) {
        this.requesterContactNo = requesterContactNo;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the startTime
     */
    public Time getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Time getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    
}
