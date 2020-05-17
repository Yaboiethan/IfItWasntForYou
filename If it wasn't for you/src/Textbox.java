import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Textbox extends JComponent
{
    //Variables
    private Image boxImg;
    private Image arrowImg;
    private String text = "";
    private Position myPos;
    private Position textOffset;
    public static int FILE_MIN = 0;
    public static int FILE_MAX;

    //TODO Font
    //private Font thisFont;

    //TODO Profile Pictures

    //Running the Textbox
    private static boolean textboxActive = false;
    private int curLine = 1;
    private String[] lines;
    private double buffer = 1;

    //Text Writing
    private String toWrite = "";
    private boolean isWriting = false;
    private final double TYPE_RATE = 0.12;
    private double textCounter;
    private int curChar;

    public Textbox(Image box, Position pos)
    {
        boxImg = box;
        arrowImg = GameObject.getResource("/Sprites/UI/Arrow.png");
        myPos = pos;
        textOffset = new Position(myPos.x + 10, myPos.y + 20);
        text = "";
        //Load the default text
        loadTextFile("errorText");
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(boxImg, myPos.x, myPos.y, null);
        if(text != null)
        {
            g.drawString(text, textOffset.x, textOffset.y);
        }
        //Show notification to move on
        if(text.equals(toWrite) && !isWriting && toWrite != "")
        {
            g.drawImage(arrowImg, myPos.x + boxImg.getWidth(null) / 2 - 25, 470, null);
        }
    }

    public void Update()
    {
        if(Player.getCurrentKey() == 'e' && textboxActive && !isWriting)
        {
            buffer += 0.5;
            //Go to next line or deactivate textbox
            if(curLine < lines.length)
            {
                if(buffer >= 1)
                {
                    text = "";
                    toWrite = lines[curLine];
                    curLine++;
                    buffer = 0;
                    isWriting = true;
                    curChar = 0;
                    textCounter = 1;
                }
            }
            else if (buffer >= 1)
            {
                unloadTextbox();
                buffer = 1;
            }
        }

        if(textboxActive && isWriting) //Text Typing
        {
            //Check if completed
            if(text.equals(toWrite))
            {
                isWriting = false;
                //TODO Display Arrow Notification
                return;
            }

            //Start with successful iteration
            if(textCounter >= TYPE_RATE)
            {
                try
                {
                    text += toWrite.toCharArray()[curChar];
                }
                catch (NullPointerException e) //try catch to prevent crashing during a nullpointer
                {
                    GUIManager.debugConsole.AddTextToView(e.toString());
                    unloadTextbox();
                }
                curChar++;
                textCounter = 0;
            }
            else
            {
                textCounter += 0.1;
            }
        }
    }

    //Sets and Gets
    public static boolean isTextboxActive() {
        return textboxActive;
    }

    //Practical Functions

    /*
    The loadTextbox function loads a txt file of dialog and activates the text box
    f must have the '.txt' extension in order to work.
     */
    public void loadTextbox(File f, int min, int max)
    {
        //Check for wrong or null file--.txt files only
        if(f == null || !f.getName().substring(f.getName().lastIndexOf('.') + 1).equals("txt"))
        {
            //Load error file
            String filePath = Textbox.class.getResource("/TextAssets/errorText.txt").getPath();
            filePath = filePath.replaceAll("%20", " ");
            f = new File(filePath);
        }

        //Run the textbox
        GUIManager.player.setMovement(false);
        curLine = 0;
        try
        {
            BufferedReader bufRead = new BufferedReader(new FileReader(f));
            lines = new String[max - min]; //Create fresh array
            String line = "";
            int ct = 0;
            while ((line = bufRead.readLine()) != null) //Add the lines
            {
                if(ct >= min)
                {
                    lines[ct] = line;
                }
                ct++;
            }
            bufRead.close();
        }
        catch (IOException e)
        {
            GUIManager.debugConsole.AddTextToView(e.toString());
        }
        textboxActive = true;
    }

    /*
    Used to pull file from resources. Needs to be called before loadTextbox is run every time to assure the internal text file
    is set properly before every run. The errorText.txt file is reloaded after every run of the textbox to easily point out errors
     */
    public File loadTextFile(String fileName)
    {
        String filePath = Textbox.class.getResource("/TextAssets/" + fileName + ".txt").getPath();
        filePath = filePath.replaceAll("%20", " ");
        File f = new File(filePath);

        FILE_MAX = 0;
        //Calculate FILE_MAX
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line = "";
            while((line = reader.readLine()) != null)
            {
                FILE_MAX++;
            }
        }
        catch (IOException e) //Something went wrong with reading the file, likely the file is not loaded properly (or at all)
        {
            GUIManager.debugConsole.AddTextToView(e.toString());
        }
        return f;
    }

    /*
    This method is run at the end of the textbox command to return movement to the player and 'disable' the textbox
    In addition, the errorText file is reloaded to errors can be pointed out easier
     */
    private void unloadTextbox()
    {
        textboxActive = false;
        GUIManager.player.setMovement(true);
        //Reload base file to spot errors
        loadTextFile("errorText");
    }
}
