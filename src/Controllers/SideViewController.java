/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Utils.Constants;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Ferdinand
 */
public class SideViewController implements Initializable {

    @FXML
    private JFXButton btnDashboard;
    @FXML
    private JFXButton btnBooks;
    @FXML
    private JFXButton btnMembers;
    @FXML
    private JFXButton btnSettings;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private JFXButton btnExit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      
    }    

    @FXML
    private void OpenDashboard(ActionEvent event) {
          switchPane(Constants.DASHBOARDVIEW);
    }


    @FXML
    private void OpenLogout(ActionEvent event) {
    }

    @FXML
    private void exit(ActionEvent event) {
    }
  
    @FXML
    private void OpenBooks(ActionEvent event) {
        switchPane(Constants.ADDBOOKS);
    }

    @FXML
    private void OpenMembers(ActionEvent event) {
         switchPane(Constants.ADDMEMBERS);
    }

    @FXML
    private void OpenSettings(ActionEvent event) {
    }
    
    
     private void switchPane(String pane){
        try {
            
        MainViewController.temporaryPane.getChildren().clear();
        StackPane pane2 = FXMLLoader.load(getClass().getResource(pane));
        ObservableList<Node> elements=pane2.getChildren();
        MainViewController.temporaryPane.getChildren().setAll(elements);
            
        } catch (Exception e) {
        }   
    }
    
}
