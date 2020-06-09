import javax.swing.*;
import java.awt.*;

public class SettingsMenu extends JLayeredPane
{
    public static final Dimension[] SCREEN_RESOLUTIONS = {new Dimension(600,700), new Dimension(1366, 768),
            new Dimension(1920, 1080), new Dimension(1440, 900), new Dimension(1536,864)};
    private static int curResolution;
    private static boolean isFullScreen = false;

    public SettingsMenu() //Blank
    {
        //Get the current resolution
        Dimension toCheck = Toolkit.getDefaultToolkit().getScreenSize();
        for(int i = 0; i < SCREEN_RESOLUTIONS.length; i++)
        {
            if(toCheck.equals(SCREEN_RESOLUTIONS[i]))
            {
                curResolution = i;
                break; //Stop, no need to check further once found
            }
        }
    }

    public void Initialize() //Get this JPanel ready
    {
        //Create and set up components
        setVisible(false);
    }

    //Sets and Gets --Most will be static
    public static void setScreenResolution(GameFrame frame, int index)
    {
        if(index < 0 || index > SCREEN_RESOLUTIONS.length || frame == null)
        {
            GameRunner.debugConsole.AddTextToView("Screen Resolution cannot be changed");
            return;
        }
        curResolution = index;
        frame.setResolution(SCREEN_RESOLUTIONS[curResolution]);
    }

    public static Dimension getScreenResolution()
    {
        return SCREEN_RESOLUTIONS[curResolution];
    }
}
