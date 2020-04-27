/*
The GameObject is the basis for which all renderable things are built. A GameObject is anything that is rendered on screen and exists within the game space.
 */
//Imports
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class GameObject extends JComponent
{
    //Transformation variables
    protected Position myPos;
    private Image sprite;
    private String ogImg;
    public GameObject()
    {
        setLayout(null);
    }

    protected void paintComponent(Graphics g) //Paint function
    {
        super.paintComponent(g);
        g.drawImage(sprite, myPos.getX(), myPos.getY(), this);
    }

    //Sets and gets
    public void setSprite(String path)
    {
        ogImg = path;
        try
        {
            sprite = ImageIO.read(getClass().getResource(path));
        }
        catch (IOException e)
        {
            System.out.println("Loading image " + path + " has failed");
        }
    }

    public void setSprite(Image i)
    {
        sprite = i;
    }

    public Image getSprite()
    {
        return sprite;
    }

    public Dimension getSpriteSize()
    {
        return new Dimension(sprite.getWidth(this), sprite.getHeight(this));
    }

    public void setPosition(int x, int y)
    {
        myPos.setX(x);
        myPos.setY(y);
    }

    public void setPosition(Position pos)
    {
        myPos = pos;
    }

    public Position getPosition()
    {
        return myPos;
    }

    //Sprite Alterations
    public void ReverseSprite()
    {
        double rotationRequired = Math.toRadians(180);
        double locationX = sprite.getWidth(this) / 2;
        double locationY = sprite.getHeight(this) / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        Image revIm = op.filter((BufferedImage) sprite, null);
        sprite = revIm;
    }

    public void ScaleSprite(double factor)
    {
        int width = (int) (sprite.getWidth(this) * factor);
        int height = (int) (sprite.getHeight(this) * factor);
        Image tmp = sprite.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        sprite = resized;
    }

    public void ResetSprite()
    {
        try
        {
            sprite = ImageIO.read(getClass().getResource(ogImg));
        }
        catch(IOException e)
        {
            System.out.println("Reset failed");
        }
    }
}

//Physics things
class Position
{
    int x;
    int y;
    public final static Position ORIGIN = new Position(0,0);
    public final static Position CENTER = new Position(GUIManager.defaultSize.width / 2, GUIManager.defaultSize.height / 2);
    public enum Direction {NORTH, EAST, WEST, SOUTH};

    public Position()
    {
        x = 0;
        y = 0;
    }

    public Position(int sx, int sy)
    {
        x = sx;
        y = sy;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int sx)
    {
        x = sx;
    }

    public void setY(int sy)
    {
        y = sy;
    }

    public String toString()
    {
        return "( " + x + ", " + y + " )";
    }
}