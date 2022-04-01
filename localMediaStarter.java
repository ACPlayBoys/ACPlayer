import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class localMediaStarter {
    static String file; 

    public localMediaStarter(){
        processInformation();
    }

    public static void main(String[] args)
    {
        try
        {
            file=args[0];
        }
        catch(ArrayIndexOutOfBoundsException e){file=null;}
        new localMediaStarter();
    }

    

    public void processInformation(){
        try
        {
            Socket s = new Socket("localhost", 5000);
            ObjectOutputStream p = new ObjectOutputStream(s.getOutputStream());
            p.writeObject(file);
            p.flush();
            // Here we read the details from server
            BufferedReader response = new BufferedReader(new InputStreamReader(s.getInputStream()));
            p.close();
            response.close();
            s.close();
        }
        catch(Exception e){new Thread(new Runnable(){public void run(){new JarvisServer().main(null);}}).start();localMediaStarter.main(new String[]{file});}
}
}