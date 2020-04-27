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
import java.awt.geom.Rectangle2D;

public class Player extends GameObject
{
    //Variables
    public boolean isMoving;
    private int speed = 2;
    char currentKey;
    Collider myCol;
    boolean mNorth = true;
    boolean mEast = true;
    boolean mSouth = true;
    boolean mWest = true;

    public Player(Position startingPos) //Fill constuctor
    {
        super();
        //TEMP--Set player sprite
        setSprite("/TestPlayer.jpg");
        setPreferredSize(getSpriteSize());
        setPosition(startingPos);
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
            Rectangle2D rect = myCol.getColObject();
            g.drawRect(((int) rect.getX()), ((int) rect.getY()),((int) rect.getWidth()), ((int) rect.getHeight()));
        }
    }
}
