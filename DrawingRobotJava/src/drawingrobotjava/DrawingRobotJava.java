package drawingrobotjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import dk.sdu.mmmi.rd1.edgedetect.*;
import dk.sdu.mmmi.rd1.robotcomm.*;
import java.util.Arrays;

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
        //Edgedetector tests
        EdgeDetector picture = new EdgeDetector("C:\\Users\\Mikke\\Desktop\\Smiley.PNG");
        int[][] magArray = picture.getMagnitudeArray();
        
        System.out.println(Arrays.deepToString(magArray));

        //Picture class tests
        
        //Picture smiley = new Picture("C:\\Users\\Mikke\\Desktop\\Smiley.PNG");
        //smiley.getImage();
        //System.out.println(smiley.height() + " x " + smiley.width());
        //smiley.show();
        
    }

}
