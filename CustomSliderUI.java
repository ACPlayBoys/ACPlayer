import mp3.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import javax.swing.plaf.basic.BasicSliderUI;


class CustomSliderUI extends BasicSliderUI { 
    static int value;
    JSlider b;
    static int col=0;static boolean chp=false;
    
     
    public CustomSliderUI(JSlider m) {
        super(m);b=m;init();uninstallKeyboardActions(m);
    }
    public void init()
    {  b.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){co();}});
    }
    public void co(){value=b.getValue();chp=true;b.repaint();}

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(10,10);
    }

    @Override
    public void paintTrack(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        float f=trackRect.width;
        float x=f/b.getMaximum();
        g2d.setPaint(Color.GRAY);int z=(int)(b.getValue()*x);
        if (b.getOrientation() == SwingConstants.HORIZONTAL) 
        {
            if(chp==true)
            {                
                g2d.setColor(Color.blue);
                g2d.fillRect(trackRect.x, trackRect.y + trackRect.height / 2,z,2);
                g2d.setColor(Color.black);g2d.drawRect(trackRect.x, trackRect.y + trackRect.height / 2,trackRect.width,2);
            }
            else
            {
                g2d.fillRect(trackRect.x, trackRect.y + trackRect.height / 2,z,2);
                g2d.setColor(Color.black);g2d.drawRect(trackRect.x, trackRect.y + trackRect.height / 2,trackRect.width,2);
            }
        }
    }
    public void update(Graphics g) 
    {
       Graphics2D g2d = (Graphics2D)g;
        float f=trackRect.width;
        float x=f/b.getMaximum();          
         g2d.setPaint(Color.GRAY); int z=(int)(b.getValue()*x);       
         g2d.drawRect(trackRect.x + trackRect.width / 2, trackRect.y,trackRect.x + trackRect.width / 2, 2);
        }
        
        
    

    @Override
    public void paintThumb(Graphics g) { Image knobImage=null;;try{
    knobImage=ImageIO.read( new File( "images/r.png") );;
    }catch(Exception e){}
    g.drawImage( knobImage, thumbRect.x, thumbRect.y, 10, 10, null );
}}
