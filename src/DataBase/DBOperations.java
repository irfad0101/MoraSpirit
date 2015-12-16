package DataBase;

import Domain.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;




public class DBOperations {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet use = null;
    //private EmployeeFactory emfac = null;
    private static DBOperations instance = null;
   
    //private String url = "jdbc:mysql://192.168.173.1:3306/SemesterProject";
    private String ip = "169.254.27.213";
    private String port = "3306";
    private String url = "jdbc:mysql://"+ip+":"+port+"/MoraSpirit";
    //private String url = "jdbc:mysql://localhost:3306/SemesterProject";
    
    //private String user = "root";
    //private String password = "";
    private String user = "moraspiritadmin";
    private String password = "mora1234";
    
    
    
    private DBOperations(){
        //this.emfac = new EmployeeFactory();
        setIP();
    }
    public void setIP(){
        String[] ipAndPort = Help.readIPandPort();
        if(ipAndPort[0]!=null&&ipAndPort[1]!=null){
            this.ip = ipAndPort[0];
            this.port = ipAndPort[1];
            this.url = "jdbc:mysql://"+ip+":"+port+"/MoraSpirit";           
        }
    }
    /*
     * Connection Establishment
     */
    
    public boolean setConenction() throws SQLException,ConnectionTimeOutException{
        boolean reachable = false;
        try {            
            Class.forName("com.mysql.jdbc.Driver").newInstance();            
            con = DriverManager.getConnection(url, user, password);           
            reachable = con.isValid(30);           
            
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
        
        }catch(Exception ex){            
            throw new ConnectionTimeOutException(ex.getMessage());
        }       
        if(!reachable)
            throw new ConnectionTimeOutException();
        return reachable;
    }
    public void closeConnection() throws SQLException{
        try{
            con.close();
            pst.close();
            use.close();
        }catch(NullPointerException ex){}
    }
    
    /*
     * Add singleton................................................................
     */
    public static DBOperations getInstace(){
        if(instance==null){
            synchronized(DBOperations.class){
                if(instance==null){
                    instance = new DBOperations();
                }
            }
        }     
        return instance;
    }
    
    /*
     * Add Data......................................................................
     */
    
    
    public boolean addStudent(Student student) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                  
        setConenction();
        pst = con.prepareStatement("INSERT INTO Student VALUES(?,?,?,?,?)");               

        pst.setString(1,student.getID());
        pst.setString(2,student.getFirstName());       
        pst.setString(3,student.getLastName());       
        pst.setString(4, student.getFaculty());
        pst.setString(5, student.getDepartment());

        pst.executeUpdate();
        
        pst = con.prepareStatement("INSERT INTO Involve VALUES(?,?)");               
        pst.setString(1, student.getID());
        pst.setString(2, student.getSportName());

        pst.executeUpdate();
        con.close();
        result = true;       
        closeConnection();        
        return result;
    }
    public boolean addResource(Resource resource) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
        
        setConenction();             
        pst = con.prepareStatement("INSERT INTO Resource VALUES(?,?,?,?)");               

        pst.setString(1,resource.getID());
        pst.setString(2, resource.getName());
        pst.setString(3, resource.getLocation());
        pst.setString(4,resource.getKeeperID());        

        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();        
        return result;
    }
    public boolean addPracticeSchedule(PracticeSchedule practiceSchedule) throws SQLException, ConnectionTimeOutException {
        boolean result = false; 
                    
        setConenction();              
        pst = con.prepareStatement("INSERT INTO PracticeSchedule VALUES(?,?,?,?,?,?)");  
       
        pst.setInt(1,practiceSchedule.getSessionID());
        pst.setString(2, practiceSchedule.getSportName());
        pst.setString(3, practiceSchedule.getResourceID());
        pst.setDate(4, practiceSchedule.getDate());
        pst.setTime(5,practiceSchedule.getStartTime());
        pst.setTime(6, practiceSchedule.getEndTime());
              
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    public boolean addAchievement(Achievement achievement) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                      
        setConenction();              
        //pst = con.prepareStatement("INSERT INTO Achievement VALUES(?,?,?,?,?,MD5(?),null)");  
        pst = con.prepareStatement("INSERT INTO Employee VALUES(?,?,?,?,?,?)");  
        pst.setString(1,achievement.getAchievementID());            
        pst.setString(2, achievement.getContest());
        pst.setDate(3,achievement.getDate());
        pst.setString(4, achievement.getPlace());
        pst.setString(5, achievement.getSportName());
        pst.setString(6, achievement.getDescription());      
        
        pst.executeUpdate();
        
        pst = con.prepareStatement("INSERT INTO Achieve VALUES(?,?)");  
        pst.setString(1,achievement.getStudentID());            
        pst.setString(2, achievement.getAchievementID());
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    public boolean addEquipment(Equipment equipment) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                      
        setConenction();              
        pst = con.prepareStatement("INSERT INTO Equipment VALUES(?,?,?,?,?,?,?)");     

        pst.setString(1,equipment.getItemNo());            
        pst.setString(2, equipment.getType());
        pst.setDate(3,equipment.getPurchaseDate());
        pst.setBoolean(4, equipment.isAvailable());
        pst.setFloat(5, equipment.getPurchasePrice());
        pst.setString(6, equipment.getCondition()); 
        pst.setString(7, equipment.getSportName());        
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    public boolean addBorrow(Borrow borrow) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
        
        if(!checkEquipmentAvailability(borrow.getItemNo()))
            return false;
                   
        setConenction();       
        pst = con.prepareStatement("INSERT INTO Borrow VALUES(?,?,?,?,?)");  

        pst.setString(1,borrow.getItemNo());            
        pst.setString(2, borrow.getStudentID());
        pst.setDate(3,borrow.getStartDate());
        pst.setDate(4, borrow.getEndDate());
        pst.setBoolean(5, borrow.isReturned());

        pst.executeUpdate();
        con.close();
        
        updateEquipmentAvailability(borrow.getItemNo(), false);
        
        result = true;
        closeConnection();
        return result;
    }
     
    
    public boolean addSport(Sport sport) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                   
        setConenction();       
        pst = con.prepareStatement("INSERT INTO Sport VALUES(?)");  
        pst.setString(1,sport.getSportName());        
       
        pst.executeUpdate();
        
        for(Utilization uti:sport.getUtilizationList()){
            pst = con.prepareStatement("INSERT INTO SportsResources VALUES(?,?,?)");  
            pst.setString(1,uti.getSportName());
            pst.setString(2,uti.getResourceID());
            pst.setFloat(3,uti.getUtilization());

            pst.executeUpdate();
        }
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    
    public boolean addAdmin(Admin admin) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                   
        setConenction();       
        pst = con.prepareStatement("INSERT INTO Admin VALUES(?)");  
        pst.setString(1,admin.getID());        
       
        pst.executeUpdate();

        pst = con.prepareStatement("INSERT INTO Users VALUES(?,?,?,?)");  
        pst.setString(1,admin.getID()); 
        pst.setString(2,admin.getName()); 
        pst.setInt(3,admin.getContactNo()); 
        pst.setString(4,admin.getPassword());
        
        pst.executeUpdate();

        con.close();

        result = true;
        closeConnection();
        return result;
    }
    
    public boolean addKeeper(Keeper keeper) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                   
        setConenction();       
        pst = con.prepareStatement("INSERT INTO Keeper VALUES(?,?)");  

        pst.setString(1,keeper.getID()); 
        pst.setString(2,keeper.getResource());
       
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    
    public boolean addCoach(Coach coach) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                   
        setConenction();       
        pst = con.prepareStatement("INSERT INTO Coach VALUES(?,?)");  

        pst.setString(1,coach.getID()); 
        pst.setString(2,coach.getSportName());
       
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
     
    public boolean addUser(User user) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                   
        setConenction();       
        pst = con.prepareStatement("INSERT INTO User VALUES(?,?,?)");  

        pst.setString(1,user.getID()); 
        pst.setString(2,user.getName());
        pst.setInt(3,user.getContactNo());
        
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    
     public boolean addEquipmentRequest(String equipmentType,String studentID) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                   
        setConenction();       
        pst = con.prepareStatement("INSERT INTO EquipmentRequest VALUES(?,?)");  

        pst.setString(1,studentID); 
        pst.setString(2,equipmentType);       
        
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    
    /*
     * Update data............................................................................. 
     */
    
     
    public boolean updateResource(Resource resource) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 

        setConenction();             
        pst = con.prepareStatement("UPDATE Resource SET ID = ?, Name = ?, Location = ?,"
                + "KeeperID = ? WHERE ID = ?");               

        pst.setString(1,resource.getID());
        pst.setString(2, resource.getName());
        pst.setString(3, resource.getLocation());
        pst.setString(4,resource.getKeeperID());
        
        pst.setString(5,resource.getID());

        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    
    public boolean updateEquipment(Equipment equipment) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
        setConenction();              
        pst = con.prepareStatement("UPDATE Equipment SET ItemNo = ?,Type = ?,PurchaseDate = ?,"
                + "Availability = ?, PurchasePrice = ?, CurrentCondition = ?, SportName = ? WHERE ItemNo = ?");               

        pst.setString(1,equipment.getItemNo());            
        pst.setString(2, equipment.getType());
        pst.setDate(3,equipment.getPurchaseDate());
        pst.setBoolean(4, equipment.isAvailable());
        pst.setFloat(5, equipment.getPurchasePrice());
        pst.setString(6, equipment.getCondition()); 
        pst.setString(7, equipment.getSportName()); 
        pst.setString(8, equipment.getItemNo()); 
        System.out.println(pst.toString());
        pst.executeUpdate();
        con.close();

        result = true;
           
        closeConnection();
        return result;
    }
    
    public boolean updateEquipmentAvailability(String itemNo,boolean availability) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
        
        setConenction();             
        pst = con.prepareStatement("UPDATE Equipment SET Availability = ? WHERE ItemNo = ?");               

        pst.setBoolean(1,availability);
        pst.setString(2, itemNo);
      
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    
    
    public boolean updateUtilization(Utilization utilization) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 

        setConenction();              
        pst = con.prepareStatement("UPDATE SportsResources SET PID = ?,SportName = ?,"
                + "ResourcesID = ?,Utilization = ? WHERE SportName = ? AND ResourcesID = ?");  
        
        pst.setString(1,utilization.getSportName());
        pst.setString(2,utilization.getResourceID());
        pst.setFloat(3,utilization.getUtilization());  
        pst.setString(4,utilization.getSportName());
        pst.setString(5,utilization.getResourceID());
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    
    
    
    /*
     * Load Data.................................................
     */
    
     
    public  ArrayList<Sport> loadSports() throws SQLException, ConnectionTimeOutException{
        ArrayList<Sport> resourceList = new ArrayList<>();
        
        setConenction();             
        pst = con.prepareStatement("SELECT * FROM Sport");              
        use = pst.executeQuery();                

        while(use.next()){                   
            Sport sport = new Sport();
            sport.setSportName(use.getString(1));                 
            resourceList.add(sport);
        }       
        closeConnection();
    
        return resourceList;
    }
   
    public  ArrayList<Resource> loadResource() throws SQLException, ConnectionTimeOutException{
        ArrayList<Resource> resourceList = new ArrayList<>();
        
        setConenction();             
        pst = con.prepareStatement("SELECT * FROM Resource");              
        use = pst.executeQuery();                

        while(use.next()){                   
            Resource resource = new Resource();
            resource.setID(use.getString(1));
            resource.setName(use.getString(2));
            resource.setLocation(use.getString(3));
            resource.setKeeperID(use.getString(4));            
            resourceList.add(resource);
        }       

        closeConnection();
    
        return resourceList;
    }
   
     public  ArrayList<Resource> loadResource(String sportName) throws SQLException, ConnectionTimeOutException{
        ArrayList<Resource> resourceList = new ArrayList<>();
        
        setConenction();             
        pst = con.prepareStatement("SELECT * FROM Resources NATURAL JOIN "
                + "(SELECT ResourceID as ID FROM SportsResources WHERE SportName = ?)as A "); 
        pst.setString(1,sportName);
        use = pst.executeQuery();                

        while(use.next()){                   
            Resource resource = new Resource();
            resource.setID(use.getString(1));
            resource.setName(use.getString(2));
            resource.setLocation(use.getString(3));
            resource.setKeeperID(use.getString(4));            
            resourceList.add(resource);
        }       

        closeConnection();
    
        return resourceList;
    }
      
    public  ArrayList<PracticeSchedule> loadPracticeSchedule() throws SQLException, ConnectionTimeOutException{
        ArrayList<PracticeSchedule> practiceScheduleList = new ArrayList<>();
        
        setConenction();             
        pst = con.prepareStatement("SELECT * FROM PracticeSchedule");              
        use = pst.executeQuery();                

        while(use.next()){                   
            PracticeSchedule practiceSchedule = new PracticeSchedule();
            practiceSchedule.setSessionID(use.getInt(1));
            practiceSchedule.setSportName(use.getString(2));
            practiceSchedule.setDate(use.getDate(3));
            practiceSchedule.setStartTime(use.getTime(4));
            practiceSchedule.setEndTime(use.getTime(5));
            practiceScheduleList.add(practiceSchedule);
        }       

        closeConnection();
    
        return practiceScheduleList;
    }
    
    public  ArrayList<Equipment> loadEquipments() throws SQLException, ConnectionTimeOutException{
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        
        setConenction();             
        pst = con.prepareStatement("SELECT * FROM Equipment");              
        use = pst.executeQuery();                

        while(use.next()){                   
            Equipment equipment = new Equipment();
            equipment.setItemNo(use.getString(1));
            equipment.setType(use.getString(2));
            equipment.setPurchaseDate(use.getDate(3));
            equipment.setAvailability(use.getBoolean(4));
            equipment.setPurchasePrice(use.getFloat(5));
            equipment.setCondition(use.getString(6));
            equipment.setSportName(use.getString(7));
            equipmentList.add(equipment);
        }       

        closeConnection();
    
        return equipmentList;
    }
    
     public  ArrayList<Equipment> loadEquipments(String sport) throws SQLException, ConnectionTimeOutException{
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        
        setConenction();             
        pst = con.prepareStatement("SELECT * FROM Equipment WHERE SportName = ?"); 
        pst.setString(1, sport);
        use = pst.executeQuery();                

        while(use.next()){                   
            Equipment equipment = new Equipment();
            equipment.setItemNo(use.getString(1));
            equipment.setType(use.getString(2));
            equipment.setPurchaseDate(use.getDate(3));
            equipment.setAvailability(use.getBoolean(4));
            equipment.setPurchasePrice(use.getFloat(5));
            equipment.setCondition(use.getString(6));
            equipment.setSportName(use.getString(7));
            equipmentList.add(equipment);
        }       

        closeConnection();
    
        return equipmentList;
    }
    /**/
    public  ArrayList<Equipment> loadAvailableEquipments() throws SQLException, ConnectionTimeOutException{
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        
        setConenction();             
        pst = con.prepareStatement("SELECT * FROM Equipment WHERE Availability = TRUE");              
        use = pst.executeQuery();                

        while(use.next()){                   
            Equipment equipment = new Equipment();
            equipment.setItemNo(use.getString(1));
            equipment.setType(use.getString(2));
            equipment.setPurchaseDate(use.getDate(3));
            equipment.setAvailability(use.getBoolean(4));
            equipment.setPurchasePrice(use.getFloat(5));
            equipment.setCondition(use.getString(6));
            equipment.setSportName(use.getString(7));
            equipmentList.add(equipment);
        }       

        closeConnection();
    
        return equipmentList;
    }
    public Equipment getEquipment(String equipmentNo) throws SQLException, ConnectionTimeOutException{
        
        Equipment equipment = null;
        setConenction();             
        pst = con.prepareStatement("SELECT * FROM Equipment WHERE ItemNo = ?");
        pst.setString(1, equipmentNo);
        use = pst.executeQuery();                

        if(use.next()){                   
            equipment = new Equipment();
            equipment.setItemNo(use.getString(1));
            equipment.setType(use.getString(2));
            equipment.setPurchaseDate(use.getDate(3));
            equipment.setAvailability(use.getBoolean(4));
            equipment.setPurchasePrice(use.getFloat(5));
            equipment.setCondition(use.getString(6));
            equipment.setSportName(use.getString(7));
            
        }       

        closeConnection();
    
        return equipment;
    }       
    
    public ArrayList<Equipment> getAvailableEquipments(String equipmentType,String sportName) throws SQLException, ConnectionTimeOutException{
        
        ArrayList<Equipment> equipments = new ArrayList<>();
        
        setConenction();             
        pst = con.prepareStatement("SELECT * FROM Equipment WHERE Type = ? AND "
                + "SportName = ? AND Availability = ?");
        pst.setString(1, equipmentType);
        pst.setString(2, sportName);
        pst.setBoolean(3, true);
        use = pst.executeQuery();                

        while(use.next()){                   
            Equipment equipment = new Equipment();
            equipment.setItemNo(use.getString(1));
            equipment.setType(use.getString(2));
            equipment.setPurchaseDate(use.getDate(3));
            equipment.setAvailability(use.getBoolean(4));
            equipment.setPurchasePrice(use.getFloat(5));
            equipment.setCondition(use.getString(6));
            equipment.setSportName(use.getString(7));
            equipments.add(equipment);
        }       

        closeConnection();
    
        return equipments;
    }       
   
    public ArrayList<TimeSlot> getResourceReservedTime(String resourceID,Date date) throws SQLException, ConnectionTimeOutException{
        
        ArrayList<TimeSlot> timeSlots = new ArrayList<>();
        
        setConenction();                   
        pst = con.prepareStatement("SELECT StartTime,EndTime FROM Booking WHERE Resources_ID = ? "
                + "AND Date = ? ");  
        pst.setString(1,resourceID);
        pst.setDate(2, date);
      
        use = pst.executeQuery();
        while(use.next()){                
            TimeSlot timeSlot = new TimeSlot();
            timeSlot.setStartTime(use.getTime(1));
            timeSlot.setEndTime(use.getTime(2));
            timeSlots.add(timeSlot);
            
        }
        
        pst = con.prepareStatement("SELECT StartTime,EndTime FROM PracticeSchedule"
                + " WHERE Resources_ID = ? AND Date = ? ");  
        pst.setString(1,resourceID);
        pst.setDate(2, date);
      
        use = pst.executeQuery();
        while(use.next()){                
            TimeSlot timeSlot = new TimeSlot();
            timeSlot.setStartTime(use.getTime(1));
            timeSlot.setEndTime(use.getTime(2));
            timeSlots.add(timeSlot);
            
        }
        
        closeConnection();
        return timeSlots;
    }
    
   
    
    /*
     * Search Data...........................................................................
     */  
    

    public ArrayList<Student> searchStudentByName(String name) throws SQLException, ConnectionTimeOutException{
       ArrayList<Student> studentList = new ArrayList<>();

       setConenction();             
       pst = con.prepareStatement("SELECT * FROM Student WHERE FirstName LIKE '%"+name+"%' OR"
               + " LastName LIKE '%"+name+"%'");              
       use = pst.executeQuery();               

       while(use.next()){                   
           Student student = new Student();

           student.setID(use.getString(1));
           student.setFirstName(use.getString(2));
           student.setLastName(use.getString(3));           
           student.setFaculty(use.getString(4));
           student.setDepartment(use.getString(5));

           studentList.add(student);
       }             
       closeConnection();
       return studentList;
    } 

    public ArrayList<Student> searchStudentByIndex(String index) throws SQLException, ConnectionTimeOutException{
        ArrayList<Student> studentList = new ArrayList<>();

        setConenction();             
        pst = con.prepareStatement("SELECT * FROM Student WHERE ID = ?"); 
        pst.setString(1,index);
        use = pst.executeQuery();               
               
        while(use.next()){                   
            Student student = new Student();

            student.setID(use.getString(1));
            student.setFirstName(use.getString(2));
            student.setLastName(use.getString(3));           
            student.setFaculty(use.getString(4));
            student.setDepartment(use.getString(5));
            
            studentList.add(student);
        }             
        closeConnection();
        return studentList;
    } 
     /*temp......temp......temp.......
    public ArrayList<Patient> searchPatients(String name) throws SQLException, ConnectionTimeOutException{
        ArrayList<Patient> patientList = new ArrayList<>();
        
        setConenction();              
        pst = con.prepareStatement("SELECT * FROM PatientFile WHERE FullName LIKE '%"+name+"%'");          
        use = pst.executeQuery();      

        while(use.next()){     

            Patient patient = new Patient();               
            patient.setPID(use.getInt(1));
            patient.setFirstName(use.getString(2));
            patient.setFullName(use.getString(3));
            patient.setLastName(use.getString(4));
            patient.setDateOfBirth(use.getDate(5));
            patient.setGender(use.getString(6));
            patient.setAddress(use.getString(7));
            patient.setNIC(use.getString(8));
            patient.setPatientContactNo(use.getInt(9));
            patient.setNameOfTheGuardian(use.getString(10));
            patient.setGuardianContactNo(use.getInt(11));
            patient.setBloodGroup(use.getString(12));
            patient.setAllergies(use.getString(13));   

            patientList.add(patient);
        }    
        closeConnection();
        
        return patientList;
    }
    public ArrayList<Patient> searchPatientsByNIC(String NIC) throws SQLException, ConnectionTimeOutException{
        ArrayList<Patient> patientList = new ArrayList<>();
        setConenction();            
        pst = con.prepareStatement("SELECT * FROM PatientFile WHERE NIC=?");

        pst.setString(1, NIC);
        use = pst.executeQuery();                
       
        while(use.next()){     

            Patient patient = new Patient();               
            patient.setPID(use.getInt(1));
            patient.setFirstName(use.getString(2));
            patient.setFullName(use.getString(3));
            patient.setLastName(use.getString(4));
            patient.setDateOfBirth(use.getDate(5));
            patient.setGender(use.getString(6));
            patient.setAddress(use.getString(7));
            patient.setNIC(use.getString(8));
            patient.setPatientContactNo(use.getInt(9));
            patient.setNameOfTheGuardian(use.getString(10));
            patient.setGuardianContactNo(use.getInt(11));
            patient.setBloodGroup(use.getString(12));
            patient.setAllergies(use.getString(13));   

            patientList.add(patient);
        }    
        closeConnection();
        return patientList;
    }
    /*
     * Delete Data............................................................................
     */
    
    public boolean deleteEmployee(int EID) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
        if(EID==1||EID==2)
        return false;

        setConenction();          
        pst = con.prepareStatement("DELETE FROM Employee WHERE EID=?");
        pst.setInt(1, EID);

        pst.executeUpdate();
        closeConnection();

        result = true;        
        return result;
    }
    /*
     * check Data.....................................................................
     */
    
     public boolean checkEquipmentAvailability(String equipmentNo) throws SQLException, ConnectionTimeOutException{
        
       
        setConenction();             
        pst = con.prepareStatement("SELECT Availability FROM Equipment WHERE ItemNo = ?");
        pst.setString(1, equipmentNo);
        use = pst.executeQuery();                

        if(use.next()){                   
            return use.getBoolean(1);
        }       

        closeConnection();
        return false;
    } 
    
    
    /*temp......temp......temp.......
     public Employee checkEmplyee(String uname,String pword) throws ConnectionTimeOutException{
        Employee employee=null;
        try {        
           setConenction();         
           pst = con.prepareStatement("SELECT * FROM Employee WHERE UserName = ? AND Password=MD5(?)"); 
           //pst = con.prepareStatement("SELECT * FROM Employee WHERE UserName = ? AND Password=?");   
           pst.setString(1,uname);
           pst.setString(2,pword);
           use = pst.executeQuery();

           while(use.next()){                   
               employee = emfac.getEmployee(use.getString(2));                
               employee.setEID(use.getInt(1));
               employee.setName(use.getString(3));
               employee.setNIC(use.getString(4));
               employee.setUsername(use.getString(5));
               employee.setPassword(use.getString(6));         
           }             
           closeConnection();


       } catch (SQLException ex) {
           
       }
       return employee;
    }
    public boolean checkPatientNIC(String NIC) throws ConnectionTimeOutException{        
        try {
            setConenction();             
            pst = con.prepareStatement("SELECT * FROM PatientFile WHERE NIC=?");
            pst.setString(1, NIC);
            use = pst.executeQuery();
            while(use.next()){     
                return true;
            }    
            con.close();           
            
        } catch (SQLException ex) {
            return false;
        }
        return false;
    }
    public boolean checkPID(String pid) throws ConnectionTimeOutException{
        try {
            setConenction();            
            pst = con.prepareStatement("SELECT * FROM PatientFile WHERE PID=?");
            pst.setString(1, pid);
            use = pst.executeQuery();                
           
            if(use.next()){     
                return true;
            }    
            closeConnection();
           
        } catch (SQLException ex) {
            return false;
        }
         return false;
    }
    public boolean checkLabTecID(String eid) throws ConnectionTimeOutException{
        try {
            setConenction();            
            pst = con.prepareStatement("SELECT * FROM Employee WHERE EID = ? AND Position = 'LabTechniciant' ");
            pst.setString(1, eid);
            use = pst.executeQuery();                
           
            if(use.next()){     
                return true;
            }    
            closeConnection();
           
        } catch (SQLException ex) {
            return false;
        }
         return false;
    }
    public boolean checkUserName(String uname) throws ConnectionTimeOutException{       
        try {
            setConenction();
            pst = con.prepareStatement("SELECT * FROM Employee WHERE UserName = ?");   
            pst.setString(1,uname);
            use = pst.executeQuery();

            if(use.next()){                   
                return true;        
            }             
            closeConnection();
           
        } catch (SQLException ex) {
            return false;
        }
        return false;
    }
    public boolean checkEmployeeNIC(String NIC) throws ConnectionTimeOutException{        
        try {
            setConenction();             
            pst = con.prepareStatement("SELECT * FROM Employee WHERE NIC=?");
            pst.setString(1, NIC);
            use = pst.executeQuery();                
           
            if(use.next()){     
                return true;
            }    
            con.close();           
            
        } catch (SQLException ex) {
            return false;
        }
        return false;
    }
    public boolean checkAdmin(String uname,String pass) throws ConnectionTimeOutException{       
       
         try {
            setConenction();
            pst = con.prepareStatement("SELECT * FROM Employee WHERE EID = 1 AND UserName = ? AND Password = MD5(?)");   
            pst.setString(1,uname);
            pst.setString(2,pass);
            use = pst.executeQuery();

            if(use.next()){                   
                return true;        
            }             
            closeConnection();
            
        } catch (SQLException ex) {
            return false;
        }
        return false;
    }
    public boolean checkPassword(String pword) throws ConnectionTimeOutException{       
        try {
            setConenction();               
            pst = con.prepareStatement("SELECT * FROM Employee WHERE Password=MD5(?)"); 
            //pst = con.prepareStatement("SELECT * FROM Employee WHERE Password = ?");   
            pst.setString(1,pword);        
            use = pst.executeQuery();
            if(use.next()){                   
                return true;        
            }             
            closeConnection();            
        } catch (SQLException ex) {
            return false;
        }
        return false;
    }
    public boolean checkDoctorID(String eid) throws ConnectionTimeOutException{        
        try {
            setConenction();
            pst = con.prepareStatement("SELECT * FROM Employee WHERE EID = ?");   
            pst.setString(1,eid);
            use = pst.executeQuery();

            if(use.next()){  
                if(use.getString(2).equals("Doctor"))
                    return true;        
            }             
            closeConnection();            
        } catch (SQLException ex) {
            return false;
        }
        return false;
    }
    public boolean isRoomAvailable(int roomNo) throws ConnectionTimeOutException{       
        try {
            setConenction();               
            
            pst = con.prepareStatement("SELECT * FROM room WHERE RoomNo = ?");   
            pst.setInt(1,roomNo);        
            use = pst.executeQuery();
            if(use.next() && use.getBoolean(2)){                   
                return true;        
            }             
            closeConnection();            
        } catch (SQLException ex) {
            return false;
        }
        return false;
    }
    
    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
