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
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Ferdinand
 */
public class AddMembersController implements Initializable {
    
    ObservableList<Members> list = FXCollections.observableArrayList();

    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton btnSave;
    Connection conn;
    PreparedStatement pst=null;
    ResultSet rs= null;
    @FXML
    private TableView<Members> MembersTableview;
    @FXML
    private TableColumn<Members, String> idCol;
    @FXML
    private TableColumn<Members, String> nameCol;
    @FXML
    private TableColumn<Members, String> mobileCol;
    @FXML
    private TableColumn<Members, String> emailCol;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CheckConnection();
        initCol();
        loadData();
       
    }    

    @FXML
    private void SaveMember(ActionEvent event) {
        
        String m_Id = id.getText();
        String m_Name = name.getText();
        String m_Mobile = mobile.getText();
        String m_Email = email.getText();
        
         if (m_Id.isEmpty()||m_Name.isEmpty()||m_Mobile.isEmpty()||m_Email.isEmpty()){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Please Enter all fields!");
        alert.showAndWait();
        return;
        }
        
         try {
            String query = "INSERT INTO MEMBERS (mID, mName, mMobile, mEmail) VALUES (?, ?, ?, ?)";
            pst=conn.prepareStatement(query);
            pst.setString(1,m_Id);
            pst.setString(2,m_Name );
            pst.setString(3, m_Mobile);
            pst.setString(4, m_Email);
            
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Member added Successfully!");
        alert.showAndWait();
     
            pst.execute();
            pst.close();
            clearFields();
            
        } catch (Exception e) {
            System.err.println(e);
        }
        
        
    }
    
    
    public void clearFields(){
        id.clear();
        name.clear();
        mobile.clear();
        email.clear();
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
    
     
     public static class Members{
        private final SimpleStringProperty m_Id;
        private final SimpleStringProperty m_Name;
        private final SimpleStringProperty m_Mobile;
        private final SimpleStringProperty m_Email;

       
        
        Members(String Id, String name, String mobile, String email){
            this.m_Id= new SimpleStringProperty(Id);
            this.m_Name= new SimpleStringProperty(name);
            this.m_Mobile= new SimpleStringProperty(mobile);
            this.m_Email= new SimpleStringProperty(email);
        }

        public String getM_Id() {
            return m_Id.get();
        }

        public String getM_Name() {
            return m_Name.get();
        }

        public String getM_Mobile() {
            return m_Mobile.get();
        }

        public String getM_Email() {
            return m_Email.get();
        }
       
    }
     
      private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("m_Id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("m_Name"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("m_Mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("m_Email"));
    }

    private void loadData() {
            try {
            String query="Select * from MEMBERS";
            
            pst=conn.prepareStatement(query);
            rs=pst.executeQuery();
            
            while(rs.next()){
            String m_Id=rs.getString("mID");
            String m_Name=rs.getString("mName");
            String m_Mobile=rs.getString("mMobile");
            String m_Email=rs.getString("mEmail");
            
            list.add(new Members(m_Id, m_Name, m_Mobile, m_Email));
                
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            System.err.println(e);
        }
            MembersTableview.getItems().setAll(list);
    }
     
    
}
