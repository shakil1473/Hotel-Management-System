/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooms;

import database.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Shakil
 */
public class UpdateRoomStatus {
    
    Connection connection;
    ResultSet resultSet;
    PreparedStatement preparedStatement;
    
    public void updateRoomStatus(){
        
        connection = DatabaseConnector.connectDatabase();
        
        int checkedOut;
        
        String roomNo;
        String queryUpdate;
        String queryDelete;
        String queryCheckedOut = "select roomno,checkout-sysdate from dual,reservation";
        
        try{
            preparedStatement = connection.prepareStatement(queryCheckedOut);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                
                checkedOut = resultSet.getInt(2);
                
                if(checkedOut<0){
                    roomNo = resultSet.getString(1);
                    queryUpdate ="update rooms set available = 0 where roomno='"+roomNo+"'";
                    queryDelete ="delete from reservation where roomno='"+roomNo+"'";
                    
                    preparedStatement = connection.prepareStatement(queryUpdate);
                    preparedStatement.executeQuery();
                    preparedStatement.close();
                    
                    preparedStatement = connection.prepareStatement(queryDelete);
                    preparedStatement.executeQuery();
                    preparedStatement.close();
                    
                }
            }
            JOptionPane.showMessageDialog(null, "Room Status Updated");
            
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null, exception);
        }
        
    }
}
