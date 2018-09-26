/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edit;

import database.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import user.Login;
import user.ReservationDetails;

/**
 *
 * @author Shakil
 */
public class EditReservation extends javax.swing.JFrame {

    /**
     * Creates new form EditReservation
     */
    
    Connection connection;
    ResultSet resultSet;
    PreparedStatement preparedStatement;
    
    String checkIn;
    String checkOut;
    String roomNo;
    int totalPrice;
    int roomPrice;
    
    public EditReservation() {
        
        super("Edit Reservation");
        initComponents();
        connection = DatabaseConnector.connectDatabase();
        getReservedRooms();
    }
    private void getReservedRooms(){
        
        String reserveQuery = "select reservation.roomno,roomtype,bedtype,(to_char(checkIn,'dd-mm-yyyy')) as CHECKIN,"
                + "(to_char(Checkout,'dd-mm-yyyy')) as CHECKOUT"
                + ",cost from reservation,rooms where username='"
                +Login.username+"' and reservation.roomno=rooms.roomno";
        
        //fetching available rooms,showing in table
        try{
            preparedStatement = connection.prepareStatement(reserveQuery);
            resultSet = preparedStatement.executeQuery();
            
            jTableRerservedRooms.setModel(DbUtils.resultSetToTableModel(resultSet));
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null, exception);
        }
        
    }
    public void back(){
        setVisible(false);
        ReservationDetails reservationDetails = new ReservationDetails();
        reservationDetails.setVisible(true);
    }
    
    public void updateReservation(){
        String queryPrice;
        String queryDays;
        String queryUpdate;
        
        int totalDays = 0;
        boolean validUpdate = false;
        roomNo = jRoom.getText();
        queryPrice = "select price from price inner join rooms on rooms.roomtype = price.roomtype and rooms.bedtype=price.bedtype and roomno='"+roomNo+"'";
        
        try{
            preparedStatement = connection.prepareStatement(queryPrice);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next())
                roomPrice= Integer.parseInt(resultSet.getString(1));
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null, exception);
            
        }
        
         //finding total days
        checkIn = jDayIn.getText()+"-"+jMonthIn.getText()+"-"+jYearIn.getText();
        checkOut = jDayOut.getText()+"-"+jMonthOut.getText()+"-"+jYearOut.getText();
        
        queryDays = "select TO_date('"+checkOut+"','dd-mm-yyyy')-TO_date('"+checkIn+"','dd-mm-yyyy') from dual";
        
        try{
            preparedStatement = connection.prepareStatement(queryDays);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next())
                totalDays= Integer.parseInt(resultSet.getString(1));
            
            if(totalDays == 0)
                totalDays = 1;
            
            if(totalDays>0){
                
                totalPrice = roomPrice*totalDays;
                validUpdate = true;
                                
            }
                
            else if(totalDays<0){
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Insert dates correctly");
                setVisible(true);
            } 
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null, exception);
        }
        if(validUpdate){
            String currentDateQuery = "Select to_date('"+checkIn+"','dd-mm-yyyy')-sysdate from dual";
        
            int checkValidity = 0;

            queryUpdate = "Update reservation set checkin=to_date('"+checkIn+"','dd-mm-yyyy'),checkout=to_date('"+checkOut+"','dd-mm-yyyy'),cost="+totalPrice+
                    " where roomno='"+roomNo+"'";

            try{
                preparedStatement = connection.prepareStatement(currentDateQuery);
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next())
                    checkValidity = resultSet.getInt(1);

                preparedStatement.close();

                if(checkValidity>=0){
                    preparedStatement = connection.prepareStatement(queryUpdate);
                    preparedStatement.executeQuery();

                    JOptionPane.showMessageDialog(null, "Reservation Updated");

                    preparedStatement.close();

                    back();
                }
                else{
                    setVisible(false);
                    JOptionPane.showMessageDialog(null, "Enter date correctly");
                    setVisible(true);
                }

            }
            catch(Exception exception){
                JOptionPane.showMessageDialog(null, exception);
            }
        }
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableRerservedRooms = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jMonthIn = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jDayIn = new javax.swing.JTextField();
        jYearIn = new javax.swing.JTextField();
        jMonthOut = new javax.swing.JTextField();
        jYearOut = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jDayOut = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jRoom = new javax.swing.JTextField();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableRerservedRooms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Room No", "Room Type", "Bedding Type", "Check In", "Check Out", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableRerservedRooms);

        jLabel1.setText("To change room reserve room from 'Room Reservation' option.");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 2), "Edit Reservation", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 18), new java.awt.Color(0, 102, 51))); // NOI18N

        jMonthIn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Day(dd)");

        btnUpdate.setBackground(new java.awt.Color(255, 255, 255));
        btnUpdate.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(0, 153, 153));
        btnUpdate.setText("Update Reservation");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Month(mm)");

        jDayIn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jYearIn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jMonthOut.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jMonthOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMonthOutActionPerformed(evt);
            }
        });

        jYearOut.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Year(yyyy)");

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel4.setText("New Checkin");

        jDayOut.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel5.setText("New Checkout");

        btnBack.setBackground(new java.awt.Color(255, 255, 255));
        btnBack.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        btnBack.setForeground(new java.awt.Color(204, 0, 0));
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel2.setText("Enter Room Numeber");

        jRoom.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(70, 70, 70)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jDayIn)
                                    .addComponent(jDayOut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(70, 70, 70)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jMonthIn)
                                    .addComponent(jMonthOut, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(70, 70, 70))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnBack)
                                .addGap(48, 48, 48)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUpdate)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jYearOut, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(82, 82, 82)))
                            .addComponent(jYearIn, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jDayIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jMonthIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jYearIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jYearOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDayOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jMonthOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnBack))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(204, 204, 204)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMonthOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMonthOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMonthOutActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateReservation();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        back();
    }//GEN-LAST:event_btnBackActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditReservation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditReservation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditReservation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditReservation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditReservation().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JTextField jDayIn;
    private javax.swing.JTextField jDayOut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField jMonthIn;
    private javax.swing.JTextField jMonthOut;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jRoom;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    protected javax.swing.JTable jTableRerservedRooms;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jYearIn;
    private javax.swing.JTextField jYearOut;
    // End of variables declaration//GEN-END:variables
}
