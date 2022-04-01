import uk.co.caprica.vlcj.component.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
class canvas extends Canvas
{
    ImageIcon background=new ImageIcon("images/np/background/1.png");  
     public void paint(Graphics g)  
        {  
            ACPlayer.an.changeOverlay(this.getWidth(),this.getHeight());  
            super.paint(g);
            Image i=background.getImage();
            setBackground(Color.BLACK);
            g.setColor(Color.BLUE);
            g.drawImage(i,1,1,this.getWidth(),this.getHeight(),this); 
        }
        public void update(Graphics g)
        {           
            Image i=background.getImage();            
            g.drawImage(i,1,1,this.getWidth(),this.getHeight(),this); 
        }
    
    public void backg(String o)
    {        
        background=new ImageIcon(o);
        this.repaint();
    }
}
