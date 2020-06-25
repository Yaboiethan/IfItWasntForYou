package Engine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/*
The arcana is the means by which all is revealed. These cards will determine various sliders determined later on
that impact a variety of things in the game depending on what is drawn, its placement in the reading, and the arcana
 */
public class TarotCard extends GameObject
{
    //Variables
    private String arcana;

    public TarotCard(Position startPos, String n)
    {
        super();
        setPosition(startPos);
        //Set initial sprite to back
        setSprite("/TarotDeck/Card_Back.jpg");
        setPreferredSize(getSpriteSize());
        //Set other variables
        arcana = n;
    }

    public void flip()
    {
        try
        {
            Image flipped = ImageIO.read(getClass().getResource("/TarotDeck/Card_" + arcana + ".jpg"));
            setSprite(flipped);
        }
        catch (IOException e)
        {
            GameRunner.debugConsole.AddTextToView("Attempt to load " + arcana + " card failed.");
        }
    }

    public String toString()
    {
        return arcana;
    }
}
