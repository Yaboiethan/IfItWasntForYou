import DEBUGCONSOLE.DebugConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Collider
{
    Rectangle2D col = new Rectangle();
    GameObject thisObj;

    public Collider(GameObject g)
    {
        //Set up reference to attached object
        thisObj = g;
        //Set up collider size
        setCollider();
        //Add collider to GUIManager colliders list
        GUIManager.AddtoColliders(this);
    }

    /*
    Run this in the update method of each collider to check for collision within each of the colliders
    This method assumes the attached gameobject is a player object
     */
    public void UpdatePlayerCollider()
    {
        setCollider();
        assert thisObj instanceof Player: "GameObject not of type: Player";
        //Should be able to successfully cast to player
        Player p = (Player) thisObj;
        for(Collider c: GUIManager.GetColliders())
        {
            if(col.intersects(c.getColObject()) && !isSame(c))
            {
                System.out.println("HIT");
                break;
            }
        }
        //Map Bounds
        if(thisObj.myPos.y + thisObj.getSpriteSize().height + 2 <= 18) //North
        {
            p.setMovement(false, Position.Direction.NORTH);
        }
        else
        {
            p.setMovement(true, Position.Direction.NORTH);
        }

        if(thisObj.myPos.y + thisObj.getSpriteSize().height + 2 >= 480) //South
        {
            p.setMovement(false, Position.Direction.SOUTH);
        }
        else
        {
            p.setMovement(true, Position.Direction.SOUTH);
        }

        if(thisObj.myPos.x + thisObj.getSpriteSize().width + 2 >= 490) //East
        {
            p.setMovement(false, Position.Direction.EAST);
        }
        else
        {
            p.setMovement(true, Position.Direction.EAST);
        }

        if(thisObj.myPos.x + thisObj.getSpriteSize().width + 2 <= 18) //West
        {
            p.setMovement(false, Position.Direction.WEST);
        }
        else
        {
            p.setMovement(true, Position.Direction.WEST);
        }
    }

    public void UpdateCollider()
    {
        setCollider();
    }

    private void setCollider()
    {
        col.setRect(thisObj.myPos.x, thisObj.myPos.y, thisObj.getSpriteSize().width, thisObj.getSpriteSize().height);
    }

    public Rectangle2D getColObject()
    {
        return col;
    }

    public boolean isSame(Collider c)
    {
        if(thisObj == c.thisObj)
        {
            return true;
        }
        return false;
    }
}
