package Domain;

import java.util.ArrayList;


public class Sport {
    private String sportName;
    private ArrayList<Utilization> utilizationList;
    
    
    public Sport(){
        this.utilizationList = new ArrayList<>();
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
    
    public void addUtilization(Utilization utilization){
        utilization.setSportName(sportName);
        this.getUtilizationList().add(utilization);
    }

    /**
     * @return the utilizationList
     */
    public ArrayList<Utilization> getUtilizationList() {
        return utilizationList;
    }
}
