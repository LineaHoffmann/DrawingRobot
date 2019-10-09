
package drawingrobotjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
        System.out.println("Hej Jonas");
        System.out.println("Hej alle!");
        System.out.println("dette er production branch");
        System.out.println("phil tester");
        System.out.println("Jonas er IKKE SEJ!");
        System.out.println("GIT ER IKKE NEMT");
        
        System.out.println("Linea Branch");
    }
    
}
