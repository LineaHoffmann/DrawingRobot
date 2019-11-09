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
        System.out.println(Gcode.createGcode("https://images.media-allrecipes.com/userphotos/50x50/3876426.jpg"));
    }
}