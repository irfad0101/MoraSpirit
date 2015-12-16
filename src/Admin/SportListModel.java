/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Domain.Sport;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Irfad Hussain
 */
public class SportListModel extends AbstractListModel<Sport>{

    private ArrayList<Sport> sportsList;
    
    public void setSportList(ArrayList<Sport> sportList){
        this.sportsList = sportList;
    }
    
    
    
    @Override
    public int getSize() {
        return sportsList.size();
    }

    @Override
    public Sport getElementAt(int index) {
        return sportsList.get(index);
    }
    
}
