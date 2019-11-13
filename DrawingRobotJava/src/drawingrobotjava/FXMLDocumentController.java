/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawingrobotjava;

import dk.sdu.mmmi.rd1.robotcomm.RobotClient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


/**
 *
 * @author Bruger
 */
public class FXMLDocumentController implements Initializable
{
    
    @FXML
    private Button isConnectedButton; //not in use
    @FXML
    private Label textLabel;
      
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
//        RobotClient robot1;
//        robot1 = new  RobotClient("127.0.0.1", 3333);
//        robot1.connect();
//        System.out.println(robot1.isConnected());
//        robot1.disconnect();
    }    

    @FXML
    private void handleIsConnectedButton(ActionEvent event)
    {
        System.out.println("IsConnected clicked");
        textLabel.setText("Clicked");
    }
    
}
