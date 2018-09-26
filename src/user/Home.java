package user;

import database.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shakil
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    
    Connection connection;
    ResultSet resultSet;
    PreparedStatement preparedStatement;
    
    String checkIn;
    String checkOut;
    String roomType;
    String bedType;
    String searchQuery;
    int roomPrice;
    int totalPrice;
    
    public Home() {
        super("Room Reservation");
        initComponents();
        connection = DatabaseConnector.connectDatabase();
        setRoomAndBedType();//comboBox
    }
    
  
    public void setRoomAndBedType(){
        
        try{
            String queryRoom = "select distinct roomtype from price";
            String queryBed = "select distinct bedtype from price";
            
            preparedStatement = connection.prepareStatement(queryRoom);
            ResultSet resultRoom = preparedStatement.executeQuery();
            
            preparedStatement = connection.prepareStatement(queryBed);
            ResultSet resultBed = preparedStatement.executeQuery();
            
            
            while(resultRoom.next()&&resultBed.next()){
                String room  = resultRoom.getString("roomtype");
                String bed = resultBed.getString("bedtype");
                
                jComboRoom.addItem(room);
                jComboBed.addItem(bed);
            }
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null, exception);
        }
    }
    
    public void searchRoom(){
        
        String queryPrice;
        String queryDays;
        int totalDays = 0;
        
        queryPrice = "select price from price where roomtype='"+(String)jComboRoom.getSelectedItem()+"' and bedtype='"+
                (String)jComboBed.getSelectedItem()+"'";
        
        roomType = (String)jComboRoom.getSelectedItem();
        bedType = (String)jComboBed.getSelectedItem();
        
        searchQuery = "select roomno,price from rooms inner join price on rooms.roomtype=price.roomtype and rooms.bedtype=price.bedtype and available = 0 where rooms.roomtype='"+
                roomType+"' and rooms.bedType='"+bedType+"'";
        //fetching available rooms,showing in table
        try{
            preparedStatement = connection.prepareStatement(searchQuery);
            resultSet = preparedStatement.executeQuery();
            jTableRooms.setModel(DbUtils.resultSetToTableModel(resultSet));
            
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null, exception);
        }
        //getting room price
        try{
            preparedStatement = connection.prepareStatement(queryPrice);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                roomPrice = Integer.parseInt(resultSet.getString("price"));
            }
            
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
                jTotalPrice.setText(Integer.toString(totalPrice));//showing total price
                
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
          
    }
    
    public void roomReservation(){
        String insertQuery = "insert into RESERVATION values(to_date('"+checkIn+"','dd-mm-yyyy'),to_date('"+checkOut+"','dd-mm-yyyy'),'"+Login.username+"','"+
                jRoomNo.getText()+"',"+totalPrice+")";
        
        String updateQuery = "Update rooms set available = 1 where roomno ='"+jRoomNo.getText()+"'";
        
        String currentDateQuery = "Select to_date('"+checkIn+"','dd-mm-yyyy')-sysdate from dual";
        
        int checkValidity = 0;
     
        
        //inserting into reservation table and updating available rooms
        try{
            
            preparedStatement = connection.prepareStatement(currentDateQuery);
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next())
                checkValidity = resultSet.getInt(1);
            
            preparedStatement.close();
            
            if(checkValidity>=0){
                
                //inserting
                preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.executeQuery();
                preparedStatement.close();

                //updating
                preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.executeQuery();
                preparedStatement.close();

                setVisible(false);
                ReservationDetails reservationDetails = new ReservationDetails();
                reservationDetails.setVisible(true);
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
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jComboRoom = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jYearOut = new javax.swing.JTextField();
        jMonthIn = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBed = new javax.swing.JComboBox();
        jDayIn = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jDayOut = new javax.swing.JTextField();
        jMonthOut = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jRoomNo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jYearIn = new javax.swing.JTextField();
        btnSearchRoom = new javax.swing.JButton();
        btnReservation = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTotalPrice = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableRooms = new javax.swing.JTable();
        menuBar = new javax.swing.JPanel();
        btnLogout = new javax.swing.JButton();
        btnHome = new javax.swing.JButton();
        btnCheck = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnProfile = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 2), "Reservation Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        jComboRoom.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboRoomActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel1.setText("Type of Room");

        jYearOut.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jYearOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jYearOutActionPerformed(evt);
            }
        });

        jMonthIn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jMonthIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMonthInActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Year(yyyy)");

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel2.setText("Bedding Type");

        jComboBed.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBedActionPerformed(evt);
            }
        });

        jDayIn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jDayIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDayInActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Month(mm)");

        jDayOut.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jDayOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDayOutActionPerformed(evt);
            }
        });

        jMonthOut.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jMonthOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMonthOutActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel4.setText("Check In");

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel3.setText("Enter Room");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Day(dd)");

        jRoomNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRoomNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRoomNoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel5.setText("Check Out");

        jYearIn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jYearIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jYearInActionPerformed(evt);
            }
        });

        btnSearchRoom.setBackground(new java.awt.Color(255, 255, 255));
        btnSearchRoom.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        btnSearchRoom.setText("Search Room");
        btnSearchRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchRoomActionPerformed(evt);
            }
        });

        btnReservation.setBackground(new java.awt.Color(255, 255, 255));
        btnReservation.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        btnReservation.setText("Confirm Reservation");
        btnReservation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservationActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel9.setText("Total Cost");

        jTotalPrice.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jRoomNo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                        .addComponent(jTotalPrice, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnSearchRoom)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jComboRoom, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBed, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jDayIn)
                                        .addComponent(jDayOut))
                                    .addGap(25, 25, 25)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jMonthIn)
                                        .addComponent(jMonthOut))))
                            .addGap(25, 25, 25)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jYearIn)
                                .addComponent(jYearOut)))
                        .addComponent(btnReservation)))
                .addGap(70, 70, 70))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel6)
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jDayIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jMonthIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jYearIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel7)))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jDayOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jMonthOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jYearOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(btnSearchRoom)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRoomNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(25, 25, 25)
                .addComponent(btnReservation)
                .addGap(40, 40, 40))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Available Rooms", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 18), new java.awt.Color(0, 102, 102))); // NOI18N

        jTableRooms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Room No", "Price"
            }
        ));
        jScrollPane2.setViewportView(jTableRooms);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        menuBar.setBackground(new java.awt.Color(0, 51, 102));

        btnLogout.setBackground(new java.awt.Color(204, 51, 0));
        btnLogout.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnHome.setBackground(new java.awt.Color(255, 255, 255));
        btnHome.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        btnHome.setText("Home");
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        btnCheck.setBackground(new java.awt.Color(255, 255, 255));
        btnCheck.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        btnCheck.setText("Reservation Details");
        btnCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 255, 255));
        btnCancel.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        btnCancel.setText("Cancel Reservation");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnProfile.setBackground(new java.awt.Color(255, 255, 255));
        btnProfile.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        btnProfile.setText("Profile");
        btnProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuBarLayout = new javax.swing.GroupLayout(menuBar);
        menuBar.setLayout(menuBarLayout);
        menuBarLayout.setHorizontalGroup(
            menuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuBarLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnHome)
                .addGap(30, 30, 30)
                .addComponent(btnProfile)
                .addGap(30, 30, 30)
                .addComponent(btnCheck)
                .addGap(30, 30, 30)
                .addComponent(btnCancel)
                .addGap(30, 30, 30)
                .addComponent(btnLogout)
                .addGap(30, 30, 30))
        );
        menuBarLayout.setVerticalGroup(
            menuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuBarLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(menuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogout)
                    .addComponent(btnCancel)
                    .addComponent(btnCheck)
                    .addComponent(btnProfile)
                    .addComponent(btnHome))
                .addGap(2, 2, 2))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(menuBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(menuBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jDayInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDayInActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jDayInActionPerformed

    private void jYearInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jYearInActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jYearInActionPerformed

    private void jMonthInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMonthInActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMonthInActionPerformed

    private void jDayOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDayOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jDayOutActionPerformed

    private void jMonthOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMonthOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMonthOutActionPerformed

    private void jYearOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jYearOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jYearOutActionPerformed

    private void jComboBedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBedActionPerformed

    private void jRoomNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRoomNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRoomNoActionPerformed

    private void btnSearchRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchRoomActionPerformed
        // TODO add your handling code here:
        searchRoom();
    }//GEN-LAST:event_btnSearchRoomActionPerformed

    private void jComboRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboRoomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboRoomActionPerformed

    private void btnReservationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservationActionPerformed
        // TODO add your handling code here:
        roomReservation();
    }//GEN-LAST:event_btnReservationActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        ButtonPress.btnLogOut();
        
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        ButtonPress.btnCancelReservation();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        ButtonPress.btnReservationDetails();
        
    }//GEN-LAST:event_btnCheckActionPerformed

    private void btnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        ButtonPress.btnProfile();
    }//GEN-LAST:event_btnProfileActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        setVisible(true);
    }//GEN-LAST:event_btnHomeActionPerformed

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCheck;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnProfile;
    private javax.swing.JButton btnReservation;
    private javax.swing.JButton btnSearchRoom;
    private javax.swing.JComboBox jComboBed;
    private javax.swing.JComboBox jComboRoom;
    private javax.swing.JTextField jDayIn;
    private javax.swing.JTextField jDayOut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jMonthIn;
    private javax.swing.JTextField jMonthOut;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jRoomNo;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableRooms;
    private javax.swing.JTextField jTotalPrice;
    private javax.swing.JTextField jYearIn;
    private javax.swing.JTextField jYearOut;
    private javax.swing.JPanel menuBar;
    // End of variables declaration//GEN-END:variables
}
