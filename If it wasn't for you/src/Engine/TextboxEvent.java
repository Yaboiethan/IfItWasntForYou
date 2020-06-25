package Engine;

/*
TextboxEvents are events that trigger on certain lines and can change the line when prompted to do so.
This base class cannot be instantiated by itself, but its more specific subclasses can, this just handles general event
triggering.
 */
public abstract class TextboxEvent
{
    //Reference to textbox to not call Engine.GameFrame.uiManager.getTextbox() all the time
    protected Textbox textbox = GamePanel.uiManager.getTextbox();
    protected int activationLine = 0; //Used to trigger event
    protected boolean activated;
    protected double buffer;
    protected boolean isDone;

    public void Update()
    {
        if(!activated)
        {
            if(activationLine != 0 && textbox.getCurLine() == activationLine)
            {
                //Trigger the event
                textbox.TriggerChoiceEvent(this);
                activated = true;
                GameRunner.hitEThisFrame = false;
            }
        }
    }

    public void setActivationLine(int l)
    {
        activationLine = l;
    }

    public boolean getIfDone()
    {
        return isDone;
    }
}