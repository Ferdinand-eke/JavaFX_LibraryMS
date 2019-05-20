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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Ferdinand
 */
public class AddBooksController implements Initializable {

    ObservableList<Book> list = FXCollections.observableArrayList();
    
    @FXML
    private JFXButton SaveBtn;
    @FXML
    private JFXButton CancelBtn;
    @FXML
    private JFXTextField Title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField Author;
    @FXML
    private JFXTextField Publisher;
    Connection conn;
    PreparedStatement pst=null;
    ResultSet rs= null;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Book> BkTableview;
    @FXML
    private TableColumn<Book, String> idCol;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> publisherCol;
    @FXML
    private TableColumn<Book, Boolean> availabilityCol;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      CheckConnection();
      CheckData();
      initCol();
      loadData(); 
    }    
    
     

    @FXML
    private void AddBook(ActionEvent event) {
        String bookID = id.getText();
        String bookName = Title.getText();
        String bookAuthor = Author.getText();
        String bookPublisher = Publisher.getText();
        
         if (bookID.isEmpty()||bookAuthor.isEmpty()||bookName.isEmpty()||bookPublisher.isEmpty()){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Please Enter all fields!");
        alert.showAndWait();
        return;
        }
        
         try {
            String query = "INSERT INTO BOOK (id, Title, Author, Publisher, isAvail) VALUES (?, ?, ?, ?, ?)";
            pst=conn.prepareStatement(query);
            pst.setString(1,bookID);
            pst.setString(2,bookName );
            pst.setString(3, bookAuthor);
            pst.setString(4, bookPublisher);
            pst.setString(5, "true");
            
            
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Book added Successfully!");
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
        Title.clear();
        Author.clear();
        Publisher.clear();
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
    
    public void CheckData(){
        try {
            String query="Select Title from BOOK";
            
            pst=conn.prepareStatement(query);
            rs=pst.executeQuery();
            
            while(rs.next()){
            String Titlex=rs.getString("Title");
            System.out.println(Titlex);
                
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    
    public static class Book{
        private final SimpleStringProperty id;
        private final SimpleStringProperty Title;
        private final SimpleStringProperty Author;
        private final SimpleStringProperty Publisher;
        private final SimpleBooleanProperty Availability;

       
        
        Book(String id, String title, String author, String publisher, Boolean avail){
            this.id= new SimpleStringProperty(id);
            this.Title= new SimpleStringProperty(title);
            this.Author= new SimpleStringProperty(author);
            this.Publisher= new SimpleStringProperty(publisher);
            this.Availability= new SimpleBooleanProperty(avail);
        }
    
         public String getId() {
            return id.get();
        }

        public String getTitle() {
            return Title.get();
        }

        public String getAuthor() {
            return Author.get();
        }

        public String getPublisher() {
            return Publisher.get();
        }

        public Boolean getAvailability() {
            return Availability.get();
        }
        
        
    }
    
     private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("avail"));
    }
     
     private void loadData() {
            try {
            String query="Select * from BOOK";
            
            pst=conn.prepareStatement(query);
            rs=pst.executeQuery();
            
            while(rs.next()){
            String id=rs.getString("id");
            String Titlex=rs.getString("Title");
            String author=rs.getString("Author");
            String publisher=rs.getString("Publisher");
            Boolean avail=rs.getBoolean("isAvail");
            
            list.add(new Book(id, Titlex, author, publisher, avail));
                
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            System.err.println(e);
        }
            BkTableview.getItems().setAll(list);
    }
    
 }



