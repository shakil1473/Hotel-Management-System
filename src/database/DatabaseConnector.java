package database;



import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shakil
 */
public class DatabaseConnector {
    
    static Connection connection;
    
    public static Connection connectDatabase(){
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","shakil","1473");//database connectin (" ","userid","password")
            return connection;
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null,exception);
        }
            return null;
    }
}
