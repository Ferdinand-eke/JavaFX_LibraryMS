/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DbUtils.SqlConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Ferdinand
 */
public class DashBoardController implements Initializable {

    @FXML
    private JFXTextField book_Id_Input;
    @FXML
    private Label bkName;
    @FXML
    private Label bkAuthor;
    @FXML
    private Text bkStatus;
      
    Connection conn;
    PreparedStatement pst=null;
    ResultSet rs= null;
    @FXML
    private Label memberName;
    @FXML
    private Label memberMobile;
    @FXML
    private JFXTextField member_id_Input;
    @FXML
    private JFXButton btnIssue;
    
  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CheckConnection();
    }    

    @FXML
    private void LoadBookInfo(ActionEvent event) {
        String id = book_Id_Input.getText();    
        String query="Select * from BOOK WHERE id='" + id +"'";
        try {
            
            pst=conn.prepareStatement(query);
            rs=pst.executeQuery();
            Boolean flag =false;
            
            while(rs.next()){
                String bName = rs.getString("Title");
                String bAuthor = rs.getString("Author");
                Boolean bStatus = rs.getBoolean("isAvail");
                flag = true;
                
                bkName.setText(bName);
                bkAuthor.setText(bAuthor);
                String Status = (bStatus)? "Not Available" : "Available";
                bkStatus.setText(Status);
                
            }
            if (!flag){
                bkName.setText("No Such Book Available");
                bkAuthor.setText("No Such Author Available");
                bkStatus.setText("");
            }
                pst.close();
                rs.close();
            
        } catch (SQLException e) {
            Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
    
     public void CheckConnection(){
        conn=SqlConnection.DbConnector();
        
        if (conn==null){
            System.out.println("Connection Not Successfull");
            System.exit(1);
        }else{
            System.out.println("Connection Successfull");
        }
    }

    @FXML
    private void LoadMemberInfo(ActionEvent event) {
        
        
        String id = member_id_Input.getText();    
        String query="Select * from MEMBERS WHERE mID='" + id +"'";
        try {
            
            pst=conn.prepareStatement(query);
            rs=pst.executeQuery();
            Boolean flag = false;
            
            while(rs.next()){
                String m_Name = rs.getString("mName");
                String m_Mobile = rs.getString("mMobile");
       
               
                
                memberName.setText(m_Name);
                memberMobile.setText(m_Mobile);
                flag = true;
                
            }
            if (!flag){
                memberName.setText("No Such Member Available");
                memberMobile.setText("No such Info Available");
            }
                pst.close();
                rs.close();
            
        } catch (SQLException e) {
            Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }

    @FXML
    private void IssueBook(ActionEvent event) {
        String bookId = book_Id_Input.getText();
        String memberId = member_id_Input.getText();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to issue the book "+ bkName.getText() + "\n"
                               + "to " + memberName.getText() + "?");
        Optional<ButtonType>response = alert.showAndWait();
        if(response.get()==ButtonType.OK){
            try {
                String query= "insert into ISSUE(id, mID) values (?,?)";
                pst=conn.prepareStatement(query);
                pst.setString(1,bookId);
                pst.setString(2,memberId);
                pst.execute();
                pst.close();
                
                String query2 ="Update BOOK set isAvail=false where id='"+ bookId +"'";
                Connection conn1 = SqlConnection.DbConnector();
                PreparedStatement pst1=conn1.prepareStatement(query2);
                pst1.execute();
                pst1.close();
                if(pst.execute(query) && pst1.execute(query2)){
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Successful");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Book issued Successfully!");
                    alert1.showAndWait();
                }else{
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Failed");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Issued Operation Failed!");
                    alert1.showAndWait();    
                }
                
                
            } catch (Exception e) {
                System.err.println(e);
            }
        }else{
//            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
//                    alert1.setTitle("Cancelled");
//                    alert1.setHeaderText(null);
//                    alert1.setContentText("Isuue operation cancelled!");
//                    alert1.showAndWait();
        }
        
    }
}
