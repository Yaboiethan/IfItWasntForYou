/*
Trigger Colliders act like normal colliders, but don't stop the player's movements
Instead, these colliders trigger events, either on a button press or automatically
 */
import java.awt.geom.Rectangle2D;

public class TriggerCollider extends Collider
{
    //Variables
    private boolean useButtonPress;
    private boolean hitButton;
    private boolean inTrigger = false;
    private int toReset = 0;
    private GameObject other;
    public TriggerCollider(Rectangle2D bounds, boolean b) //Default
    {
        super(bounds);
        useButtonPress = b;
        GUIManager.addtoColliders(this);
    }

    //Overridden methods
    @Override
    protected void CollisionEnter(GameObject other)
    {
        inTrigger = true;
        this.other = other;
        //Add button press event
        if(useButtonPress)
        {
            if(Player.getCurrentKey() == 'e' && !hitButton)
            {
                hitButton = true;
                Player.resetCurrentKey();
            }

            //Reset trigger
            if(hitButton)
            {
                if(toReset >= 2)
                {
                    toReset = 0;
                    hitButton = false;
                }
                else
                {
                    toReset += 1;
                }
            }
        }
    }

    @Override
    protected void CollisionExit()
    {
        hitButton = false;
        other = null;
        inTrigger = false;
    }

    //Various gets
    public boolean getInTrigger()
    {
        //Check for button presses
        if(useButtonPress)
        {
            boolean toRet = inTrigger && hitButton; //Only send flag once
            hitButton = false;
            return toRet;
        }
        return inTrigger;
    }

    public GameObject getOther()
    {
        return other;
    }
}
