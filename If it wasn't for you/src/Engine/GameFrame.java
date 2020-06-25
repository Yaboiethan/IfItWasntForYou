package Engine;/*
The Engine.GameFrame is the actual JFrame object that contains the game elements, even though they are added from the Engine.GameRunner
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameFrame extends JFrame
{
    private static GamePanel gp;
    //private static JLayeredPane playArea = new JLayeredPane();
    public static boolean inBattle = true;
    //public static UIManager uiManager;
    private static MapBuilder mapBuilder;
    private static JScrollPane scrollPane;

    public GameFrame()
    {
        Initialize();
        gp = new GamePanel(this);
        scrollPane.setViewportView(gp);

        //Set up the map builder
        mapBuilder = new MapBuilder();
        mapBuilder.setTileScale(2.3);
        mapBuilder.loadTileMap("TestMap");
        mapBuilder.buildMapToPanel();
    }

    private void Initialize()
    {
        setTitle("If it wasn't for you");
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //Set up the scrollpane
        scrollPane = new JScrollPane();
        scrollPane.setSize(getSize());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        add(scrollPane);
        validate();

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

    public void setResolution(Dimension d)
    {
        setSize(d);
        gp.setResolution(d);
    }


    public static GamePanel getGamePanel()
    {
        return gp;
    }
}
