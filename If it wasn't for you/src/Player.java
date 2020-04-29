/*
GameObjects in themselves cannot be instantiated, but are the foundation for which all rendered objects are built
The Player class is the vessel of the player's inputs, and respond directly to what they input
    - Movement
    - Interaction with environment
    - Animation
    - Collision
 */

import DEBUGCONSOLE.DebugConsole;

import javax.swing.*;
import java.awt.*;

public class Player extends GameObject
{
    //Variables
    public boolean isMoving;
    private static int speed = 2;
    char currentKey;
    Collider myCol;
    boolean mNorth = true;
    boolean mEast = true;
    boolean mSouth = true;
    boolean mWest = true;
    //Collider storage
    /*
    0 == NORTH
    1 == EAST
    2 == SOUTH
    3 == WEST
     */
    Collider[] touchedCols = new Collider[4];

    public Player(Position startingPos) //Fill constuctor
    {
        super();
        setPosition(startingPos);
        //TEMP--Set player sprite
        setSprite("/TestPlayer.jpg");
        setPreferredSize(getSpriteSize());
        myCol = new Collider(this);
    }

    public void Update()
    {
        if(isMoving)
        {
            switch(currentKey)
            {
                case 'w': //Move up
                    if(mNorth)
                    setPosition(myPos.x, myPos.y - speed);
                    break;

                case 'a': //Move Left
                    if(mWest)
                    setPosition(myPos.x - speed, myPos.y);
                    break;
                case 's': //Move up
                    if(mSouth)
                    setPosition(myPos.x, myPos.y + speed);
                    break;

                case 'd': //Move Left
                    if(mEast)
                    setPosition(myPos.x + speed, myPos.y);
                    break;
            }
        }
        myCol.UpdatePlayerCollider();
        repaint();
    }

    //Sets and Gets
    public void setMoving(boolean m, char key)
    {
        currentKey = key;
        isMoving = m;
    }

    public void setMovement(boolean b, Position.Direction dir)
    {
        switch(dir)
        {
            case NORTH:
                mNorth = b;
                break;

            case EAST:
                mEast = b;
                break;

            case SOUTH:
                mSouth = b;
                break;

            case WEST:
                mWest = b;
                break;

        }
    }

    public boolean getMovement(Position.Direction dir)
    {
        boolean toRet = false;
        switch(dir)
        {
            case NORTH:
                toRet = mNorth;
                break;

            case EAST:
                toRet = mEast;
                break;

            case SOUTH:
                toRet = mSouth;
                break;

            case WEST:
                toRet = mWest;
                break;

        }
        return toRet;
    }

    public static int getSpeed()
    {
        return speed;
    }

    public Collider getCollider()
    {
        return myCol;
    }

    //Overriding the base paintComponent to add collider command
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        //Add on the rectangle
        if(DebugConsole.SHOW_COLLIDERS)
        {
            g.setColor(Color.GREEN);
            Graphics2D g2d = (Graphics2D) g;
            g2d.draw(myCol.getColObject());
            g2d.dispose();
        }

        //Mouse Coordinates
        if(DebugConsole.SHOW_MOUSE_COORS)
        {
            g.setColor(Color.BLACK);
            g.drawString(GUIManager.GetMousePosition().toString(), GUIManager.GetMousePosition().x, GUIManager.GetMousePosition().y + 2);
        }
    }

    public void storeCollider(Position.Direction dir, Collider c)
    {
        switch(dir)
        {
            case NORTH:
                touchedCols[0] = c;
                break;

            case EAST:
                touchedCols[1] = c;
                break;

            case SOUTH:
                touchedCols[2] = c;
                break;

            case WEST:
                touchedCols[3] = c;
                break;
        }
    }

    public Collider getFromStorage(Position.Direction dir)
    {
        switch(dir)
        {
            case NORTH:
                return touchedCols[0];

            case EAST:
                return touchedCols[1];

            case SOUTH:
                return touchedCols[2];

            case WEST:
                return touchedCols[3];
        }
        return null;
    }

    public void removeCollider(Position.Direction dir)
    {
        switch(dir)
        {
            case NORTH:
                touchedCols[0] = null;
                break;

            case EAST:
                touchedCols[1] = null;
                break;

            case SOUTH:
                touchedCols[2] = null;
                break;

            case WEST:
                touchedCols[3] = null;
                break;
        }
    }
}
