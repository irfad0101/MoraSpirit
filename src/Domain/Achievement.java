package Domain;

import java.sql.Date;

public class Achievement {
    private String achievementID;
    private String contest;
    private Date date;
    private String place;
    private String sportName;
    private String description;

    /**
     * @return the achievementID
     */
    public String getAchievementID() {
        return achievementID;
    }

    /**
     * @param achievementID the achievementID to set
     */
    public void setAchievementID(String achievementID) {
        this.achievementID = achievementID;
    }

    /**
     * @return the contest
     */
    public String getContest() {
        return contest;
    }

    /**
     * @param contest the contest to set
     */
    public void setContest(String contest) {
        this.contest = contest;
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
     * @return the place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(String place) {
        this.place = place;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
