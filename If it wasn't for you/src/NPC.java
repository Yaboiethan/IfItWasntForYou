/*
NPCs are gameobjects that interact (or at least have the capacity to) with the player in some form:
dialogue, walking colliders, stuff like that
 */

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;

public class NPC extends GameObject
{
    //Variables
    private Collider myCol;
    private TriggerCollider myTrig;
    private File myText;
    private Animator anim;

    //Movement
    private int speed = 2;
    Position.Direction dir;
    private int curMoved = 0;
    private int moveAmt = 0;
    private boolean isMoving = false;

    public NPC(Position startingPos)
    {
        myPos = startingPos;
        //Set sprite
        InitializeAnimator();
        ScaleSprite(1.4);
        setPreferredSize(getSpriteSize());
        //Set up colliders and triggers
        myCol = new Collider(this);
        Rectangle2D rect = new Rectangle();
        rect.setRect(myPos.x - 12, myPos.y - 10, getSpriteSize().width + 24, getSpriteSize().height + 20);
        myTrig = new TriggerCollider(rect, true);
        //Set up my file
        myText = GUIManager.uiManager.getTextbox().getTextFile("sample");
    }

    private void InitializeAnimator()
    {
        String resourcePath = "/Sprites/NPC/Girl0_Walking_";
        anim = new Animator(this, "Girl0");

        //Anim 0--Idle S
        anim.buildAnimation("Idle_S", getResource(resourcePath + "S_0.png"), 0);
        //Anim 1--Idle N
        anim.buildAnimation("Idle_N", getResource(resourcePath + "N_0.png"), 0);
        //Anim 2--Idle E
        anim.buildAnimation("Idle_E", getResource(resourcePath + "E_0.png"), 0);
        //Anim 3--Idle W
        anim.buildAnimation("Idle_W", getResource(resourcePath + "W_0.png"), 0);

        setSprite(anim.getCurAnim().getFrame(0));
    }

    public void Update() //To be run every frame
    {
        //Check if moving
        if(isMoving)
        {
            move();
        }

        //Check if in trigger
        if(myTrig.getInTrigger())
        {
            if(!Textbox.isTextboxActive()) //Make sure textbox isn't active
            {
                GUIManager.uiManager.getTextbox().loadTextFile(myText);
                //TODO Replace with multiple types
                Player p = (Player) myTrig.getOther();
                anim.setCurrentAnimation("Idle_" + Position.getDirectionAbbrev(Position.getOppositeDirection(p.getDirection())));
                GUIManager.uiManager.getTextbox().loadTextbox(myText, Textbox.FILE_MIN, Textbox.FILE_MAX);
            }
        }
        myCol.UpdateCollider();
        anim.UpdateAnimator();
        myTrig.UpdateCollider();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(DebugConsole.SHOW_COLLIDERS) //Draw Colliders
        {
            //Show normal Collider
            g.setColor(Color.GREEN);
            Graphics2D g2d = (Graphics2D) g;
            g2d.draw(myCol.getColObject());
            //Show trigger
            g.setColor(Color.RED);
            g2d.draw(myTrig.getColObject());
            g2d.dispose();
        }
    }

    /*
    This method initializes the movement that will later take place in the Update loop
     */
    public void moveIn(Position.Direction dir, int amt)
    {
        isMoving = true;
        this.dir = dir;
        moveAmt = amt;
    }

    public void turn(Position.Direction dir)
    {
        anim.setCurrentAnimation("Idle_" + Position.getDirectionAbbrev(dir));
    }

    private void move()
    {
        //Check if done
        if(curMoved >= moveAmt)
        {
            isMoving = false;
            curMoved = 0;
            return;
        }
        //Set animation if not done so
        anim.setCurrentAnimation("Idle_" + Position.getDirectionAbbrev(dir));
        Position toMove = new Position(0,0);
        switch(dir)
        {
            case NORTH:
                //Check if can move
                if(!GUIManager.pointIntersectsCollider(new Position(myPos.x, myPos.y - speed), myCol))
                {
                    myPos = new Position(myPos.x, myPos.y - speed);
                }
                else
                {
                    curMoved = moveAmt;
                }
                break;

            case EAST:
                //Check if can move
                if(!GUIManager.pointIntersectsCollider(new Position(myPos.x + getSpriteSize().width + speed, myPos.y), myCol))
                {
                    myPos = new Position(myPos.x + speed, myPos.y);
                }
                else
                {
                    curMoved = moveAmt;
                }
                break;

            case SOUTH:
                //Check if can move
                if(!GUIManager.pointIntersectsCollider(new Position(myPos.x, (myPos.y + getSpriteSize().height) + speed), myCol))
                {
                    myPos = new Position(myPos.x, myPos.y + speed);
                }
                else
                {
                    curMoved = moveAmt;
                }
                break;

            case WEST:
                //Check if can move
                if(!GUIManager.pointIntersectsCollider(new Position(myPos.x - speed, myPos.y), myCol))
                {
                    myPos = new Position(myPos.x - speed, myPos.y);
                }
                else
                {
                    curMoved = moveAmt;
                }
                break;
        }
        curMoved++;
    }


    //Sets and Gets
    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public void setAnimation(String name)
    {
        anim.setCurrentAnimation(name);
    }
}
