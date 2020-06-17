/*
RenderableTiles are low-resource representations of tiles created by the MapBuilder.
They have a tile painted on to them and then disable repainting so it's not called every frame
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderableTile extends JComponent
{
    private Image mImage;
    private double scaleFactor;
    private Position mPos;
    private int layerId;

    public RenderableTile(Image i, Position p, int lId)
    {
        mImage = i;
        mPos = p;
        setIgnoreRepaint(true);
        layerId = lId;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(mImage, mPos.x, mPos.y, null);
    }

    public void scale(double factor)
    {
        //Set the variable
        scaleFactor = factor;
        //Actual Math
        int width = (int) Math.round(mImage.getWidth(this) * factor);
        int height = (int) Math.round(mImage.getHeight(this) * factor);
        Image tmp = mImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        mImage = resized;
        setIgnoreRepaint(false);
        repaint();
        setIgnoreRepaint(true);
    }

    public void setPosition(Position p)
    {
        mPos = p;
    }

    public int getTileSize()
    {
        return mImage.getWidth(null);
    }

    public int getLayerId()
    {
        return layerId;
    }
}
