/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Utils.Constants;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXToolbar;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Ferdinand
 */
public class MainViewController implements Initializable {

    @FXML
    private JFXToolbar toolbar;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private JFXDrawer drawer;
    
    public static AnchorPane temporaryPane;
    @FXML
    private StackPane mainStackPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        temporaryPane = contentPane;
        initDrawer();
    }    
    
    private void initDrawer(){
    
        try {
            VBox menu = FXMLLoader.load(getClass().getResource(Constants.SIDEMENUVIEW));
            drawer.setSidePane(menu);
            
                 HamburgerBackArrowBasicTransition task =new HamburgerBackArrowBasicTransition(hamburger);
                 task.setRate(-1);
        
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
        task.setRate(task.getRate()* -1);
        task.play();
        
        if (drawer.isClosed()) {
                    drawer.open();
                    //drawer.setMinWidth(150);
                } else {
                   // task.setRate(-1);
                    drawer.close();
                    //drawer.setMinWidth(0);
                }
        
    });
            
        } catch (Exception e) {
             Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, e);
        }
        
   
    }
  

    
    
    
}
