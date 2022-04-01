import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class ACPlayer
{
    JWindow startup;
    Timer ti,ti1;
    int count=0,count1;
    boolean ret=false;
    static boolean tich=false;
    static basicPlayer an;
    String s[]=null,tempi;
    public void startPlayer(int ID,final String path)
    {
        if(ID==0)
        {
            an=new basicPlayer();
            NativeLibrary.addSearchPath("libvlc", "C:\\Program Files (x86)\\VideoLAN\\VLC");
            Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
            startup=new JWindow();
            startup.add(new win());
            startup.setSize(1000,500);
            startup.setLocationRelativeTo(null);
            startup.setBackground(Color.yellow);
            startup.setLayout(new GridLayout());
            startup.setVisible(true);
            ti=new Timer(1000,new ActionListener(){public void actionPerformed(ActionEvent e){startupwin();}});
            ti.start();
            startPlayer(path);
        }
        else
        {
            if(an.addatthelast(path)==false)System.out.println("Error");
        }
    }
    public void startPlayer(String g)
    {
        ret=an.init(g);
    }
    public void startupwin() 
    {
        count++;
        System.out.println(count);
        if(count==2)
        {
        System.out.println("call");
            if(ret==true)
            {
                tich=true;
                ti.stop();
                startup.setVisible(false);
                an.start();
            }
            else count=1;
        }        
    }
}
    