package DataBase;


public class ConnectionTimeOutException extends Exception{
    
    public ConnectionTimeOutException(){
        super("Cannot connect to the database in time");
    }
    public ConnectionTimeOutException(String message){
        super(message);
    }
    
}
