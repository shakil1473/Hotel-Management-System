package user;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Shakil
 */
import user.*;
public class ButtonPress {
    static public void btnLogOut(){
        
        Login login = new Login();
        login.setVisible(true);
    }
    
    static public void btnHome(){
        Home home = new Home();
        home.setVisible(true);
    }
    
    static public void btnCancelReservation(){
        
        CancelReservation cancelReservation = new CancelReservation();
        cancelReservation.setVisible(true);
    }
    
    static public void btnReservationDetails(){
        
        ReservationDetails reservationDetails = new ReservationDetails();
        reservationDetails.setVisible(true);
    }
    static public void btnProfile(){
        Profile profile = new Profile();
        profile.setVisible(true);
    }
}
