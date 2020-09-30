package TimeTableGeneratorDesktopApp.DatabaseHelper;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static DatabaseConnection databaseConnection;
    private Connection connection;

    private DatabaseConnection(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/timetabledb", "root","root");
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
