package drawingrobotjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import dk.sdu.mmmi.rd1.edgedetect.*;
import dk.sdu.mmmi.rd1.robotcomm.*;

public class DrawingRobotJava extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        // instantiere ny robotclient, og opsætter TCp/IP connection
        RobotClient robot1;
        robot1 = new RobotClient("192.168.1.11", 3333);
        robot1.connect();
        System.out.println(robot1.isConnected());
        //sender gkoden
        Gcode picture = new Gcode("C:\\Users\\Mikke\\Dropbox\\SDU - Dropbox\\Semester projekt 1\\Billede af Christian 200x200.jpg");
        picture.createGcode();
        robot1.write(picture.getGcode());
        picture.printBoolArray();

        System.out.println(picture.getGcode());

        launch(args);
        robot1.disconnect();
//https://images.media-allrecipes.com/userphotos/50x50/3876426.jpg
    }
}
