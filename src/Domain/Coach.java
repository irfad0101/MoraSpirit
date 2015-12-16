package Domain;


public class Coach extends User{
    private String sportName;

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
}
