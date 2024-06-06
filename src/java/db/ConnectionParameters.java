package db;

/**
 * It's better practice to store this information in a configuration file.
 */
public class ConnectionParameters {
    
    // use this line for JavaDB
    //public static final String URL = "jdbc:derby://localhost:1527/restaurantdb";
    
    // use this line for MySQL
    public static final String URL = "jdbc:mysql://localhost:3306/blockbusterdb?zeroDateTimeBehavior=CONVERT_TO_NULL&autoReconnect=false&useSSL=false&allowPublicKeyRetrieval=true";
    public static final String USERNAME = "bro";
    public static final String PASSWORD = "bro";
    
    // no instantiation allowed
    private ConnectionParameters() {}
    
} // end class ConnectionParameters
