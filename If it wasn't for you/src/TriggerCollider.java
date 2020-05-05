/*
Trigger Colliders act like normal colliders, but don't stop the player's movements
Instead, these colliders trigger events, either on a button press or automatically
 */
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TriggerCollider extends Collider
{
    //Variables
    private boolean useButtonPress;
    private boolean inTrigger = false;

    public TriggerCollider(Rectangle2D bounds, boolean b) //Default
    {
        super(bounds);
        useButtonPress = b;
        GUIManager.AddtoColliders(this);
    }

    //Overridden methods
    @Override
    protected void CollisionEnter()
    {
        inTrigger = true;
    }

    @Override
    protected void CollisionExit() {
        inTrigger = false;
    }

    //Various gets
    public boolean getInTrigger()
    {
        return inTrigger;
    }
}
