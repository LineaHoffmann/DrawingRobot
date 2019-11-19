package drawingrobotjava;

import dk.sdu.mmmi.rd1.edgedetect.EdgeDetector;

public class Gcode
{

    //Attributes
    public static String file;
    private static String gcode = "";
    private static int startDraw = 0;
    private static int endDraw = 0;
    private static int lengthDrawn = 0;

    public static String createGcode(String file)
    {
        //Make an instance of a picture from given file or URL, and make an int array with values from 0 to 255 of intensity of color
        EdgeDetector picture = new EdgeDetector(file);
        int[][] magArray = picture.getMagnitudeArray();

        //Remake the 2d int arry where only colors stronger than 128 is displayed into a boolean array
        boolean[][] boolArray = new boolean[magArray.length][magArray[0].length];
        for (int row = 0; row < magArray.length; row++)
        {
            for (int col = 0; col < magArray[row].length; col++)
            {
                if (magArray[row][col] > 128)
                {
                    boolArray[row][col] = true;
                } else
                {
                    boolArray[row][col] = false;
                }
            }
        }
        gcode = gcode.concat("M17;G28;G30;"); //Start (M17 enable stepmotor, G28 go to home, G30 go to drawing area (0,0))
        //Loop through the array
        for (int row = 0; row < boolArray.length; row++)
        {
            for (int col = 1; col < boolArray[0].length; col++) //The magnitudeArray method has makes the array 1 smaller on each edge, so we have to start at column 1
            {
                if (lengthDrawn > 3) //After drawing for this long
                {
                    gcode = gcode.concat("G12;"); //Add a pencilsharpener command and reset counter for length drawn
                    lengthDrawn = 0;
                }
                if (col == boolArray.length - 1) //If we are at the end of a line
                {
                    gcode = gcode.concat("G08 Z1;" + "G01 X0 Y" + row + ";"); //Add a commando to the gcode to go to start of next line
                    break;
                }
                if (!(boolArray[row][col] == boolArray[row][col - 1])) //If there is a change in value between one element and the next
                {
                    int drawLength = col; //The X coordinate (from left to right)
                    if (boolArray[row][col] == true) //If the change is going from false to true
                    {
                        gcode = gcode.concat("G08 Z0;" + "G01 X" + drawLength + "G01 Y" + row + ";"); //Add a gcode command that puts pen down and one that goes to the next change
                        startDraw = col; //Set the start of draw to calculate draw length

                    } else
                    {
                        gcode = gcode.concat("G08 Z1;" + "G01 X" + drawLength + "G01 Y" + row + ";"); //Else add a gcode command that puls the pen up and one that goes to the next change
                        endDraw = col; //Set the end of draw to calculate draw length
                    }
                    lengthDrawn += (startDraw - endDraw); //Calculate length drawn
                }
            }
        }
        gcode = gcode.concat("G28;M18;M00;"); //Stop (G28 go to home, M18 disable stepmotor, M00 stop all)
        return gcode;
    }

    public static void setFile(String file)
    {
        Gcode.file = file;
    }

    public static String getFile()
    {
        return file;
    }

    public static String getGcode()
    {
        return gcode;
    }

    public static void printBool(String file)
    {
        //Make an instance of a picture from given file or URL, and make an int array with values from 0 to 255 of intensity of color
        EdgeDetector picture = new EdgeDetector(file);
        int[][] magArray = picture.getMagnitudeArray();

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

    }
}

//        //Prints the magnitude array as a table
//            for (int row = 0; row < magArray.length; row++)
//        {
//            for (int col = 1; col < magArray[row].length; col++)
//            {
//                System.out.print(magArray[row][col] + "\t");
//            }
//            System.out.println();
//        }
//        //Print the boolean array as a table for easy understanding
//        for (int row = 0; row < boolArray.length; row++)
//        {
//            for (int col = 0; col < boolArray[0].length; col++)
//            {
//                System.out.print(boolArray[row][col] + "\t");
//            }
//            System.out.println();
//        // Jeg har omdannet int-arrayet til et boolean array for det i min optik er
//        // lettere at skrive Gkode der der går igennem linje for linje og sætter blyanten
//        // ned når arrayet er true og løfter blyanten igen når det bliver false
//        
//        //Todo:

