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
    private boolean isActive = true;

    public TriggerCollider(Rectangle2D bounds, boolean press) //Default
    {
        super(bounds);
        useButtonPress = press;
        GameRunner.addtoColliders(this);
    }

    //Overridden methods

    @Override
    public void UpdateCollider()
    {
        super.UpdateCollider();
        if(!isActive)
        {
            return; //Stop Updating
        }
        hitButton = false;
        if(useButtonPress && inTrigger)
        {
            if(GameRunner.hitEThisFrame)
            {
                hitButton = true;
            }
        }
    }

    @Override
    protected void CollisionEnter(Collider other)
    {
        if(this.other == other)
        {
            return;
        }
        inTrigger = true;
        this.other = other;
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
        if(Textbox.isTextboxActive() || !isActive) //If the box is on, the answer is no
        {
            return false;
        }
        //Check for button presses
        if(useButtonPress)
        {
            return inTrigger && hitButton;
        }
        return inTrigger;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        if(active)
        {
            hitButton = false;
        }
        isActive = active;
    }
}
