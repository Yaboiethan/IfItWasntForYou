/*
The GameFrame is the actual JFrame object that contains the game elements, even though they are added from the GameRunner
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameFrame extends JFrame
{
    private static JLayeredPane playArea = new JLayeredPane();
    public static UIManager uiManager;
    private static MapBuilder mapBuilder;

    public GameFrame()
    {
        Initialize();
        mapBuilder = new MapBuilder();
        mapBuilder.setTileScale(2.3);
        mapBuilder.loadTileMap("TestMap");
        mapBuilder.buildMapToPanel(this);
    }

    private void Initialize()
    {
        setTitle("If it wasn't for you");
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //Set up the UI Layer
        uiManager = new UIManager(this);
        add(uiManager);

        //Set up the play area
        playArea.setPreferredSize(getSize());
        playArea.setOpaque(false);
        playArea.setLayout(new BorderLayout());
        playArea.setBorder(BorderFactory.createTitledBorder("Play Area"));
        add(playArea);


        //Add player keylisteners
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(Character.toUpperCase(e.getKeyChar()) == 'E') //Runs Async, so all good
                {
                    GameRunner.hitEThisFrame = true;
                    try {
                        Thread.sleep(1000L / 60L);
                        GameRunner.hitEThisFrame = false;
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }

                if(Player.isValidKey(e.getKeyChar()))
                {
                    GameRunner.player.setMoving(true, e.getKeyChar());
                }
                //Debug log
                if(e.getKeyCode() == KeyEvent.VK_F1) //F1 brings up debug log
                {
                    if(!GameRunner.debugConsole.isVisible())
                    {
                        GameRunner.debugConsole.setVisible(true);
                    }
                    else
                    {
                        GameRunner.debugConsole.setVisible(false);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(Player.isValidKey(e.getKeyChar()))
                {
                    GameRunner.player.setMoving(false, e.getKeyChar());
                }
            }
        });
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
        revalidate();
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
}
