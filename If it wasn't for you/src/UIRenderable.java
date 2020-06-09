import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
UIRenderables are the base class for all things that are drawn on the UIManager and whatever.
This contains methods and the paint method to edit various things
 */
public class UIRenderable extends JComponent
{
    protected Position myPos;

    public UIRenderable() //Blank
    {
        myPos = new Position();
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
    }

    //Editable Methods
    public Image ScaleSprite(Image i, double factor)
    {
        //Actual Math
        int width = (int) Math.round(i.getWidth(this) * factor);
        int height = (int) Math.round(i.getHeight(this) * factor);
        Image tmp = i.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public Image ScaleHorizontally(Image i, double factor)
    {
        //Actual Math
        int width = (int) Math.round(i.getWidth(this) * factor);
        int height = Math.round(i.getHeight(this));
        Image tmp = i.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public Image ScaleVertically(Image i, double factor)
    {
        //Actual Math
        int width = Math.round(i.getWidth(this));
        int height = (int) Math.round(i.getHeight(this) * factor);
        Image tmp = i.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public void setPosition(Position p)
    {
        myPos = p;
    }
}
