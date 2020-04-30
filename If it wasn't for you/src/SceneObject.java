/*
Sceneobjects are static, non-moving assets that serve mainly as setpieces in the environment
 */
import DEBUGCONSOLE.DebugConsole;

import java.awt.*;

public class SceneObject extends GameObject
{
    //Private Variables
    Collider myCol;
    public SceneObject(Position startingPos, String fileName)
    {
        super();
        setPosition(startingPos);
        setSprite("/StaticAssets/" + fileName + ".png");
        setPreferredSize(getSpriteSize());
        ScaleSprite(1.4);
        myCol = new Collider(this);
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
    }
}
