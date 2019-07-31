package AppPackage;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
public class MainClass 
{
    FileInputStream FIS;
    BufferedInputStream BIS;
    public long pauseLocation;
    public Player player;
    public long songTotalLength;
    public String FileLocation;
    public void stop()
    {
        if(player != null)
        {
            player.close();
            pauseLocation=0;
            songTotalLength=0;
            MP3PlayerGUI.Display.setText("");
        }
    }
     public void pause()
    {
        if(player != null)
        {
            try {
                pauseLocation = FIS.available();
                player.close();
            } catch (IOException ex) {
            }
        }
    }
    public void play(String path) throws JavaLayerException
    {
        try {
            FIS = new FileInputStream(path);
            BIS = new BufferedInputStream(FIS);
            player = new Player(BIS);
           songTotalLength = FIS.available();
           FileLocation = path+"";
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
        }
        new Thread()
        {
            @Override
            public void run()
            {
                try {
                    player.play();
                    if(player.isComplete()&&MP3PlayerGUI.count==1)
                    {
                        play(FileLocation);
                    }if(player.isComplete())
                    {
                        MP3PlayerGUI.Display.setText("");
                    }
                } catch (JavaLayerException ex) {
                   
                }
            }
        }.start();
    }
    public void resume() throws JavaLayerException
    {
        try {
            FIS = new FileInputStream(FileLocation);
            BIS = new BufferedInputStream(FIS);
            player = new Player(BIS);
            FIS.skip(songTotalLength-pauseLocation);
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
        }
        new Thread()
        {
            @Override
            public void run()
            {
                try {
                    player.play();
                } catch (JavaLayerException ex) {
                   
                }
            }
        }.start();
    }
}
