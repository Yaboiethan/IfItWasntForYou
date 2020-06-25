package BattleScripts;

import Engine.GameObject;
import Engine.Position;
import java.awt.*;

public class Player_Battle extends GameObject
{
    public Player_Battle(Position stP)
    {
        super();
        //Get the Image
        setSprite("/Sprites/Battle/Player_Battle.png");
        setPreferredSize(getSpriteSize());
        setPosition(stP);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }
}
