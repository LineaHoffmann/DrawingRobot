package drawingrobotjava;

import dk.sdu.mmmi.rd1.edgedetect.EdgeDetector;

public class Gcode2
{

    //Attributes
    public String file;
    private String gcode = "";
    private int startDraw = 0;
    private int endDraw = 0;
    private int lengthDrawn = 0;
    private int[][] magArray;
    private boolean[][] boolArray;

    //Constructor
    public Gcode2(String file)
    {
        this.file = file;

        EdgeDetector picture = new EdgeDetector(file);
        magArray = picture.getMagnitudeArray();

        boolArray = new boolean[magArray.length][magArray[0].length];
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
    }

    //Methods
    //Creates gcode from the boolean array (make sure this is updated if file is changed)
    public void createGcode()
    {
        gcode = gcode.concat("M17;G28;G30;G01 X0 Y0;G12;"); //Start (M17 enable stepmotor, G28 go to home, G30 go to drawing area (0,0) then sharpen the pencil)
        //Loop through the array
        for (int row = 0; row < boolArray.length; row++)
        {
            for (int col = 1; col < boolArray[0].length; col++) //The magnitudeArray method has makes the array 1 smaller on each edge, so we have to start at column 1
            {
                if (lengthDrawn > 400) //After drawing for this long
                {
                    gcode = gcode.concat("G12;"); //Add a pencilsharpener command and reset counter for length drawn
                    lengthDrawn = 0;
                }
                if (col == boolArray.length - 1) //If we are at the end of a line
                {
                    gcode = gcode.concat("G08 Z10;" + "G01 X0 Y" + row + ";"); //Add a commando to the gcode to go to start of next line
                    break;
                }
                if (!(boolArray[row][col] == boolArray[row][col - 1])) //If there is a change in value between one element and the next
                {
                    int drawLength = col; //The X coordinate (from left to right)
                    if (boolArray[row][col] == true) //If the change is going from false to true
                    {
                        gcode = gcode.concat("G08 Z0;" + "G01 X" + drawLength + " Y" + row + ";"); //Add a gcode command that puts pen down and one that goes to the next change
                        startDraw = col; //Set the start of draw to calculate draw length

                    } else
                    {
                        gcode = gcode.concat("G08 Z10;" + "G01 X" + drawLength + " Y" + row + ";"); //Else add a gcode command that puls the pen up and one that goes to the next change
                        endDraw = col; //Set the end of draw to calculate draw length
                    }
                    lengthDrawn += (startDraw - endDraw); //Calculate length drawn
                }
            }
        }
        gcode = gcode.concat("G08 Z50;M18;M00;"); //Stop (Lift pencil, M18 disable stepmotor, M00 stop all)
    }

    public void setFile(String file)
    {
        this.file = file;
    }

    //Sets the magnitude array again (use if file has been changed)
    public void setMagArray()
    {
        //Make an instance of a picture from given file or URL, and make an int array with values from 0 to 255 of intensity of color
        EdgeDetector picture = new EdgeDetector(file);
        magArray = picture.getMagnitudeArray();
    }

    //Sets the boolean array (use if magnitude array has been changed)
    public void setBoolArray()
    {
        //Remake the 2d int arry where only colors stronger than 128 is displayed into a boolean array
        boolArray = new boolean[magArray.length][magArray[0].length];
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

}
