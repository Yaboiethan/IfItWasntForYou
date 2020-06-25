package Engine;
/*
The GamePanel is the main panel for which all UI and game stuff is displayed. This class is focused on display
This Panel displays other panels on top of it
 */

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JLayeredPane
{
    //Various layers
    private JLayeredPane playArea;
    private static BattleFrame battleArea;
    public static UIManager uiManager;

    public GamePanel(JFrame p)
    {
        Initialize(p);
    }

    private void Initialize(JFrame p)
    {
        //Set up panel
        setSize(SettingsMenu.getScreenResolution());
        //Set up play area
        playArea = new JLayeredPane();
        playArea.setSize(getSize());
        playArea.setOpaque(false);
        playArea.setLayout(new BorderLayout());
        add(playArea);
        //Set up UI
        uiManager = new UIManager(p);
        add(uiManager);
        //Set up battle
        //Set up the battlearea
        battleArea = new BattleFrame();
        add(battleArea);

        setLayer(playArea, 0);
        setLayer(battleArea, 1);
        setLayer(uiManager, 2);
        toggleBattleScreen(GameFrame.inBattle);
    }

    public void addToPlayArea(JComponent c)
    {
        //Set layering
        if(c instanceof RenderableTile)
        {
            playArea.setLayer(c, ((RenderableTile) c).getLayerId());
        }
        else
        {
            playArea.setLayer(c, 1);
        }
        playArea.add(c);
        validate();
        repaint();
    }

    public void setResolution(Dimension d)
    {
        setSize(d);
        playArea.setSize(d);
        uiManager.setPreferredSize(d);
        uiManager.setBounds(getBounds());
        uiManager.getTextbox().setPosition(new Position());
        repaint();
    }

    public static void toggleBattleScreen(boolean b)
    {
        GameFrame.inBattle = b;
        battleArea.setVisible(GameFrame.inBattle);
    }
}
