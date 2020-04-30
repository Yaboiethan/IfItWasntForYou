import DEBUGCONSOLE.DebugConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Collider
{
    Rectangle2D col = new Rectangle();
    private GameObject thisObj;
    private boolean init = false;

    public Collider(GameObject g)
    {
        //Set up reference to attached object
        thisObj = g;
        //Set up collider size
        setCollider();
        //Add collider to GUIManager colliders list
        GUIManager.AddtoColliders(this);
    }

    public Collider(Rectangle2D bounds) //Empty Collider--static, non viewable colliders
    {
        thisObj = null;
        GUIManager.AddtoColliders(this);
        col = bounds;
    }

    /*
    Run this in the update method of each collider to check for collision within each of the colliders
    This method assumes the attached gameobject is a player object
     */
    public void UpdatePlayerCollider()
    {
        assert thisObj instanceof Player: "GameObject not of type: Player";
        //Should be able to successfully cast to player
        Player p = (Player) thisObj;
        int offsetH = thisObj.getSpriteSize().height - Player.getSpeed();
        int offsetW = (thisObj.getSpriteSize().width / 4) - Player.getSpeed();
        setCollider();
        for(Collider c: GUIManager.GetColliders())
        {
            if(!isSame(c)) //Make sure the objects aren't the same object
            {
                if(col.getBounds2D().intersects(c.getColObject().getBounds2D())) //Check if colliding
                {
                    if(getColObject().getMaxY() - offsetH == (c.getColObject().getMaxY() + 1)) //North
                    {
                        p.setMovement(false, Position.Direction.NORTH);
                        p.storeCollider(Position.Direction.NORTH, c);
                        break;
                    }
                    else if(getColObject().getMinY() + offsetH == (c.getColObject().getMinY())) //South
                    {
                        p.setMovement(false, Position.Direction.SOUTH);
                        p.storeCollider(Position.Direction.SOUTH, c);
                        break;
                    }
                    else if(getColObject().getMinX() + offsetW == (c.getColObject().getMaxX())) //West
                    {
                        p.setMovement(false, Position.Direction.WEST);
                        p.storeCollider(Position.Direction.WEST, c);
                        break;
                    }
                    else if(getColObject().getMaxX() - offsetW == (c.getColObject().getMinX() + 1)) //East
                    {
                        p.setMovement(false, Position.Direction.EAST);
                        p.storeCollider(Position.Direction.EAST, c);
                        break;
                    }
                }
            }
        }
        //Check if collisions need to be reset
        if(p.getFromStorage(Position.Direction.NORTH) != null && !col.intersects(p.getFromStorage(Position.Direction.NORTH).getColObject()))
        {
            p.setMovement(true, Position.Direction.NORTH);
            p.removeCollider(Position.Direction.NORTH);
        }
        if(p.getFromStorage(Position.Direction.EAST) != null && !col.intersects(p.getFromStorage(Position.Direction.EAST).getColObject()))
        {
            p.setMovement(true, Position.Direction.EAST);
            p.removeCollider(Position.Direction.EAST);
        }
        if(p.getFromStorage(Position.Direction.SOUTH) != null && !col.intersects(p.getFromStorage(Position.Direction.SOUTH).getColObject()))
        {
            p.setMovement(true, Position.Direction.SOUTH);
            p.removeCollider(Position.Direction.SOUTH);
        }
        if(p.getFromStorage(Position.Direction.WEST) != null && !col.intersects(p.getFromStorage(Position.Direction.WEST).getColObject()))
        {
            p.setMovement(true, Position.Direction.WEST);
            p.removeCollider(Position.Direction.WEST);
        }
    }

    public void UpdateCollider()
    {
        setCollider();
    }

    private void setCollider()
    {
        if(thisObj != null) //Make sure GameObject is filled
        {
            //Bounding box is based off of the first frame of animation
            if(!init)
            {
                col.setRect(thisObj.myPos.x - 1, thisObj.myPos.y - 1, thisObj.getSpriteSize().getWidth() + 1, thisObj.getSpriteSize().height + 1);
                init = true;
            }
            else
            {
                col.setRect(thisObj.myPos.x - 1, thisObj.myPos.y - 1, col.getWidth(), col.getHeight());
            }
        }
    }

    public void setSize(Rectangle2D rect)
    {
        col = rect;
    }

    public Rectangle2D getColObject()
    {
        setCollider();
        return col;
    }

    public boolean isSame(Collider c)
    {
        //Error, no GameObject attached
        if(thisObj == null || c.thisObj == null)
        {
            return false;
        }

        if(thisObj == c.thisObj)
        {
            return true;
        }
        return false;
    }

    public String toString()
    {
        return getColObject().getBounds2D().toString();
    }
}
