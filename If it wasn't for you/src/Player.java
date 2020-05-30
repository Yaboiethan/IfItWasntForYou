/*
GameObjects in themselves cannot be instantiated, but are the foundation for which all rendered objects are built
The Player class is the vessel of the player's inputs, and respond directly to what they input
    - Movement
    - Interaction with environment
    - Animation
    - Collision
 */

import java.awt.*;

public class Player extends GameObject
{
    //Variables
    public boolean isMoving;
    private static int speed = 2;
    private static char currentKey;
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
        String resourcePath = "/Sprites/Player/Player_";
        //Initialize Object
        anim = new Animator(this, "Player");

        //Anim 0-- IDLE SOUTH
        anim.buildAnimation("Idle_S", getResource(resourcePath + "Walking_S_0.png"), 0);

        //Anim 1-- WALKING SOUTH
        anim.buildAnimation("Walking_S", resourcePath, new int[] {1,0,2,0}, 1.1);

        //Anim 2-- IDLE NORTH
        anim.buildAnimation("Idle_N", getResource(resourcePath + "Walking_N_0.png"), 0);

        //Anim 3-- WALKING NORTH
        anim.buildAnimation("Walking_N", resourcePath, new int[] {1,0,2,0}, 1.1);

        //Anim 4-- IDLE EAST
        anim.buildAnimation("Idle_E", getResource(resourcePath + "Walking_E_0.png"), 0);

        //Anim 5- WALKING EAST
        anim.buildAnimation("Walking_E", resourcePath, new int[] {1,0,2,0}, 1.1);

        //Anim 6-- IDLE WEST
        anim.buildAnimation("Idle_W", getResource(resourcePath + "Walking_W_0.png"), 0);

        //Anim 7- WALKING WEST
        anim.buildAnimation("Walking_W", resourcePath, new int[] {1,0,2,0}, 1.1);

        //Set the first frame of the object
        setSprite(anim.getCurAnim().getFrame(0));
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
    public void setMoving(boolean m, char key) //Set the key being pressed and if the player is moving
    {
        currentKey = key;
        isMoving = m;
        //Animation stuff
        if(getMovement(Position.charToDirection(currentKey))) //Check if can move in direction
        {
            if(m)
            {
                anim.setCurrentAnimation("Walking_" + Position.getDirectionAbbrev(Position.charToDirection(currentKey)));
            }
            else
            {
                anim.setCurrentAnimation("Idle_" + Position.getDirectionAbbrev(Position.charToDirection(currentKey)));
            }
        }
        else
        {
            anim.setCurrentAnimation("Idle_" + Position.getDirectionAbbrev(getDirection()));
        }
    }

    public void setMovement(boolean b, Position.Direction dir) //Enable/Disable movement in specific direction
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

    private boolean getMovement(Position.Direction d)
    {
        switch(d)
        {
            case NORTH:
                return mNorth;

            case EAST:
                return mEast;

            case SOUTH:
                return mSouth;

            case WEST:
                return mWest;

        }
        return false;
    }

    public void setMovement(boolean b) //Used to disable/enable all movement at once (IE: Textbox)
    {
        mNorth = b;
        mEast = b;
        mSouth = b;
        mWest = b;
        //Check if false for animation correction
        if(!b)
        {
            String animName = "Idle_" + Position.getDirectionAbbrev(getDirection());
            anim.setCurrentAnimation(animName);
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

    public Position.Direction getDirection() //Get the direction the player is facing
    {
        String editedName = anim.getCurAnim().getName();
        editedName = editedName.replaceAll("Idle_", "");
        editedName = editedName.replaceAll("Walking_", "");
        switch(editedName.charAt(0))
        {
            case 'N':
                return Position.Direction.NORTH;

            case 'S':
                return Position.Direction.SOUTH;

            case 'E':
                return Position.Direction.EAST;

            case 'W':
                return Position.Direction.WEST;
        }
        return null;
    }

    public static char getCurrentKey()
    {
        return Character.toUpperCase(currentKey);
    }

    public static void resetCurrentKey()
    {
        currentKey = '\u0000';
    }

    //Overriding the base paintComponent to add various Debug things
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
            Position mPos = new Position(GameRunner.getMousePosition().x, GameRunner.getMousePosition().y);
            g.drawString(GameRunner.getMousePosition().toString(), mPos.x, mPos.y + 2);
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

    public static boolean isValidKey(char key)
    {
        key = Character.toUpperCase(key);
        boolean flag = false;
        char[] acceptedKeys = new char[] {'W', 'A', 'S', 'D'};
        for(char c: acceptedKeys)
        {
            if(c == key)
            {
                flag = true;
            }
        }
        return flag;
    }
}
