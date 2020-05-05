/*
NPCs are gameobjects that interact (or at least have the capacity to) with the player in some form:
dialogue, walking colliders, stuff like that
 */

import DEBUGCONSOLE.DebugConsole;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class NPC extends GameObject
{
    //Variables
    private Collider myCol;
    private TriggerCollider myTrig;

    public NPC(Position startingPos)
    {
        myPos = startingPos;
        //Set sprite
        setSprite(getResource("/Sprites/NPC/Girl0_Walking_0.png"));
        ScaleSprite(1.4);
        //Set up colliders and triggers
        myCol = new Collider(this);
        Rectangle2D rect = new Rectangle();
        rect.setRect(myPos.x - 12, myPos.y - 10, getSpriteSize().width + 24, getSpriteSize().height + 20);
        myTrig = new TriggerCollider(rect, false);
    }

    public void Update() //To be run every frame
    {
        //Check if in trigger
        if(myTrig.getInTrigger())
        {
            System.out.println("In trigger");
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(DebugConsole.SHOW_COLLIDERS) //Draw Colliders
        {
            g.setColor(Color.GREEN);
            Graphics2D g2d = (Graphics2D) g;
            g2d.draw(myCol.getColObject());
            g.setColor(Color.RED);
            g2d.draw(myTrig.getColObject());
            g2d.dispose();
        }
    }
}
