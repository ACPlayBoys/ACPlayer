import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class JarvisServer {

    public static void main(String[] argv) 
    {
        int startID=0;
        ACPlayer play=new ACPlayer();
        try{
            ServerSocket s = new ServerSocket(5000);
            System.out.println("Server started");
            while (true)
            {
                Socket t = s.accept();// wait for client to connect
                System.out.println("server connected");
                ObjectInputStream b = new ObjectInputStream(t.getInputStream());
                String received = (String)b.readObject();
                System.out.println("Hello Sir I Recived The following "+received); 
                play.startPlayer(startID,received);
                b.close();
                t.close();
                startID=1;
            }
        }catch(Exception e){}
    }
}