
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
class panel extends JPanel
{
    ImageIcon background=new ImageIcon("images/np/background/1.png");  
     public void paintComponent(Graphics g)  
        {        
            super.paintComponent(g);
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
