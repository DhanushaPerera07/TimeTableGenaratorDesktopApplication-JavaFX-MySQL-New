package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.Controller;
import TimeTableGeneratorDesktopApp.DatabaseQueries.DatabaseCreation;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static String databaseName = "timetabledb";
    public static String portNo = "3306";
    public static String user = "root";
    public static String password = "root";

    private static DatabaseConnection databaseConnection;
    private Connection connection;

    private DatabaseConnection(){
        try{
            //connection = DriverManager.getConnection("jdbc:mysql://remotemysql.com:"+ portNo +"/fN7hdgbNPn", ""+ user +"", ""+ password +"");
            //connection = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:"+ portNo +"/sql12368426", ""+ user +"", ""+ password +"");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:"+ portNo +"/timetabledb", ""+ user +"", ""+ password +"");
            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/timetabledb", "root","root");
        }catch(Exception ex){
            System.out.println("Error: getConnection() :::: " + ex.getMessage());
        }
    }

    public static DatabaseConnection getInstance() {
        return (databaseConnection == null)? databaseConnection = new DatabaseConnection() : databaseConnection;
    }

    public Connection getConnection(){
        return connection;
    }
}
