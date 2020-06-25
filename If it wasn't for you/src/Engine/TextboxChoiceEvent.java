package Engine;/*
This event signals to the textbox that this event needs to take place and sends the result of whatever choice was made
 */
import java.util.ArrayList;

public class TextboxChoiceEvent extends TextboxEvent
{
    //Other variables
    private String[] choices;
    private ArrayList<LoadedTextFile> choiceOutcomes = new ArrayList<LoadedTextFile>();

    public TextboxChoiceEvent(String[] c)
    {
        assert c.length <= 4: "Choice maximum is 4"; //Set maximum
        assert c.length >= 2: "Why have a choice with 1 answer?"; //Set minimum
        choices = c;
    }

    @Override
    public void Update()
    {
        super.Update();

        if(activated)
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
}
