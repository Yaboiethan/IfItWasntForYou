import javax.swing.*;
import java.awt.*;

public class UIManager extends JLayeredPane
{
    //Private Variables
    private boolean showBlackScreen = true;
    private Textbox textBox;
    private SettingsMenu settingsMenu;

    public UIManager(JFrame f)
    {
        //Set up the frame
        setPreferredSize(f.getSize());
        setBounds(f.getBounds());
        setLayout(null);
        setOpaque(false);
        setBackground(new Color(255, 255, 255, 0));
        //Set up the textbox image and location
        textBox = new Textbox(GameObject.getResource("/Sprites/UI/TestTextbox.jpg"));
        add(textBox);
        //Set up the settingsMenu
        settingsMenu = new SettingsMenu();
        add(settingsMenu);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //Screen black
        if(showBlackScreen || DebugConsole.OVERRIDE_BLACKSCREEN)
        {
            setOpaque(true);
            setBackground(new Color(0,0,0,255));
        }
        else
        {
            if(isOpaque()) //Reset after showing black screen
            {
                setOpaque(false);
                setBackground(new Color(255, 255, 255, 0));
            }

            if(Textbox.isTextboxActive()) //Show the Textbox
            {
                textBox.paintComponent(g);
            }
            else if (DebugConsole.OVERRIDE_TEXTBOX_VISUALS)
            {
                textBox.paintOverride(g);
            }
        }
    }

    public void Update()
    {
        if(Textbox.isTextboxActive())
        {
            textBox.Update();
        }

        //Update black screen
        //TODO Screen wiping
    }

    //Various Sets and Gets
    public boolean isBlackScreenActive()
    {
        return showBlackScreen;
    }

    public void setShowBlackScreen(boolean showBlackScreen)
    {
        this.showBlackScreen = showBlackScreen;
    }

    public Textbox getTextbox()
    {
        return textBox;
    }

    public SettingsMenu getSettingsMenu()
    {
        return settingsMenu;
    }
}
