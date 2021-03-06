package Engine;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Collider
{
    Rectangle2D col = new Rectangle();
    protected GameObject thisObj;
    private boolean init = false;
    protected Collider other;

    public Collider(GameObject g) //Viewable colliders (eg: Buildings, NPCs, Trees)
    {
        //Set up reference to attached object
        thisObj = g;
        //Set up collider size
        setCollider();
        //Add collider to GUIManager colliders list
        GameRunner.addtoColliders(this);
    }

    public Collider(Rectangle2D bounds) //Empty Engine.Collider--static, non viewable colliders
    {
        thisObj = null;
        GameRunner.addtoColliders(this);
        col = bounds;
    }

    /*
    Run this in the update method of each collider to check for collision within each of the colliders
    This method assumes the attached gameobject is a player object
     */
    public void UpdatePlayerCollider()
    {
        assert thisObj instanceof Player: "Engine.GameObject not of type: Engine.Player";
        //Should be able to successfully cast to player
        Player p = (Player) thisObj;
        setCollider();
        for(Collider c: GameRunner.getColliders())
        {
            if(!isSame(c)) //Make sure the objects aren't the same object
            {
                if(col.getBounds2D().intersects(c.getColObject().getBounds2D())) //Check if colliding
                {
                    CollisionEnter((Player) thisObj, c);
                    c.CollisionEnter(this);
                }
            }
        }
        //Check if collisions need to be reset
        if(p.getFromStorage(Position.Direction.NORTH) != null && !col.intersects(p.getFromStorage(Position.Direction.NORTH).getColObject()))
        {
            p.getFromStorage(Position.Direction.NORTH).CollisionExit();
            CollisionExit(Position.Direction.NORTH);
        }
        if(p.getFromStorage(Position.Direction.EAST) != null && !col.intersects(p.getFromStorage(Position.Direction.EAST).getColObject()))
        {
            p.getFromStorage(Position.Direction.EAST).CollisionExit();
            CollisionExit(Position.Direction.EAST);
        }
        if(p.getFromStorage(Position.Direction.SOUTH) != null && !col.intersects(p.getFromStorage(Position.Direction.SOUTH).getColObject()))
        {
            p.getFromStorage(Position.Direction.SOUTH).CollisionExit();
            CollisionExit(Position.Direction.SOUTH);
        }
        if(p.getFromStorage(Position.Direction.WEST) != null && !col.intersects(p.getFromStorage(Position.Direction.WEST).getColObject()))
        {
            p.getFromStorage(Position.Direction.WEST).CollisionExit();
            CollisionExit(Position.Direction.WEST);
        }
    }

    public void UpdateCollider()
    {
        setCollider();
    }

    //Engine.Collider Events
    private void CollisionEnter(Player p, Collider c) //Meant for overriding in other Colliders
    {
        other = c;
        int offsetH = thisObj.getSpriteSize().height - Player.getSpeed();
        int offsetW = (thisObj.getSpriteSize().width / 4) - Player.getSpeed();
        boolean isTrigger = c instanceof TriggerCollider; //Make sure movement isn't stopped by TriggerColliders
        if(getColObject().getMaxY() - offsetH == (c.getColObject().getMaxY() + 1)) //North
        {
            if(!isTrigger)
            {
                p.setMovement(false, Position.Direction.NORTH);
            }
            p.storeCollider(Position.Direction.NORTH, c);
        }
        else if(getColObject().getMinY() + offsetH == (c.getColObject().getMinY())) //South
        {
            if(!isTrigger)
            {
                p.setMovement(false, Position.Direction.SOUTH);
            }
            p.storeCollider(Position.Direction.SOUTH, c);
        }
        else if(getColObject().getMinX() + offsetW == (c.getColObject().getMaxX())) //West
        {
            if(!isTrigger)
            {
                p.setMovement(false, Position.Direction.WEST);
            }
            p.storeCollider(Position.Direction.WEST, c);
        }
        else if(getColObject().getMaxX() - offsetW == (c.getColObject().getMinX() + 1)) //East
        {
            if(!isTrigger)
            {
                p.setMovement(false, Position.Direction.EAST);
            }
            p.storeCollider(Position.Direction.EAST, c);
        }
    }

    protected void CollisionEnter(Collider c)
    {
        other = c;
    }

    protected void CollisionExit(Position.Direction d)
    {
        Player p = (Player) thisObj;
        p.setMovement(true, d);
        p.removeCollider(d);
    }

    protected void CollisionExit() //Empty method for overriding
    {
        other = null;
    }

    protected void setCollider()
    {
        if(thisObj != null) //Make sure Engine.GameObject is filled
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
        setCollider(); //Update Engine.Collider
        return col;
    }

    public boolean isSame(Collider c)
    {
        //Error, no Engine.GameObject attached
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

    public Collider getOther()
    {
        return other;
    }
}
