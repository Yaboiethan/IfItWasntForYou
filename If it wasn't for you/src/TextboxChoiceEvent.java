/*
TextboxChoiceEvents are events that trigger on certain lines and can change the line when prompted to do so.
This event signals to the textbox that this event needs to take place and sends the result of whatever choice was made
 */
import java.io.File;
import java.util.ArrayList;

public class TextboxChoiceEvent
{
    //Reference to textbox to not call GameFrame.uiManager.getTextbox() all the time
    private Textbox textbox;

    //Other variables
    private String[] choices;
    private ArrayList<LoadedTextFile> choiceOutcomes = new ArrayList<LoadedTextFile>();
    private int activationLine; //Used to trigger event
    private boolean activated;
    private double buffer;
    private boolean isDone;

    public TextboxChoiceEvent(String[] c, int activation)
    {
        assert c.length <= 4: "Choice maximum is 4"; //Set maximum
        assert c.length >= 2: "Why have a choice with 1 answer?"; //Set minimum
        textbox = GameFrame.uiManager.getTextbox();
        choices = c;
        activationLine = activation;
    }

    public void Update()
    {
        if(!activated)
        {
            if(activationLine != 0 && textbox.getCurLine() == activationLine)
            {
                try
                {
                    //Make sure there are as many choice outcomes as choices
                    if(choices.length != choiceOutcomes.size())
                    {
                        throw new Exception("Choice amount is not equal to Choice Outcome amount");
                    }
                    //Trigger the event
                    textbox.TriggerChoiceEvent(this);
                    activated = true;
                }
                catch (Exception e)
                {
                    GameRunner.debugConsole.AddTextToView(e.toString());
                }
            }
        }
        else //The event is active
        {
            if(GameRunner.hitEThisFrame)
            {
                //Load new file after quickly unloading
                textbox.loadTextbox(choiceOutcomes.get(textbox.getCurOption()));
                isDone = true;
            }

            switch(Player.getCurrentKey())
            {
                case 'W': //Up selection
                    buffer += 0.2;
                    if(buffer >= 1.5)
                    {
                        Player.resetCurrentKey();
                        textbox.changeCurOption(1);
                        buffer = 0;
                    }
                    break;

                case 'S': //Down selection
                    buffer += 0.2;
                    if(buffer >= 1.5)
                    {
                        Player.resetCurrentKey();
                        textbox.changeCurOption(-1);
                        buffer = 0;
                    }
                    break;
            }
        }
    }

    public String[] getAllOptions()
    {
        return choices;
    }

    public void addChoiceOutcome(LoadedTextFile f)
    {
        choiceOutcomes.add(f);
    }

    public boolean getIfDone()
    {
        return isDone;
    }
}

/*
LoadedTextFiles are files with minimums and maximums so that the Textbox can easily access this information from the
event itself, rather than from an NPC or something.
 */
class LoadedTextFile
{
    //Variables
    private File mFile;
    private int min;
    private int max;
    public LoadedTextFile(String name, int mi, int ma) //String file input
    {
        mFile = Textbox.getTextFile(name);
        min = mi;
        max = ma;
    }

    public LoadedTextFile(File f, int mi, int ma) //Literal file input
    {
        mFile = f;
        min = mi;
        max = ma;
    }

    public void setMax()
    {
        max = Textbox.getFileMax(mFile);
    }

    public File getmFile() {
        return mFile;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String toString()
    {
        return "File: " + getmFile().getPath() + "  MIN: " + min + "  MAX: " + max;
    }
}
