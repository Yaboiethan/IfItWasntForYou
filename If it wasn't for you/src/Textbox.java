import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Textbox extends JComponent
{
    //Images
    private Image boxImg; //The actual texbox image
    private Image arrowImg;
    //Images--Selection Boxes
    private Image selectionBox;
    private Image highlightBox;

    //Text and positioning
    private String text = "";
    private Position myPos;
    private Position textOffset;

    //TODO Font
    private Font thisFont = new Font("TimesRoman", Font.PLAIN, 18);

    //TODO Character Names

    //Running the Textbox
    private static boolean textboxActive = false;
    private static boolean canUse = true; //used to stop selecting in event
    private int curLine = 1;
    private String[] lines;

    //Events
    private TextboxChoiceEvent curEvent;
    private int curOption = 0;

    //Text Writing
    private String toWrite = "";
    private boolean isWriting = false;
    private final double TYPE_RATE = 0.12;
    private double textCounter;
    private int curChar;

    public Textbox(Image box, Position pos)
    {
        boxImg = box;
        //Get various images
        arrowImg = GameObject.getResource("/Sprites/UI/Arrow.png");
        selectionBox = GameObject.getResource("/Sprites/UI/SelectionBox.jpg");
        highlightBox = GameObject.getResource("/Sprites/UI/HighlightBox.png");
        //Set the remaining variables
        myPos = pos;
        textOffset = new Position(myPos.x + 10, myPos.y + 20);
        text = "";
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(boxImg, myPos.x, myPos.y, null);
        if(text != null)
        {
            g.setFont(thisFont);
            g.drawString(text, textOffset.x, textOffset.y);
        }
        //Show notification to move on
        if(text.equals(toWrite) && !isWriting)
        {
            g.drawImage(arrowImg, myPos.x + boxImg.getWidth(null) / 2 - 25, myPos.y + boxImg.getHeight(null) / 3, null);
        }
        //Show event stuff
        if(curEvent != null && !isWriting)
        {
            g.drawImage(selectionBox, myPos.x + boxImg.getWidth(null) - selectionBox.getWidth(null) - 10,
                    myPos.y - selectionBox.getHeight(null) - 5, null);
            //Draw all the options in text
            int yOffset = 0;
            for(int i = 0; i < curEvent.getAllOptions().length; i++) //Draw all options
            {
                g.drawString(curEvent.getAllOptions()[i], myPos.x + boxImg.getWidth(null) - selectionBox.getWidth(null) - 5,
                        420 - selectionBox.getHeight(null) + yOffset);
                yOffset += (selectionBox.getHeight(null) - 5) / 4;
            }
            //Draw the highlight box
            g.drawImage(highlightBox, myPos.x + boxImg.getWidth(null) - selectionBox.getWidth(null) - 5,
                    myPos.y - selectionBox.getHeight(null) + (50 * curOption) - 5, null);
        }
    }

    public void paintOverride(Graphics g) //Used in UIManager to display graphics regardless of active setting
    {
        super.paintComponent(g);
        g.drawImage(boxImg, myPos.x, myPos.y, null); //Draw the box
        g.drawImage(arrowImg, myPos.x + boxImg.getWidth(null) / 2 - 25, myPos.y + boxImg.getHeight(null) / 3, null); //Draw arrow
        //Draw Options box
        g.drawImage(selectionBox, myPos.x + boxImg.getWidth(null) - selectionBox.getWidth(null) - 10,
                myPos.y - selectionBox.getHeight(null) - 5, null);
        //Draw the highlight box
        g.drawImage(highlightBox, myPos.x + boxImg.getWidth(null) - selectionBox.getWidth(null) - 5,
                myPos.y - selectionBox.getHeight(null) + (50 * curOption) - 5, null);
    }

    public void Update()
    {
        if(GameRunner.hitEThisFrame && textboxActive && !isWriting)
        {
            if(canUse)
            {
                //Go to next line or deactivate textbox
                if(curLine < lines.length)
                {
                    text = "";
                    toWrite = lines[curLine];
                    curLine++;
                    isWriting = true;
                    curChar = 0;
                    textCounter = 1;
                }
                else
                {
                    unloadTextbox();
                }
            }
        }

        if(textboxActive && isWriting) //Text Typing
        {
            //Check if completed
            if(text.equals(toWrite))
            {
                isWriting = false;
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
                    GameRunner.debugConsole.AddTextToView(e.toString());
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

    public int getCurLine()
    {
        return curLine;
    }

    public int getCurOption() {
        return curOption;
    }

    //Practical Functions

    /*
    The loadTextbox function loads a txt file of dialog and activates the text box
    f must have the '.txt' extension in order to work.
     */
    public void loadTextbox(LoadedTextFile f)
    {
        //Reset some things
        resetTextbox();
        toWrite = "";

        //Run the textbox
        GameRunner.player.setMovement(false);
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new FileReader(f.getmFile()));
            lines = new String[f.getMax() - f.getMin()]; //Create fresh array
            String line = "";
            int ct = 0;
            int i = 0;
            while ((line = reader.readLine()) != null) //Add the lines
            {
                if(ct >= f.getMin() && ct <= f.getMax())
                {
                    if(i < lines.length)
                    {
                        lines[i] = line;
                        i++;
                    }
                }
                ct++;
            }
            reader.close();
            //Ready the text
            toWrite = lines[0];
            isWriting = true;
        }
        catch (IOException e)
        {
            GameRunner.debugConsole.AddTextToView(e.toString());
        }
        textboxActive = true;
        revalidate();
        repaint();
    }

    public static int getFileMax(File f)
    {
        int toRet = 0;
        //Check if f is null/unreadable
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(f));
            String line = "";
            while((line = reader.readLine()) != null)
            {
                toRet++;
            }
            reader.close();
        }
        catch (IOException e) //Something went wrong with reading the file, likely the file is not loaded properly (or at all)
        {
            GameRunner.debugConsole.AddTextToView(e.toString());
        }
        return toRet;
    }

    /*
    Used to pull file from resources. The errorText.txt file is reloaded after every run of the textbox to easily point out errors
     */
    public static File getTextFile(String fName)
    {
        String filePath = Textbox.class.getResource("/TextAssets/" + fName + ".txt").getPath();
        filePath = filePath.replaceAll("%20", " ");
        File f = new File(filePath);
        return f;
    }

    /*
    This method is run at the end of the textbox command to return movement to the player and 'disable' the textbox
    In addition, the errorText file is reloaded to errors can be pointed out easier
     */
    private void unloadTextbox()
    {
        resetTextbox();
        GameRunner.player.setMovement(true);
    }

    private void resetTextbox()
    {
        curEvent = null;
        canUse = true;
        lines = null;
        //isWriting = false;
        curLine = 1;
        textboxActive = false;
        curChar = 0;
        text = "";
    }

    //TEXTBOX EVENTS
    public void TriggerChoiceEvent(TextboxChoiceEvent event)
    {
        //Stop the textbox
        canUse = false;
        //Display the box
        curEvent = event;
        curOption = 0;
    }

    public void changeCurOption(int add)
    {
        if(curOption - add < curEvent.getAllOptions().length && curOption - add >= 0)
        {
            curOption -= add;
        }
    }
}
