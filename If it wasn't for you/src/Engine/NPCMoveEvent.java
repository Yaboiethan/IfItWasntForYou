package Engine;

/*
NPCMoveEvents are events that prompt a specific Engine.NPC to move in a specific direction at a specific distance
The textbox is interrupted, and resumes when the Engine.NPC is done moving
 */
public class NPCMoveEvent extends TextboxEvent
{
    //TODO Engine.Textbox pausing toggability?
    private NPC thisNPC;
    private Position.Direction dir;
    private int moveAmt;

    private boolean moved; //Completion
    private boolean inMovement; //Progress

    public NPCMoveEvent(NPC n, Position.Direction d, int m)
    {
        thisNPC = n;
        dir = d;
        moveAmt = m;
    }

    @Override
    public void Update()
    {
        super.Update();
        if(activated && !moved && !inMovement)
        {
            inMovement = true;
            thisNPC.moveIn(dir, moveAmt);
        }

        //Check if done
        if(inMovement)
        {
            //Pause textbox
            Textbox.paused = true;
            if(!thisNPC.getMoving()) //Check if done
            {
                moved = true;
                inMovement = false;
                Textbox.paused = false;
            }
        }
    }

    public boolean isNPCMoving()
    {
        return inMovement;
    }
}
