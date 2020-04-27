import DEBUGCONSOLE.DebugConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GUIManager {
    //Frame variables
    static JFrame eGui = new JFrame();
    public static final Dimension defaultSize = new Dimension(500, 500);
    static JPanel playArea = new JPanel();
    static Player player;
    private static TarotDeck deck;
    private static ArrayList<Collider> colliders = new ArrayList<>();
    private static DebugConsole debugConsole = new DebugConsole();

    public static void main(String[] args) {
        deck = new TarotDeck();
        deck.Shuffle();
        Initialize();
    }

    private static void Initialize() {
        //Set up the window basics
        eGui.setTitle("If it wasn't for you");
        eGui.setSize(500, 500);
        eGui.setResizable(false);
        eGui.setDefaultCloseOperation(eGui.EXIT_ON_CLOSE);

        //Set up the play area
        eGui.add(playArea);
        playArea.setPreferredSize(new Dimension(500, 500));
        playArea.setLayout(new BorderLayout());
        //Enable window
        eGui.setVisible(true);

        //Add the first card
        TarotCard testCard = deck.GetCard(0);
        testCard.setPosition(50, 50);
        playArea.add(testCard);

        //Add the player
        player = new Player(new Position(200, 200));
        playArea.add(player);
        playArea.setBorder(BorderFactory.createTitledBorder("Play Area"));
        //Add player keylisteners
        eGui.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                player.setMoving(true, e.getKeyChar());
                //Debug log
                if(e.getKeyCode() == KeyEvent.VK_F1) //F1 brings up debug log
                {
                   if(!debugConsole.isVisible())
                   {
                       debugConsole.setVisible(true);
                   }
                   else
                   {
                       debugConsole.setVisible(false);
                   }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player.setMoving(false, e.getKeyChar());
            }
        });

        eGui.pack();
        eGui.revalidate();
        //Start the gameUpdate
        GameUpdate();
    }

    private static void GameUpdate() {
        while (eGui.isVisible()) {
            //Player Update
            player.Update();

            //Sleep to slow things down a bit
            try {
                Thread.sleep(1000L / 60L);
            } catch (InterruptedException e) {
                System.out.println("Interruption Exception");
            }
        }
    }

    public void AddComponentToScreen(GameObject j) {
        eGui.add(j);
        eGui.revalidate();
    }

    public static void AddtoColliders(Collider c)
    {
        colliders.add(c);
    }

    public static ArrayList<Collider> GetColliders()
    {
        return colliders;
    }
}
