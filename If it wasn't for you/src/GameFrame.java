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

    public GameFrame()
    {
        Initialize();
    }

    private void Initialize()
    {
        setTitle("If it wasn't for you");
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //Set up the UI Layer
        uiManager = new UIManager(this);
        add(uiManager);

        //Set up the play area
        playArea.setPreferredSize(new Dimension(500, 500));
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
        playArea.add(c);
        revalidate();
        repaint();
    }
}
