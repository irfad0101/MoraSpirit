package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.Date;



public class DBOperations {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet use = null;
    //private EmployeeFactory emfac = null;
    private static DBOperations instance = null;
   
    //private String url = "jdbc:mysql://192.168.173.1:3306/SemesterProject";
    private String ip = "192.168.173.1";
    private String port = "3306";
    private String url = "jdbc:mysql://"+ip+":"+port+"/SemesterProject";
    //private String url = "jdbc:mysql://localhost:3306/SemesterProject";
    
    //private String user = "root";
    //private String password = "";
    private String user = "hosdataadmin";
    private String password = "coperativehos7456391";
    
    
    
    private DBOperations(){
        //this.emfac = new EmployeeFactory();
        setIP();
    }
    public void setIP(){
        String[] ipAndPort = Help.readIPandPort();
        if(ipAndPort[0]!=null&&ipAndPort[1]!=null){
            this.ip = ipAndPort[0];
            this.port = ipAndPort[1];
            this.url = "jdbc:mysql://"+ip+":"+port+"/SemesterProject";           
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
    
    /*temp......temp......temp.......
    public boolean addPatient(Patient patient) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                  
        setConenction();
        pst = con.prepareStatement("INSERT INTO PatientFile VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");               

        pst.setInt(1,patient.getPID());
        pst.setString(2,patient.getFirstName());
        pst.setString(3,patient.getFullName());
        pst.setString(4,patient.getLastName());            
        pst.setDate(5,patient.getDateOfBirth());
        pst.setString(6, patient.getGender());
        pst.setString(7, patient.getAddress());
        pst.setString(8, patient.getNIC());
        pst.setInt(9, patient.getPatientContactNo());
        pst.setString(10, patient.getNameOfTheGuardian());
        pst.setInt(11, patient.getGuardianContactNo());
        pst.setString(12, patient.getBloodGroup());
        pst.setString(13, patient.getAllergies());

        pst.executeUpdate();
        con.close();

        result = true;       
        closeConnection();        
        return result;
    }
    public boolean addMedicalReport(MedicalReport medicalReport) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
        // For insert to medical report there should be a PID which has same PID in medical report
        setConenction();             
        pst = con.prepareStatement("INSERT INTO MedicalReport VALUES(?,?,?,?,?,?)");               

        pst.setInt(1,medicalReport.getPID());
        pst.setDate(2, medicalReport.getDate());
        pst.setInt(3, medicalReport.getDoctorID());
        pst.setInt(4,medicalReport.getMedicalReportNum());
        pst.setString(5, medicalReport.getTestTypes());
        pst.setString(6, medicalReport.getTreatementDescription());            

        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();        
        return result;
    }
    public boolean addLabReport(LabReport labReport) throws SQLException, ConnectionTimeOutException {
        boolean result = false; 
                    
        setConenction();              
        pst = con.prepareStatement("INSERT INTO LabReport VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");  
        int index;

        pst.setInt(1,labReport.getPID());
        pst.setDate(2, labReport.getDate());
        pst.setInt(3, labReport.getLabReportNo());
        pst.setInt(4,labReport.getTestType());
        pst.setInt(5, labReport.getLabTechID());
        index = 6;
        for(String data:labReport.getDataList()){
            pst.setString(index++, data);
        }
        for(int i = 0;(i+index)<22;i++){               
            pst.setString(index+i, null);
        }           
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    public boolean addEmployee(Employee employee) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                      
        setConenction();              
        pst = con.prepareStatement("INSERT INTO Employee VALUES(?,?,?,?,?,MD5(?),null)");  
        //pst = con.prepareStatement("INSERT INTO Employee VALUES(?,?,?,?,?,?,null)");  
        pst.setInt(1,employee.getEID());            
        pst.setString(2, employee.getPosition());
        pst.setString(3,employee.getName());
        pst.setString(4, employee.getNIC());
        pst.setString(5, employee.getUsername());
        pst.setString(6, employee.getPassword());      
        
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    public boolean addChronicConditionsReport(ChronicConditionsReport chronicConditionsReport) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                      
        setConenction();              
        pst = con.prepareStatement("INSERT INTO ChronicConditions VALUES(?,?,?,?,?,?,?,?)");     

        pst.setInt(1,chronicConditionsReport.getPID());            
        pst.setString(2, chronicConditionsReport.getChronicConditionsCol());
        pst.setBoolean(3,chronicConditionsReport.isHeartDisease());
        pst.setBoolean(4, chronicConditionsReport.isStroke());
        pst.setBoolean(5, chronicConditionsReport.isCancer());
        pst.setBoolean(6, chronicConditionsReport.isDiabetes()); 
        pst.setBoolean(7, chronicConditionsReport.isObesity()); 
        pst.setBoolean(8, chronicConditionsReport.isArthritis()); 
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    public boolean addRoom(Room room) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                   
        setConenction();       
        pst = con.prepareStatement("INSERT INTO room VALUES(?,?,?,?)");  

        pst.setInt(1,room.getRoomNo());            
        pst.setBoolean(2, room.isAvailability());
        pst.setInt(3,room.getPID());
        pst.setDate(4, room.getDate());

        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
   
    /*
     * Update data............................................................................. 
     */
    
    /*temp......temp......temp.......
    public boolean updatePatient(Patient patient) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 

        setConenction();             
        pst = con.prepareStatement("UPDATE PatientFile SET FirstName = ?,FullName = ?,LastName = ?,DateOfBirth = ?,Gender = ?,"
                + "Address = ?,NIC = ?,PatientContactNo = ?,NameOfTheGuardian = ?,GuardianContactNo = ?,BloodGroup = ?,Allergies = ?"
                + " WHERE PID = ?");               

        pst.setInt(13,patient.getPID());
        pst.setString(1,patient.getFirstName());
        pst.setString(2,patient.getFullName());
        pst.setString(3,patient.getLastName());            
        pst.setDate(4,patient.getDateOfBirth());
        pst.setString(5, patient.getGender());
        pst.setString(6, patient.getAddress());
        pst.setString(7, patient.getNIC());
        pst.setInt(8, patient.getPatientContactNo());
        pst.setString(9, patient.getNameOfTheGuardian());
        pst.setInt(10, patient.getGuardianContactNo());
        pst.setString(11, patient.getBloodGroup());
        pst.setString(12, patient.getAllergies());

        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    
    public boolean updateMedicalReport(MedicalReport medicalReport) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
        setConenction();              
        pst = con.prepareStatement("Update MedicalReport SET PID = ?,Date = ?,DoctorID = ?,TestTypes = ?, TreatementDescription = ? WHERE MedicalReportNum = ?");               

        pst.setInt(1,medicalReport.getPID());
        pst.setDate(2, medicalReport.getDate());
        pst.setInt(3, medicalReport.getDoctorID());
        pst.setInt(6,medicalReport.getMedicalReportNum());
        pst.setString(4, medicalReport.getTestTypes());
        pst.setString(5, medicalReport.getTreatementDescription());            

        pst.executeUpdate();
        con.close();

        result = true;
           
        closeConnection();
        return result;
    }
    
    public boolean updateLabReport(LabReport labReport) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 

        setConenction();              
        pst = con.prepareStatement("UPDATE LabReport SET PID = ?,Date = ?,TestType = ?,LabTechID = ?,"
                + "Data1 = ?,Data2 = ?,Data3 = ?,Data4 = ?,Data5 = ?,Data6 = ?,Data7 = ?,Data8 = ?,Data9 = ?,Data10 = ?,"
                + "Data11 = ?,Data12 = ?,Data13 = ?,Data14 = ?,Data15 = ?,Data16 = ? WHERE LabReportNo = ?");  
        int index;

        pst.setInt(1,labReport.getPID());
        pst.setDate(2, labReport.getDate());
        pst.setInt(21, labReport.getLabReportNo());
        pst.setInt(3,labReport.getTestType());
        pst.setInt(4, labReport.getLabTechID());
        index = 5;       
        for(String data:labReport.getDataList()){
            pst.setString(index++, data);
        }
        for(int i = 0;(i+index)<21;i++){               
            pst.setString(index+i, null);
        }           
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    
    public boolean updateEmployee(Employee employee) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 

        setConenction();             
        pst = con.prepareStatement("UPDATE Employee SET EID = ?,Name = ?,NIC = ?");              

        pst.setInt(1,employee.getEID());          
        pst.setString(3,employee.getName());
        pst.setString(4, employee.getNIC());

        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    public boolean updateEmployeeUserNamePassWord(Employee employee) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
            
        setConenction();              
        pst = con.prepareStatement("UPDATE Employee SET UserName = ?,Password = MD5(?) WHERE EID = ?"); 
        //pst = con.prepareStatement("UPDATE Employee SET UserName = ?,Password = ? WHERE EID = ?");              

        pst.setString(1,employee.getUsername());          
        pst.setString(2,employee.getPassword());
        pst.setInt(3,employee.getEID());

        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }   
    public boolean updateManager(Manager manager) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                    
        setConenction();           
        pst = con.prepareStatement("UPDATE Employee SET Name = ?, NIC = ?,UserName = ?,Password = MD5(?) WHERE EID = 2");
        //pst = con.prepareStatement("UPDATE Employee SET Name = ?, NIC = ?,UserName = ?,Password = ? WHERE EID = 1");              

        pst.setString(1,manager.getName());
        pst.setString(2,manager.getNIC());
        pst.setString(3,manager.getUsername());          
        pst.setString(4,manager.getPassword());            
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }   
    public boolean setDoctorAvailability(int EID,boolean availability) throws SQLException, ConnectionTimeOutException{
        boolean result = false;                    
        setConenction();               
        pst = con.prepareStatement("UPDATE Employee SET Availability = ? WHERE EID = ? ");  

        pst.setBoolean(1,availability);            
        pst.setInt(2,EID);                
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    public boolean setRoomAvailability(int roomNo,boolean availability) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                      
        setConenction();                  
        pst = con.prepareStatement("UPDATE room SET Availability = ? WHERE roomNo = ? ");  

        pst.setBoolean(1,availability);            
        pst.setInt(2,roomNo);                
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
    public boolean updateRoom(Room room) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                   
        setConenction();              
        //pst = con.prepareStatement("INSERT INTO Employee VALUES(?,?,?,?,?,MD5(?))");  
        pst = con.prepareStatement("UPDATE room SET PID = ?, Date = ?,Availability = ? WHERE RoomNo = ?");  

        pst.setInt(1,room.getPID());
        pst.setDate(2, room.getDate());
        pst.setBoolean(3, room.isAvailability());
        pst.setInt(4,room.getRoomNo());    
        

        pst.executeUpdate();
        
        result = true;
        closeConnection();
        return result;
    }
    
    public boolean updateChronicConditionsReport(ChronicConditionsReport chronicConditionsReport) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                      
        setConenction();              
        pst = con.prepareStatement("UPDATE ChronicConditions SET ChronicConditionscol = ?, HeartDisease = ?, Stroke = ?, Cancer = ?, Diabetes = ?, Obesity = ?, Arthritis = ? WHERE PID = ?");     
         
        pst.setString(1, chronicConditionsReport.getChronicConditionsCol());
        pst.setBoolean(2,chronicConditionsReport.isHeartDisease());
        pst.setBoolean(3, chronicConditionsReport.isStroke());
        pst.setBoolean(4, chronicConditionsReport.isCancer());
        pst.setBoolean(5, chronicConditionsReport.isDiabetes()); 
        pst.setBoolean(6, chronicConditionsReport.isObesity()); 
        pst.setBoolean(7, chronicConditionsReport.isArthritis()); 
        pst.setInt(8,chronicConditionsReport.getPID());   
        pst.executeUpdate();
        con.close();

        result = true;
        closeConnection();
        return result;
    }
   
    
    /*
     * Load Data.................................................
     */
    
    /*temp......temp......temp.......
    public  ArrayList<Patient> loadPatients() throws SQLException, ConnectionTimeOutException{
        ArrayList<Patient> patientList = new ArrayList<>();
        
        setConenction();             
        pst = con.prepareStatement("SELECT * FROM PatientFile");              
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
   
    public Patient getPatient(int PID) throws SQLException, ConnectionTimeOutException{
        Patient patient = null;
       
        setConenction();          
        pst = con.prepareStatement("SELECT * FROM PatientFile WHERE PID = ?");
        pst.setInt(1,PID);
        use = pst.executeQuery();                

        if(use.next()){                   
            patient = new Patient();

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

        }       

        closeConnection();
       
        return patient;
    }
    
    public ChronicConditionsReport getChronicCondotionReport(int PID) throws SQLException, ConnectionTimeOutException{
        ChronicConditionsReport ChronicCondition = null;
       
        setConenction();          
        pst = con.prepareStatement("SELECT * FROM ChronicConditions WHERE PID = ?");
        pst.setInt(1,PID);
        use = pst.executeQuery();                

        if(use.next()){                   
            ChronicCondition = new ChronicConditionsReport();
            ChronicCondition.setPID(use.getInt(1));
            ChronicCondition.setChronicConditionsCol(use.getString(2));
            ChronicCondition.setHeartDisease(use.getBoolean(3));
            ChronicCondition.setStroke(use.getBoolean(4));
            ChronicCondition.setCancer(use.getBoolean(5));
            ChronicCondition.setDiabetes(use.getBoolean(6));
            ChronicCondition.setObesity(use.getBoolean(7));
            ChronicCondition.setArthritis(use.getBoolean(8));
        }       

        closeConnection();
       
        return ChronicCondition;
    }
    
    public ArrayList<Doctor> loadDoctors() throws SQLException, ConnectionTimeOutException{
        ArrayList<Doctor> doctorList = new ArrayList<>();
        
        setConenction();               
        pst = con.prepareStatement("SELECT * FROM Employee WHERE Position='Doctor'");              
        use = pst.executeQuery();                

        while(use.next()){                   
            Doctor doctor = new Doctor();

            doctor.setEID(use.getInt(1));
            doctor.setName(use.getString(3));
            doctor.setNIC(use.getString(4));            
            doctor.setAvailablity(use.getBoolean(7));

            doctorList.add(doctor);
        }             
        closeConnection();
        
        return doctorList;
    }
    
    public ArrayList<Employee> loadEmplyee() throws SQLException, ConnectionTimeOutException{
        ArrayList<Employee> employeeList = new ArrayList<>();

        setConenction();        
        pst = con.prepareStatement("SELECT * FROM Employee WHERE Position <> 'Manager' AND Position <> 'Admin'");   
        use = pst.executeQuery();

        while(use.next()){                   
            Employee employee = emfac.getEmployee(use.getString(2));                
            employee.setEID(use.getInt(1));
            employee.setName(use.getString(3));
            employee.setNIC(use.getString(4));            
            employeeList.add(employee);
        }             
        closeConnection();
        return employeeList;
    }
    public Employee getEmplyee(int EID) throws SQLException, ConnectionTimeOutException{
        Employee employee=null;
        
        setConenction();            
        pst = con.prepareStatement("SELECT * FROM Employee WHERE EID=?");              
        pst.setInt(1, EID);
        use = pst.executeQuery();

        while(use.next()){                   
            employee = emfac.getEmployee(use.getString(2));                
            employee.setEID(use.getInt(1));
            employee.setName(use.getString(3));
            employee.setNIC(use.getString(4));
                   
        }             
        closeConnection();
        return employee;
    }
    public ArrayList<Date> getLabDates(int PID) throws SQLException, ConnectionTimeOutException{
        ArrayList<Date> dateList = new ArrayList<>();
        
        setConenction();              
        pst = con.prepareStatement("SELECT * FROM LabReport WHERE PID= ?");
        pst.setInt(1,PID);
        use = pst.executeQuery();                

        while(use.next()){                   
            dateList.add(use.getDate(2));
        }         
        closeConnection();
        return dateList;
    }
    public ArrayList<Date> getMedicalDates(int PID) throws SQLException, ConnectionTimeOutException{
        ArrayList<Date> dateList = new ArrayList<>();
       
        setConenction();           
        pst = con.prepareStatement("SELECT * FROM MedicalReport WHERE PID= ?");
        pst.setInt(1,PID);
        use = pst.executeQuery();                

        while(use.next()){                   
            dateList.add(use.getDate(2));
        }         
        closeConnection();
        return dateList;
    }
    public ArrayList<MedicalReport> getMedicalReports(int PID,Date date) throws SQLException, ConnectionTimeOutException{
        ArrayList<MedicalReport> medicalReportList = new ArrayList<>();      
        setConenction();             
        pst = con.prepareStatement("SELECT * FROM MedicalReport WHERE PID= ? AND Date =?");
        pst.setInt(1,PID);
        pst.setDate(2, date);
        use = pst.executeQuery();                

        while(use.next()){                  
            MedicalReport medicalReport =  new MedicalReport();
            medicalReport.setPID(use.getInt(1));
            medicalReport.setDate(use.getDate(2));
            medicalReport.setDoctorID(use.getInt(3));
            medicalReport.setMedicalReportNum(use.getInt(4));
            medicalReport.setTestTypes(use.getString(5));
            medicalReport.setTreatementDescription(use.getString(6));     
            medicalReportList.add(medicalReport);
        }         
        closeConnection();
        
        return medicalReportList;
    }
    
    public MedicalReport getMedicalReport(int medicalReportNum) throws SQLException, ConnectionTimeOutException{
        MedicalReport medicalReport = null;

        setConenction();
        pst = con.prepareStatement("SELECT * FROM MedicalReport WHERE MedicalReportNum = ?");
        pst.setInt(1,medicalReportNum);

        use = pst.executeQuery();                

        if(use.next()){                  
            medicalReport =  new MedicalReport();
            medicalReport.setPID(use.getInt(1));
            medicalReport.setDate(use.getDate(2));
            medicalReport.setDoctorID(use.getInt(3));
            medicalReport.setMedicalReportNum(use.getInt(4));
            medicalReport.setTestTypes(use.getString(5));
            medicalReport.setTreatementDescription(use.getString(6));     

        }         
        closeConnection();
        return medicalReport;
    }
    
    public ArrayList<LabReport> getLabReports(int PID,Date date) throws SQLException, ConnectionTimeOutException{
        ArrayList<LabReport> labReportList = new ArrayList<>();       
        setConenction();             
        pst = con.prepareStatement("SELECT * FROM LabReport WHERE PID = ? AND Date = ?");
        pst.setInt(1,PID);
        pst.setDate(2, date);
        use = pst.executeQuery();             

        while(use.next()){       

            LabReport labReport =  new LabReport();
            labReport.setPID(use.getInt(1));
            labReport.setDate(use.getDate(2));
            labReport.setLabReportNo(use.getInt(3));
            labReport.setTestType(use.getInt(4));
            labReport.setLabTechID(use.getInt(5));          

            int index = 6;

            for(int i = 0;i<16;i++){                
                String data = use.getString(index++);
                if(data!=null)
                    labReport.addDataToTheList(data);
            }               
            labReportList.add(labReport);
        }         
        closeConnection();
        return labReportList;
    }
    
    public LabReport getLabReport(int labReportNo) throws SQLException, ConnectionTimeOutException{
        LabReport labReport = null;        
        setConenction();             
        pst = con.prepareStatement("SELECT * FROM LabReport WHERE LabReportNo = ?");
        pst.setInt(1,labReportNo);

        use = pst.executeQuery();             

        if(use.next()){       

            labReport =  new LabReport();
            labReport.setPID(use.getInt(1));
            labReport.setDate(use.getDate(2));
            labReport.setLabReportNo(use.getInt(3));
            labReport.setTestType(use.getInt(4));
            labReport.setLabTechID(use.getInt(5));          

            int index = 6;

            for(int i = 0;i<16;i++){                
                String data = use.getString(index++);
                if(data!=null)
                    labReport.addDataToTheList(data);
            }                

        }         
        closeConnection();
        return labReport;
    }
    public LabReport getLastLabReport() throws SQLException, ConnectionTimeOutException{
        LabReport labReport = null;
        int labReportNo = getLastLabReportNo();        
        setConenction();              
        pst = con.prepareStatement("SELECT * FROM LabReport WHERE LabReportNo = ?");

        pst.setInt(1,labReportNo);            
        use = pst.executeQuery();      
        if(use.next()){       

            labReport =  new LabReport();
            labReport.setPID(use.getInt(1));
            labReport.setDate(use.getDate(2));
            labReport.setLabReportNo(use.getInt(3));
            labReport.setTestType(use.getInt(4));
            labReport.setLabTechID(use.getInt(5));          

            int index = 6;
            for(int i = 0;i<16;i++){                
                String data = use.getString(index++);
                if(data!=null)
                    labReport.addDataToTheList(data);
            }    
            
        }    
        closeConnection();
        return labReport;
    }
    public ArrayList<Room> getAddmitedRooms() throws SQLException, ConnectionTimeOutException{
        ArrayList<Room> roomList = new ArrayList<>();    
        setConenction();             
        pst = con.prepareStatement("SELECT * FROM room WHERE Availability = 0");
        
        use = pst.executeQuery();             

        while(use.next()){ 
            
            Room room = new Room();
            room.setRoomNo(use.getInt(1));
            room.setAvailability(use.getBoolean(2));            
            room.setPID(use.getInt(3));
            room.setDate(use.getDate(4));
            roomList.add(room);
        }         
        closeConnection();
        return roomList;
    }
    public int getLastPID() throws SQLException, ConnectionTimeOutException{
        int pid = -1;
        setConenction();            
        pst = con.prepareStatement("SELECT MAX(PID) FROM PatientFile");
        use = pst.executeQuery();   
        if(use.next())
            pid = use.getInt(1);

        return pid;
    }
    public int getPID(String nic) throws SQLException, ConnectionTimeOutException{
        int pid = -1;
        setConenction();            
        pst = con.prepareStatement("SELECT MAX(PID) FROM PatientFile WHERE NIC = ?");
        pst.setString(1,nic);
        use = pst.executeQuery();   
        if(use.next())
            pid = use.getInt(1);

        return pid;
    }
    public int getLastLabReportNo() throws SQLException{
        int labReportNo = -1;        
        con = DriverManager.getConnection(url, user, password);               
        pst = con.prepareStatement("SELECT MAX(LabReportNo) FROM LabReport");
        use = pst.executeQuery();  

        if(use.next())
            labReportNo = use.getInt(1);
        closeConnection();
        return labReportNo;
    }
    public boolean getDoctorAvailability(int EID) throws SQLException, ConnectionTimeOutException{
        boolean result = false; 
                 
        setConenction();                   
        pst = con.prepareStatement("SELECT * FROM Employee WHERE EID = ? ");  
        pst.setInt(1, EID);
        use = pst.executeQuery();
        if(use.next()){                
            return use.getBoolean(7);
        }

        result = true;
        closeConnection();
        return result;
    }
    /*
     * Search Data...........................................................................
     */
    
    /*temp......temp......temp.......
     public ArrayList<Doctor> searchDoctors(String name) throws SQLException, ConnectionTimeOutException{
        ArrayList<Doctor> doctorList = new ArrayList<>();

        setConenction();             
        pst = con.prepareStatement("SELECT * FROM Employee WHERE Position='Doctor' AND Name LIKE '%"+name+"%'");              
        use = pst.executeQuery();               
               
        while(use.next()){                   
            Doctor doctor = new Doctor();

            doctor.setEID(use.getInt(1));
            doctor.setName(use.getString(3));
            doctor.setNIC(use.getString(4));           
            doctor.setAvailablity(use.getBoolean(7));

            doctorList.add(doctor);
        }             
        closeConnection();
        return doctorList;
    } 
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
