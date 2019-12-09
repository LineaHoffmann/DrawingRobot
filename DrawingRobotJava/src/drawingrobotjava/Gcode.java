package drawingrobotjava;

import dk.sdu.mmmi.rd1.edgedetect.EdgeDetector;

public class Gcode
{

    //Attributes
    private String file;
    private String gcode = "";
    private int startDraw = 0;
    private int endDraw = 0;
    private int lengthDrawn = 0;
    private boolean[][] boolArray;
    private int drawThreshold = 128;

    //Constructor
    //Make an instance of a picture, with the the default value for when the intesity of a pixel is strong enough to be drawn
    public Gcode(String file)
    {
        this.file = file;

        EdgeDetector picture = new EdgeDetector(file);
        int[][] magArray = picture.getMagnitudeArray();

        boolArray = new boolean[magArray.length][magArray[0].length];
        for (int row = 0; row < magArray.length; row++)
        {
            for (int col = 0; col < magArray[row].length; col++)
            {
                if (magArray[row][col] < drawThreshold)
                {
                    boolArray[row][col] = true;
                } else
                {
                    boolArray[row][col] = false;
                }
            }
        }
    }

    //Make an instance of a picture, with a given value for when the intesity of a pixel is strong enough to be drawn
    public Gcode(String file, int drawThreshold)
    {
        this.file = file;
        this.drawThreshold = drawThreshold;

        EdgeDetector picture = new EdgeDetector(file);
        int[][] magArray = picture.getMagnitudeArray();

        boolArray = new boolean[magArray.length][magArray[0].length];
        for (int row = 0; row < magArray.length; row++)
        {
            for (int col = 0; col < magArray[row].length; col++)
            {
                if (magArray[row][col] < drawThreshold)
                {
                    boolArray[row][col] = true;
                } else
                {
                    boolArray[row][col] = false;
                }
            }
        }
    }

    //Methods
    //Creates gcode from the boolean array (make sure this is updated if file is changed)
    public void createGcode()
    {
        gcode = ""; //'reset' the string so the concat() method does not just add on to an existing gcode
        gcode = gcode.concat("M17;G28;G30;G01 X0 Y0;"); //Start (M17 enable stepmotor, G28 go to home, G30 go to drawing area (0,0) then sharpen the pencil)
        //Loop through the array
        for (int row = 0; row < boolArray.length; row++)
        {
            for (int col = 1; col < boolArray[0].length; col++) //The magnitudeArray method has made the array 1 smaller on each edge, so we have to start at column 1
            {

//                if (lengthDrawn > 100) //After drawing for this long
//                {
//                    gcode = gcode.concat("G12;"); //Add a pencilsharpener command and reset counter for length drawn
//                    lengthDrawn = 0;
//                }
                if (col == boolArray.length - 1) //If we are at the end of a line
                {
                    if (col % 5 == 0)
                    {
                        gcode = gcode.concat("G12");
                    }

                    gcode = gcode.concat("G08 Z10;" + "G01 X0 Y" + row + ";"); //Add a commando to the gcode to go to start of next line
                    break;
                }
                if (!(boolArray[row][col] == boolArray[row][col - 1])) //If there is a change in value between one element and the next
                {
                    int drawLength = col; //The X coordinate (from left to right)
                    if (boolArray[row][col] == true) //If the change is going from false to true
                    {
                        gcode = gcode.concat("G08 Z10;" + "G01 X" + drawLength + " Y" + row + ";"); //Add a gcode command that puts pen down and one that goes to the next change
                        //startDraw = col; //Set the start of draw to calculate draw length

                    } else
                    {
                        gcode = gcode.concat("G08 Z0;" + "G01 X" + drawLength + " Y" + row + ";"); //Else add a gcode command that puls the pen up and one that goes to the next change
                        //endDraw = col; //Set the end of draw to calculate draw length
                    }
                    //lengthDrawn += (startDraw - endDraw); //Calculate length drawn
                }
            }
        }
        gcode = gcode.concat("G08 Z50;M18;M00;"); //Stop (Lift pencil, M18 disable stepmotor, M00 stop all)
    }

    //Clears the old object and recreate the boolean array from a new file
    public void setFile(String file)
    {
        this.file = file;
        //Remake the 2d int array where only colors stronger than 128 is displayed into a boolean array
        EdgeDetector picture = new EdgeDetector(file);
        int[][] magArray = picture.getMagnitudeArray();

        //Make sure the object is clear
        gcode = "";
        startDraw = 0;
        endDraw = 0;
        lengthDrawn = 0;

        boolArray = new boolean[magArray.length][magArray[0].length];
        for (int row = 0;
                row < magArray.length;
                row++)
        {
            for (int col = 0; col < magArray[row].length; col++)
            {
                if (magArray[row][col] < drawThreshold)
                {
                    boolArray[row][col] = true;
                } else
                {
                    boolArray[row][col] = false;
                }
            }
        }
    }

    public void setdrawThreshold(int drawThreshold)
    {
        this.drawThreshold = drawThreshold;
    }

    public String getFile()
    {
        return file;
    }

    public String getGcode()
    {
        return gcode;
    }

    public int getLengthDrawn()
    {
        return lengthDrawn;
    }

    public boolean[][] getBoolArray()
    {
        return boolArray;
    }

    public void printBoolArray()
    //Iterates the boolean array to print in a table for easy understanding of when the robot will draw
    {
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
