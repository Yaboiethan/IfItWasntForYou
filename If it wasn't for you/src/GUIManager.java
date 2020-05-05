import DEBUGCONSOLE.DebugConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class GUIManager {
    //Frame variables
    static JFrame eGui = new JFrame();
    public static final Dimension defaultSize = new Dimension(500, 500);
    static JPanel playArea = new JPanel();
    static Player player;
    private static TarotDeck deck;
    //ArrayLists for updating
    private static ArrayList<Collider> colliders = new ArrayList<>();
    private static ArrayList<NPC> npcs = new ArrayList<>();

    //Debug Variables
    public static DebugConsole debugConsole = new DebugConsole();
    private static Position mousePos = new Position(0,0);

    public static void main(String[] args)
    {
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
        playArea.setBorder(BorderFactory.createTitledBorder("Play Area"));
        //Enable window
        eGui.setVisible(true);

        //Add the first card
        TarotCard testCard = deck.GetCard(0);
        testCard.setPosition(150, 280);
        playArea.add(testCard);
        testCard.flip();

        //Add MarketCollider
        SceneObject testCollider = new SceneObject(new Position(100,100), "Market Tent");
        //testCollider.myCol.UpdateCollider();
        playArea.add(testCollider);
        eGui.revalidate();

        //Add TestNPC
        NPC testGirl = new NPC(new Position(150,200));
        playArea.add(testGirl);
        npcs.add(testGirl); //Add to array
        eGui.revalidate();

        //Add the player
        player = new Player(new Position(250, 200));
        playArea.add(player);

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
        eGui.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mousePos = new Position(e.getX(), e.getY());
            }
        });

        eGui.pack();
        //Start the gameUpdate
        GameUpdate();
    }

    private static void GameUpdate() {
        while (eGui.isVisible())
        {
            //Player Update
            player.Update();

            //Update NPCS
            for(NPC e: npcs)
            {
                e.Update();
            }

            //Sleep to slow things down a bit
            try
            {
                Thread.sleep(1000L / 60L);
            }
            catch (InterruptedException e)
            {
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

    public static Position GetMousePosition()
    {
        return mousePos;
    }
}
