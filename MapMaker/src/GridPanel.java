import com.sun.corba.se.impl.orbutil.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class GridPanel extends JLayeredPane
{
    //Variables
    private int rowAmt;
    private int colAmt;

    private int tileSize;
    private double zoomFactor = 1;
    private int[][] tiles;

    public GridPanel()
    {
        super();
        rowAmt = 800;
        colAmt = 800;
        setPreferredSize(new Dimension(rowAmt, colAmt));
        setLayout(new GridLayout(rowAmt, colAmt));
        tileSize = 32;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        //Zoom
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = new AffineTransform();
        at.scale(zoomFactor, zoomFactor);
        g2d.transform(at);
        //Paint the rows
        g.setColor(Color.BLACK);
        for(int y = 0; y <= rowAmt; y += tileSize)
        {
            g.drawLine(0, y, getWidth(), y);
        }
        //Paint columns
        for(int x = 0; x <= colAmt; x += tileSize)
        {
            g.drawLine(x, 0, x, getHeight());
        }

    }

    //Sets & Gets
    public void setDimensions(Dimension d)
    {
        rowAmt = d.width;
        colAmt = d.height;
        setLayout(new GridLayout(rowAmt, colAmt));
    }

    public int getTileSize()
    {
        return tileSize;
    }

    public Dimension getDimensions()
    {
        return new Dimension(rowAmt, colAmt);
    }

    public void setZoomFactor(double d)
    {
        zoomFactor = d;
        repaint();
    }
}
