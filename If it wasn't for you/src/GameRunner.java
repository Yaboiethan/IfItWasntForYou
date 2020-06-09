/*
The GameRunner class contains all the game elements themselves and the various lists needed to keep the game running
//TODO Generate Maps through libtiled library
 */
import java.awt.event.*;
import java.util.ArrayList;

public class GameRunner {
    //Frame variables (Statics)
    static GameFrame eGui;
    public static Player player;
    private static TarotDeck deck;
    private static boolean isReady = false;

    //ArrayLists for updating
    private static ArrayList<Collider> colliders = new ArrayList<>();
    private static ArrayList<NPC> npcs = new ArrayList<>();

    //Debug Variables
    public static DebugConsole debugConsole = new DebugConsole();
    private static Position mousePos = new Position(0,0);
    public static boolean hitEThisFrame;

    public static void main(String[] args)
    {
        deck = new TarotDeck();
        deck.Shuffle();
        eGui = new GameFrame();
        Initialize();
    }

    private static void Initialize()
    {
        //Enable black screen while loading
        GameFrame.uiManager.setShowBlackScreen(true);
        //Add the first card
        TarotCard testCard = deck.GetCard(0);
        testCard.setPosition(150, 280);
        eGui.addToPlayArea(testCard);
        testCard.flip();

        //Add MarketCollider
        SceneObject testCollider = new SceneObject(new Position(100,100), "Market Tent");
        eGui.addToPlayArea(testCollider);

        //Add TestNPC
        NPC testGirl = new NPC(new Position(150,200));
        eGui.addToPlayArea(testGirl);
        npcs.add(testGirl); //Add to arraylist

        //Add the player
        player = new Player(new Position(250, 200));
        eGui.addToPlayArea(player);

        //Add listener for mouse position
        eGui.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mousePos = new Position(e.getX(), e.getY());
            }
        });

        eGui.pack();
        //Disable black screen, since done loading
        GameFrame.uiManager.setShowBlackScreen(false);
        isReady = true;
        //Start the gameUpdate
        GameUpdate();
    }

    /*
    This method runs all of the GameObject Update() methods and helps keep the game running.
    If something needs to be updated, it needs to be called here.
     */
    private static void GameUpdate()
    {
        //TODO REMOVE BELOW
        //Testing manual resolution override
        SettingsMenu.setScreenResolution(eGui, 0);
        while (eGui.isVisible())
        {
            if(!isReady) //Check if ready
            {
                continue;
            }

            //UIManager Update
            GameFrame.uiManager.Update();

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

    public static void addtoColliders(Collider c)
    {
        colliders.add(c);
    }

    public static ArrayList<Collider> getColliders()
    {
        return colliders;
    }

    public static Position getMousePosition()
    {
        return mousePos;
    }

    public static boolean pointIntersectsCollider(Position pos, Collider me)
    {
        boolean flag = false;
        for(Collider c: colliders)
        {
            //Check if same
            if(me.isSame(c))
            {
                continue;
            }

            if(!(c instanceof TriggerCollider) && c.col.contains(pos.x, pos.y))
            {
                flag = true;
            }
        }
        return flag;
    }

    public static ArrayList<NPC> getNpcs()
    {
        return npcs;
    }

    public static TarotDeck getDeck() {
        return deck;
    }
}
