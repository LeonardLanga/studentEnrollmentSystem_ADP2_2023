package za.ac.cput.studentenrolmentsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Josshua Mokwebo
 *
 */
public class DBConnection {

  

    public static Connection getConection() throws SQLException {

        String database_url = "jdbc:derby://localhost:1527/StudentEnrolmentDB";
        String username = "administrator";
        String password = "password";
        
        Connection con= DriverManager.getConnection(database_url, username, password);
        return con; 

    }

   
}
