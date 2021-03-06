package Engine;/*
The Engine.Animator class is the projector that constantly spins the animation "film" and projects it onto its attached Engine.GameObject
Animators require GameObjects to run, and will throw an error is this is not the case.
 */
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Animator
{
    //Private Variables
    private GameObject obj;
    private String name; //Used to get keyframes from resources folder
    private ArrayList<Animation> anims = new ArrayList<Animation>();
    int curFrame = 0;
    int curAnim = 0;
    double timer = 0;

    public Animator(GameObject o, String n) //Fill Constructor
    {
        obj = o;
        name = n;
    }

    public void UpdateAnimator()
    {
        //Dont bother updating 1 frame animations
        if(anims.get(curAnim).getSize() > 1)
        {
            if (timer >= anims.get(curAnim).getCoolDown()) //Switch to next frame
            {
                obj.setSprite(getNextFrame());
                timer = 0; //Reset
            }
            else
            {
                timer += 0.1; //Step
            }
        }
    }

    //Gets
    public GameObject getObject()
    {
        return obj;
    }

    public String getName()
    {
        return name;
    }

    public String toString()
    {
        return name +  "  " + getCurAnim().getName() + ":  Frame " + curFrame + "/" + anims.get(curAnim).getSize();
    }

    public void buildAnimation(String name, String filePrefix, int[] frameOrder, double cooldown) //Multi-Frame animation
    {
        String finalFilePrefix = filePrefix + name + "_";
        Animation thisAnim = new Animation(frameOrder.length, name, cooldown);
        //Add frames according to frameOrder
        for(int i = 0; i < frameOrder.length; i++)
        {
            thisAnim.setFrame(GameObject.getResource(finalFilePrefix + frameOrder[i] + ".png"), i);
        }
        this.addAnimation(thisAnim); //Add to this animator
    }

    public void buildAnimation(String name, Image baseImg, double cooldown) //Creates single frame animation
    {
        Animation thisAnim = new Animation(1, name, cooldown);
        thisAnim.setFrame(baseImg, 0);
        this.addAnimation(thisAnim);
    }

    public void setCurrentAnimation(String n)
    {
        for(int i = 0; i < anims.size(); i++) //Find anim with this string
        {
            if(anims.get(i).getName().equals(n))
            {
                //Make sure the animation isn't the same
                if(curAnim != i)
                {
                    curAnim = i;
                    //Set the first frame already
                    obj.setSprite(anims.get(curAnim).getFrame(0));
                    //Reset animation variables
                    curFrame = 0;
                    timer = 0;
                }
                return;
            }
        }

        try //Throw exception if animation not found
        {
            throw new Exception("Animation '" + n +  "' does not exist");
        }
        catch (Exception e)
        {
            GameRunner.debugConsole.AddTextToView(e.toString() + "\n    " + Arrays.toString(e.getStackTrace()));
        }
    }

    private Image getNextFrame()
    {
        if(curFrame >= anims.get(curAnim).getSize() - 1) //Reset animation
        {
            curFrame = 0;
        }
        else
        {
            curFrame++;
        }
        return anims.get(curAnim).getFrame(curFrame);
    }

    public Animation getCurAnim()
    {
        return anims.get(curAnim);
    }

    //Practical Functions
    public void addAnimation(Animation a) //Add a pre-made Animation
    {
        anims.add(a);
    }
}

/*Animations are spools of images that constantly spin according to the cooldown time between frames
The current frame is then sent to the animator and projected onto the Engine.GameObject ultimately
 */
class Animation
{
    //Variables
    private int animSize; //Size of animation
    private Image[] frames; //All the frames
    private String name;
    private double coolDown; //Time between frames

    public Animation(int size, String n, double d) //Fill Constructor
    {
        animSize = size;
        name = n;
        coolDown = d;
        //Create array
        frames = new Image[animSize]; //0 will store an Image
    }

    //Sets and Gets
    public int getSize()
    {
        return animSize;
    }

    public Image getFrame(int i)
    {
        if(i < animSize)
        {
            return frames[i];
        }
        return null;
    }

    public void setFrame(Image f, int i)
    {
        if(i < animSize)
        {
            frames[i] = f;
        }
    }

    public String getName()
    {
        return name;
    }

    public double getCoolDown()
    {
        return coolDown;
    }
}
