import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LayersTab extends JPanel
{
    //Variables
    private static ArrayList<Tile> tiles;
    public LayersTab()
    {
        super();
        tiles = new ArrayList<>();
        setLayout(new FlowLayout());
        Initialize();
    }

    public void Initialize()
    {
        //Add sub-components
        //3 buttons for layers
        JButton bgLayer = new JButton("B");
        JButton colLayer = new JButton("F");
        JButton npcLayer = new JButton("N");
        add(bgLayer);
        add(colLayer);
        add(npcLayer);
    }
}
