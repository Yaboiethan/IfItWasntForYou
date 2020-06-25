package Engine;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Textbox extends UIRenderable
{
    //Images
    private Image boxImg; //The actual texbox image
    private Image arrowImg;
    //Images--Selection Boxes
    private Image selectionBox;
    private Image highlightBox;

    //Text and positioning
    private String text = "";
    private Position textOffset;

    //TODO Font
    private Font thisFont = new Font("TimesRoman", Font.PLAIN, 18);

    //TODO Character Names

    //Running the Engine.Textbox
    private static boolean textboxActive = false;
    private static boolean canUse = true; //used to stop selecting in event
    public static boolean paused = false; //used to pause in event (pause or for movement)
    private int curLine = 1;
    private String[] lines;

    //Events
    private TextboxEvent curEvent;
    private int curOption = 0;

    //Text Writing
    private String toWrite = "";
    private boolean isWriting = false;
    private final double TYPE_RATE = 0.12;
    private double textCounter;
    private int curChar;

    public Textbox()
    {
        //Get various images
        boxImg = GameObject.getResource("/Sprites/UI/TestTextbox.jpg");
        arrowImg = GameObject.getResource("/Sprites/UI/Arrow.png");
        selectionBox = GameObject.getResource("/Sprites/UI/SelectionBox.jpg");
        highlightBox = GameObject.getResource("/Sprites/UI/HighlightBox.png");
        //Set the remaining variables
        myPos = new Position(0, SettingsMenu.getScreenResolution().height - boxImg.getHeight(null) - 50);
        textOffset = new Position(myPos.x + 10, myPos.y + 20);
        text = "";
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(paused)
        {
            return;
        }

        g.drawImage(boxImg, myPos.x, myPos.y, null);
        if(text != null)
        {
            g.setFont(thisFont);
            g.drawString(text, textOffset.x, textOffset.y);
        }
        //Show notification to move on
        if(text.equals(toWrite) && !isWriting)
        {
            g.drawImage(arrowImg, myPos.x + boxImg.getWidth(null) / 2 - 25, myPos.y + boxImg.getHeight(null) - arrowImg.getHeight(null) - 20, null);
        }
        //Show event stuff
        if(curEvent != null && !isWriting && curEvent instanceof TextboxChoiceEvent)
        {
            TextboxChoiceEvent choiceEvent = (TextboxChoiceEvent) curEvent;

            g.drawImage(selectionBox, myPos.x + boxImg.getWidth(null) - selectionBox.getWidth(null) - 10,
                    myPos.y - selectionBox.getHeight(null) - 5, null);
            //Draw all the options in text
            int yOffset = 20;
            for(int i = 0; i < choiceEvent.getAllOptions().length; i++) //Draw all options
            {
                g.drawString(choiceEvent.getAllOptions()[i], myPos.x + boxImg.getWidth(null) - selectionBox.getWidth(null) - 5,
                        myPos.y - selectionBox.getHeight(null) + yOffset);
                yOffset += (selectionBox.getHeight(null) - 5) / 4;
            }
            //Draw the highlight box
            g.drawImage(highlightBox, myPos.x + boxImg.getWidth(null) - selectionBox.getWidth(null) - 5,
                    myPos.y - selectionBox.getHeight(null) + (50 * curOption) - 5, null);
        }
    }

    public void paintOverride(Graphics g) //Used in Debug Log to display graphics regardless of active setting
    {
        super.paintComponent(g);
        g.drawImage(boxImg, myPos.x, myPos.y, null); //Draw the box
        g.drawImage(arrowImg, myPos.x + boxImg.getWidth(null) / 2 - 25, myPos.y + boxImg.getHeight(null) - arrowImg.getHeight(null) - 20, null);
        //Draw Options box
        g.drawImage(selectionBox, myPos.x + boxImg.getWidth(null) - selectionBox.getWidth(null) - 10,
                myPos.y - selectionBox.getHeight(null) - 5, null);
        //Draw the highlight box
        g.drawImage(highlightBox, myPos.x + boxImg.getWidth(null) - selectionBox.getWidth(null) - 5,
                myPos.y - selectionBox.getHeight(null) + (50 * curOption) - 5, null);
    }

    public void Update()
    {
        if(paused)
        {
            return;
        }

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
                    GameRunner.debugConsole.AddTextToView(e.toString() + " " + Arrays.toString(e.getStackTrace()));
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
        int ct = 0;

        GameRunner.player.setMovement(false);
        lines = new String[f.getMax() - f.getMin()];
        for(int i = 0; i < f.getMax(); i++)
        {
            if(i >= f.getMin() && i < f.getMax())
            {
                lines[ct] = f.getLine(i);
                ct++;
            }
        }
        //Ready to Write
        toWrite = lines[0];
        isWriting = true;
        textboxActive = true;
        revalidate();
        repaint();
    }

    @Override
    public void setPosition(Position p)
    {
        super.setPosition(p);
        myPos = new Position(0, SettingsMenu.getScreenResolution().height - boxImg.getHeight(null) - 50);
        textOffset = new Position(myPos.x + 10, myPos.y + 20);
        //Get how much to resize
        double sf = (double) (SettingsMenu.getScreenResolution().width - boxImg.getWidth(this)) / boxImg.getWidth(this);
        //Resize
        boxImg = ScaleHorizontally(boxImg, 0.9 + sf);
    }

    /*
        Used to pull file from resources. The errorText.txt file is reloaded after every run of the textbox to easily point out errors
         */
    public static InputStream getTextFile(String fName)
    {
        InputStream is = Textbox.class.getResourceAsStream("/TextAssets/" + fName + ".txt");
        return is;
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
    public void TriggerChoiceEvent(TextboxEvent event)
    {
        if(!(event instanceof TextboxChoiceEvent))
        {
            return;
        }

        //Stop the textbox
        canUse = false;
        //Display the box
        curEvent = event;
        curOption = 0;
    }

    public void changeCurOption(int add)
    {
        if(curOption - add < ((TextboxChoiceEvent) curEvent).getAllOptions().length && curOption - add >= 0)
        {
            curOption -= add;
        }
    }

    public int getChoice()
    {
        return curOption;
    }
}

/*
LoadedTextFiles are files with minimums and maximums so that the Engine.Textbox can easily access this information from the
event itself, rather than from an Engine.NPC or something.
The text is stored in its entirety
 */
class LoadedTextFile
{
    //Variables
    private String[] mFile;
    private int min;
    private int max;

    public LoadedTextFile(InputStream f, int mi, int ma) //Literal file input
    {
        mFile = loadTextFileStream(f);
        min = mi;
        max = ma;
    }

    //Practical Functions
    public String[] loadTextFileStream(InputStream is)
    {
        BufferedReader bfRead = new BufferedReader(new InputStreamReader(is));
        ArrayList<String> lines = new ArrayList<>();
        String line = "";
        try
        {
            while ((line = bfRead.readLine()) != null)
            {
                lines.add(line);
            }
            bfRead.close();
            is.close();
        }
        catch (IOException e)
        {
            GameRunner.debugConsole.AddTextToView("Could not load text file" + e.getStackTrace());
        }
        String[] toRet = new String[lines.size()];
        for(int i = 0; i < lines.size(); i++)
        {
            toRet[i] = lines.get(i);
        }
        return toRet;
    }
    //Sets and Gets
    public void setMax()
    {
        max = mFile.length;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getLine(int index)
    {
        if(index < 0 || index > mFile.length)
        {
            return null;
        }
        return mFile[index];
    }
}