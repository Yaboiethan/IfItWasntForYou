/*
AudioPlayer components do just that, play audio. They store audio files and play them on demand whenever the GameObject
requests for it to play.
 */
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class AudioPlayer
{
    public enum PlayStatus {PLAYING, PAUSED, STOPPED}

    //Variables
    private InputStream soundFile;
    private Clip audioClip;
    private PlayStatus curStatus;
    private AudioInputStream ais;
    //TODO Pause and Resume functions
    //private int curMilisecond;
    //TODO Loop function


    public AudioPlayer() //Blank
    {
        audioClip = null;
        curStatus = PlayStatus.STOPPED;
    }

    public void loadClip(String fileName)
    {
        try
        {
            //soundFile = new File("C:\\Windows\\media\\Speech On.wav");
            soundFile = AudioPlayer.class.getResourceAsStream("/SFX/" + fileName + ".wav");
            ais = AudioSystem.getAudioInputStream(soundFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(ais);
        }
        catch (UnsupportedAudioFileException | IOException e)
        {
            GameRunner.debugConsole.AddTextToView("AudioPlayer could not load " + fileName + "\n  " + e.getMessage());
        }
        catch(LineUnavailableException e)
        {
            GameRunner.debugConsole.AddTextToView(e.getMessage() + "\n  " + Arrays.toString(e.getStackTrace()));
        }
    }

    public void play()
    {
        //Reset before play
        audioClip.stop();
        audioClip.setMicrosecondPosition(0);

        //Check null
        if(audioClip == null)
        {
            try
            {
                throw new Exception();
            }
            catch (Exception e)
            {
                GameRunner.debugConsole.AddTextToView("Clip is null");
                return;
            }
        }
        //Play
        audioClip.start();
        curStatus = PlayStatus.PLAYING;
    }

    public static File GetResource(String fName)
    {
        String f = AudioPlayer.class.getResource("/SFX/" + fName + ".wav").getPath();
        f = f.replaceAll("%20", " ");
        File file = new File(f);
        return file;
    }

    public void clear()
    {

        try {
            ais.close();
        } catch (IOException e) {
            GameRunner.debugConsole.AddTextToView(e.getMessage());
        }
        audioClip.close();
    }
}
