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
        //Make an instance of a picture from a filepath, and make an int array with values from 0 to 255 of intensity of color
        EdgeDetector picture = new EdgeDetector("https://images.media-allrecipes.com/userphotos/50x50/3876426.jpg");
        int[][] magArray = picture.getMagnitudeArray();

        //Prints the magnitude array as a table
/*      for (int row = 0; row < magArray.length; row++)
        {
            for (int col = 1; col < magArray[row].length; col++)
            {
                System.out.print(magArray[row][col] + "\t");
            }
            System.out.println();
        }
         */

        //Remake the 2d int arry where only colors stronger than 128 is displayed into a boolean array
        boolean[][] boolArray = new boolean[magArray.length][magArray[0].length];
        for (int row = 0; row < magArray.length; row++)
        {
            for (int col = 0; col < magArray[row].length; col++)
            {
                if (magArray[row][col] < 128)
                {
                    boolArray[row][col] = true;
                } else
                {
                    boolArray[row][col] = false;
                }
            }
        }

        //Print the boolean array as a table for easy understanding
        for (int row = 0; row < boolArray.length; row++)
        {
            for (int col = 0; col < boolArray[0].length; col++)
            {
                System.out.print(boolArray[row][col] + "\t");
            }
            System.out.println();
        }

        //Loop through the array
        String gcode = "";
        for (int row = 0; row < boolArray.length; row++)
        {
            for (int col = 1; col < boolArray[0].length; col++) //The magnitudeArray method has makes the array 1 smaller on each edge, so we have to start at column 1
            {
                if (col == boolArray.length - 1) //If we are at the end of a line, add a commando to the gcode to go to start of next line
                {
                    gcode = gcode.concat("G081;" + "G010" + row + ";");
                    break;
                }
                if (!(boolArray[row][col] == boolArray[row][col - 1])) //If there is a change in value between one element and the next
                {
                    int drawLength = col;
                    if (boolArray[row][col] == false) //If the change is going from false to true
                    {
                        gcode = gcode.concat("G080;" + "G010" + drawLength + ";"); //Add a gcode command that puts pen down and one that goes to the next change

                    } else
                    {
                        gcode = gcode.concat("G081;" + "G010" + drawLength + ";"); //Else add a gcode command that puts pen down and one that goes to the next change
                    }
                }
            }
        }
        System.out.println(gcode);
        
        
        // Jeg har omdannet int-arrayet til et boolean array for det i min optik er
        // lettere at skrive Gkode der der går igennem linje for linje og sætter blyanten
        // ned når arrayet er true og løfter blyanten igen når det bliver false

        //System.out.println(drawLength); //er fra starten af linjen
        //OBS Out of bounds hvis col2 = 0 (fix eller er det okay med col2 = 1?)
        //Jeg tror også at lige nu begynder den først at tegne, når der er en linje med noget at tegne
    }

}
