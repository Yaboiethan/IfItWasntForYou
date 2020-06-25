package Engine;/*
The Engine.BattleFrame is the pane that displays the battle screen, and will be the screen when it comes to combat things
 */
import BattleScripts.*;
import javax.swing.*;
import java.awt.*;

public class BattleFrame extends JPanel
{
    //Components
    private SceneObject[][] grid = new SceneObject[3][3];
    private SceneObject[][] enemyGrid = new SceneObject[3][3];
    private Player_Battle player;
    private Enemy testEnemy;

    public BattleFrame()
    {
        super();
        Initialize();
        //Set up player
        player = new Player_Battle(new Position(grid[1][1].getPosition().x + 20, grid[1][1].getPosition().y + 8));
        add(player);
        setComponentZOrder(player, 0);
        //Set up enemy
        testEnemy = new Enemy(new Position(enemyGrid[1][1].getPosition().x + 6, enemyGrid[1][1].getPosition().y + 11));
        add(testEnemy);
        setComponentZOrder(testEnemy, 0);
    }

    private void Initialize()
    {
        //Set up the panel
        setSize(SettingsMenu.getScreenResolution());
        setLayout(new OverlayLayout(this));
        setOpaque(true);
        setVisible(false);

        //Create the player grid
        int startX = 20;
        int startY = 50;
        //Draw all the grid objects
        for(int r = 0; r < grid.length; r++)
        {
            for(int c = 0; c < grid[0].length; c++)
            {
                grid[r][c] = new SceneObject(new Position(startX, startY), "TestFrame");
                grid[r][c].ScaleSprite(0.5);
                add(grid[r][c]);
                validate();
                repaint();
                startX += 65;
            }
            startY += 65;
            startX = 20;
        }

        //Create the enemy grid
        startX = 350;
        startY = 50;
        //Draw all the grid objects
        for(int r = 0; r < grid.length; r++)
        {
            for(int c = 0; c < grid[0].length; c++)
            {
                enemyGrid[r][c] = new SceneObject(new Position(startX, startY), "TestFrame");
                enemyGrid[r][c].ScaleSprite(0.5);
                add(enemyGrid[r][c]);
                startX += 65;
            }
            startY += 65;
            startX = 350;
        }
    }

    public boolean canMoveTo(Position.Direction dir)
    {
        boolean ret = false;

        return ret;
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }
}
