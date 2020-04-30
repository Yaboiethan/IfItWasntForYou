/*
GameObjects in themselves cannot be instantiated, but are the foundation for which all rendered objects are built
The Player class is the vessel of the player's inputs, and respond directly to what they input
    - Movement
    - Interaction with environment
    - Animation
    - Collision
 */

import DEBUGCONSOLE.DebugConsole;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Player extends GameObject
{
    //Variables
    public boolean isMoving;
    private static int speed = 2;
    private char currentKey;
    private Collider myCol;
    private boolean mNorth = true;
    private boolean mEast = true;
    private boolean mSouth = true;
    private boolean mWest = true;
    private Animator anim;
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
        InitializeAnimator();
        ScaleSprite(1.6);
        setPreferredSize(getSpriteSize());
        //Set up components
        myCol = new Collider(this);
    }

    public void InitializeAnimator()
    {
        String resourcePath = "/Placeholder/Player/";
        //Initialize Object
        anim = new Animator(this, "Player");
        //Anim 0-- IDLE
        Animation idleS = new Animation(1, "Idle_S", 0);
        idleS.setFrame(getResource(resourcePath + anim.GetName()+ "_Walking_S_0.png"), 0);
        anim.AddAnimation(idleS);
        //Anim 1-- WALKING SOUTH
        Animation walkingS = new Animation(4, "Walking_S", 1.1);
        //For loop to set up the frames
        walkingS.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingS.getName() + "_1.png"), 0);
        walkingS.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingS.getName() + "_0.png"), 1);
        walkingS.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingS.getName() + "_2.png"), 2);
        walkingS.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingS.getName() + "_0.png"), 3);
        anim.AddAnimation(walkingS);
        //Anim 2-- IDLE NORTH
        Animation idleN = new Animation(1, "Idle_N", 0);
        idleN.setFrame(getResource(resourcePath + anim.GetName() + "_Walking_N_0.png"), 0);
        anim.AddAnimation(idleN);
        //Anim 3-- WALKING NORTH
        Animation walkingN = new Animation(4, "Walking_N", 1.1);
        //For loop to set up the frames
        walkingN.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingN.getName() + "_1.png"), 0);
        walkingN.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingN.getName() + "_0.png"), 1);
        walkingN.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingN.getName() + "_2.png"), 2);
        walkingN.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingN.getName() + "_0.png"), 3);
        anim.AddAnimation(walkingN);
        //Anim 4-- IDLE EAST
        Animation idleE = new Animation(1, "Idle_E", 0);
        idleE.setFrame(getResource(resourcePath + anim.GetName() + "_Walking_E_0.png"), 0);
        anim.AddAnimation(idleE);
        //Anim 5- WALKING EAST
        Animation walkingE = new Animation(4, "Walking_E", 1.1);
        //For loop to set up the frames
        walkingE.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingE.getName() + "_1.png"), 0);
        walkingE.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingE.getName() + "_0.png"), 1);
        walkingE.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingE.getName() + "_2.png"), 2);
        walkingE.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingE.getName() + "_0.png"), 3);
        anim.AddAnimation(walkingE);
        //Anim 6-- IDLE WEST
        Animation idleW = new Animation(1, "Idle_W", 0);
        idleW.setFrame(getResource(resourcePath + anim.GetName() + "_Walking_W_0.png"), 0);
        anim.AddAnimation(idleW);
        //Anim 7- WALKING WEST
        Animation walkingW = new Animation(4, "Walking_W", 1.1);
        //For loop to set up the frames
        walkingW.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingW.getName() + "_1.png"), 0);
        walkingW.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingW.getName() + "_0.png"), 1);
        walkingW.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingW.getName() + "_2.png"), 2);
        walkingW.setFrame(getResource(resourcePath + anim.GetName() + "_" + walkingW.getName() + "_0.png"), 3);
        anim.AddAnimation(walkingW);

        //Set the first frame of the object
        setSprite(anim.getCurAnim().getFrame(0));
        anim.SetCurrentAnimation("Idle");
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
                case 's': //Move down
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
        anim.UpdateAnimator();
        repaint();
    }

    //Sets and Gets
    public void setMoving(boolean m, char key)
    {
        currentKey = key;
        isMoving = m;
        //Animation Stuff
        switch(key)
        {
            case 'w':
                if(isMoving)
                {
                    anim.SetCurrentAnimation("Walking_N");
                }
                else
                {
                    anim.SetCurrentAnimation("Idle_N");
                }
                break;

            case 'a':
                if(isMoving)
                {
                    anim.SetCurrentAnimation("Walking_W");
                }
                else
                {
                    anim.SetCurrentAnimation("Idle_W");
                }
                break;

            case 's':
                if(isMoving)
                {
                    anim.SetCurrentAnimation("Walking_S");
                }
                else
                {
                    anim.SetCurrentAnimation("Idle_S");
                }
                break;

            case 'd':
                if(isMoving)
                {
                    anim.SetCurrentAnimation("Walking_E");
                }
                else
                {
                    anim.SetCurrentAnimation("Idle_E");
                }
                break;
        }
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

        //Anim info
        if(DebugConsole.SHOW_ANIMATOR_INFO)
        {
            g.setColor(Color.BLACK);
            g.drawString(anim.toString(), myPos.x + 5, myPos.y - 1);
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
