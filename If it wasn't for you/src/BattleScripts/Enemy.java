package BattleScripts;/*
Enemies are just that, blobs of HP with attacks every now and then.
*/

import Engine.GameObject;
import Engine.Position;

import java.awt.*;

public class Enemy extends GameObject
{
    public Enemy(Position stP)
    {
        super();
        //TODO Image stuff
        setSprite("/Sprites/Battle/Enemy_Battle.png");
        setPreferredSize(getSpriteSize());
        setPosition(stP);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
