import mp3.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import javax.imageio.*;
import java.io.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.util.List;
import uk.co.caprica.vlcj.component.*;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.Color;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.player.*;
import uk.co.caprica.vlcj.player.embedded.*;
import uk.co.caprica.vlcj.component.overlay.*;
import uk.co.caprica.vlcj.binding.internal.*;
import uk.co.caprica.vlcj.player.embedded.windows.*;
import uk.co.caprica.vlcj.runtime.windows.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.lang.reflect.*;
public class basicPlayer extends JPanel implements DropTargetListener
    {        
        int checki=0;
        int position=0; 
        int currentplaying=0;
        int remover=0,backgr=0,openwithcount=0;;
        boolean mutecheck=false,playing=false,add=false,isplay=true,reselect=false,openwith=false,playmode=false,replay=true,visicheck=true;
        boolean fullcheck=false,opencheck=false,preparedmedia=false;
        float volume;
        int style=1,index,volumeval=0;  
        int startup_count=0;
        int visibiltycount=0;
        boolean visiblefull=false;
        String property;
        JPopupMenu popup;
        JRadioButtonMenuItem popups[];
        String background[]; 
        JMenu menu1,menu2,menu3,menu4,menu5;
        JMenuItem item1,item2,item3,item4,item5,item6,item7,item8,item9,item10,item11,item12,item13,item14,item15;
        JMenuItem item16,item17,item18,item19,item20,item21,item22,item23,item24,item25,item26;
        ImageIcon mute_disabled, mute_enabeled, next, now_playing, pause, play, previous, repeate_disabled;
        ImageIcon stop_disabled_rollover, mute_disabled_rollover, next_rollover, libraryrollover, pause_rollover, play_rollover, previous_rollover, repete_enabled_rollover,suffle_disabled_rollover;
        ImageIcon icon,repete_enabled, stop_disabled, stop_enabled, suffle_disabled, suffle_enabled;
        JButton playbutton,stopbutton,nextbutton,previousbutton,nowplayingbutton,mutebutton,repetebutton,sufflebutton;
        JButton playbutton_np,stopbutton_np,nextbutton_np,previousbutton_np,nowplayingbutton_np,mutebutton_np,repetebutton_np,sufflebutton_np;
        JFileChooser openfile;
        JFrame librarywindow;
        JFrame nowplaying;
        String ext[]=null;
        
        canvas playercanvas;
        MediaPlayerFactory mediaPlayerFactory;
        EmbeddedMediaPlayer player=null;
        
        File file[]=null,openwith_file;
        Timer progress_set,visibility,backg,openwitht;
        JSlider progress_lib,volume_lib;
        JSlider progress_np,volume_np;
        int duration;
        JLabel timelib=null,timenp=null;
        JLabel info_image=null,info_text=null;
        JList playlist=null;
        
       

        
        Mp3File id3v1_info=null;
        ID3v1 info_song1=null;
        ID3v2 info_song2=null;
        
        
        JPanel info_panel;        
        JPanel buttonpanellib=new JPanel(); //button panel
        JPanel buttonpanelnowplayong=new JPanel();//button panel np 
        JPanel playlist_panel=new JPanel(new BorderLayout());  
        JPanel lowpanel=new JPanel(), subpanel=new JPanel(new GridLayout()),mainpanel=new JPanel(new BorderLayout());    //library mode
        JPanel subpanel_np=new JPanel(new GridLayout());    //np mode      
        static JWindow supereme;

        JPanel mainpanel_np=new JPanel();
        DefaultListModel listModel = new DefaultListModel(); 
        
        String set=null;
        
                        public void changeOverlay(int w,int h)
                        {
                            player.enableOverlay(false);
                            player.enableOverlay(true);
                        }
        public boolean init(String pathe)
        {
            Thread thk=new Thread(new Runnable(){public void run(){getbackgrounds();}});            
            thk.start();
            boolean hik=true;
            if(pathe!=null)
            {
                try{
                    openwith_file=new File(pathe);
                    if(openwith_file.exists())
                    {
                        file=new File[1];
                        file[0]=openwith_file;
                        hik=true;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(librarywindow,"cannot find file","ERROR",JOptionPane.ERROR_MESSAGE);
                        hik=false; 
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(librarywindow,e,"ERROR",JOptionPane.ERROR_MESSAGE);
                    hik=false;
                }
            }
            else
            {
                openwith=false;
                hik=false;
            }            
            if(hik==true)
            {
                openwith=true;
            }
            else
            {
                openwith=false;
            }
            playercanvas= new canvas();
            popup=new JPopupMenu();
            popup.setInvoker(playercanvas);
            mediaPlayerFactory = new MediaPlayerFactory();                                                  
            property=System.getProperty("user.dir");
            icon=new ImageIcon(property+"/images/icon.png");
            librarywindow = new JFrame("AC Player");
            librarywindow.setIconImage(icon.getImage());
            backg=new Timer(10000,new ActionListener(){public void actionPerformed(ActionEvent e){background();}});  
            openwitht=new Timer(1000,new ActionListener(){public void actionPerformed(ActionEvent e){openwithcall();}});  
            visibility=new Timer(1000,new ActionListener(){public void actionPerformed(ActionEvent e){visibile();}});
            progress_set=new Timer(100,new ActionListener(){public void actionPerformed(ActionEvent e){tick();}});
            librarywindow.setSize(1000,700);       
            mute_disabled= new ImageIcon(property+"/images/play/mute_disabled.png");
                            next=new ImageIcon(property+"/images/play/next.png");
                            now_playing=new ImageIcon(property+"/images/play/now_playing.png");
                            play=new ImageIcon(property+"/images/play/play.png");
                            previous=new ImageIcon(property+"/images/play/previous.png");
                            pause=new ImageIcon(property+"/images/play/pause.png");
                            repete_enabled=new ImageIcon(property+"/images/play/repete_enabled.png");
                            repeate_disabled=new ImageIcon(property+"/images/play/repeate_disabled.png");
                            stop_disabled=new ImageIcon(property+"/images/play/stop_disabled.png");
                            suffle_disabled=new ImageIcon(property+"/images/play/suffle_disabled.png");
                            suffle_enabled=new ImageIcon(property+"/images/play/suffle_enabled.png");                            
                            stop_enabled=new ImageIcon(property+"/images/play/stop_enabled.png");
                            
                            mute_disabled_rollover= new ImageIcon(property+"/images/play/rollover/mute_disabled_rollover.png");
                            next_rollover=new ImageIcon(property+"/images/play/rollover/next_rollover.png");
                            libraryrollover=new ImageIcon(property+"/images/play/rollover/libraryrollover.png");
                            play_rollover=new ImageIcon(property+"/images/play/rollover/play_rollover.png");
                            previous_rollover=new ImageIcon(property+"/images/play/rollover/previous_rollover.png");
                            pause_rollover=new ImageIcon(property+"/images/play/rollover/pause_rollover.png");
                            repete_enabled_rollover=new ImageIcon(property+"/images/play/rollover/repete_enabled_rollover.png");
                            stop_disabled_rollover=new ImageIcon(property+"/images/play/rollover/stop_disabled_rollover.png");
                            suffle_disabled_rollover=new ImageIcon(property+"/images/play/rollover/suffle_disabled_rollover.png");
            JMenuBar menubar=new  JMenuBar();
            menu1=new JMenu("File");
            menu2=new JMenu("View");
            menu3=new JMenu("Play");
            menu4=new JMenu("Help");
            item1=new  JMenuItem("Open"); 
            item2=new  JMenuItem("Save as...");
            item3=new  JMenuItem("Close");  
            item4=new  JMenuItem("CreatePlaylist");  
            item5=new  JMenuItem("Save Now Playlist");   
            item6=new  JMenuItem("Exit");
            item7=new  JMenuItem("Download"); 
            item8=new  JMenuItem("Library");               
            item9=new  JMenuItem("Animation");      
            item10=new JMenuItem("Now Playing");                   
            item11=new JMenuItem("Skin Chooser");       
            item12=new JMenuItem("Show Menu Bar");                 
            item13=new JMenuItem("Full Screen");    
            item14=new JMenuItem("Refresh");       
            item15=new JMenuItem("Skin");            
            item16=new JMenuItem("Play/Pause");
            item17=new JMenuItem("Stop");           
            item18=new JMenuItem("Previous");                  
            item19=new JMenuItem("Next");         
            item20=new JMenuItem("Repeat");                  
            item21=new JMenuItem("Volume");         
            item22=new JMenuItem("Eject");              
            item23=new JMenuItem("AC Player Help");   
            item24=new JMenuItem("AC Player Online");
            item25=new JMenuItem("Privacy Statement Online"); 
            item26=new JMenuItem("About AC Player");
            menu5=new JMenu("Audio Track");               
            popup.add(menu5);
            playbutton=new JButton(play);                    
            stopbutton=new JButton(stop_enabled);
            nextbutton=new JButton(new ImageIcon(property+"/images/play/next.png"));
            previousbutton=new JButton(previous);
            nowplayingbutton=new JButton(now_playing);
            mutebutton=new JButton(mute_disabled);
            repetebutton=new JButton(repete_enabled);
            sufflebutton=new JButton(suffle_disabled);
            progress_lib=new JSlider(0,100,0);
            volume_lib=new JSlider(0,150,75);
            volume_lib.setPreferredSize(new Dimension(80,18));
            timelib=new JLabel(0+":"+0);                                              
            listModel.addElement("                                           ");
            playlist=new JList(listModel);            
            
            JScrollPane scroll = new JScrollPane();
            scroll.getViewport().setView(playlist);
            info_panel=new JPanel();
            playbutton.setRolloverIcon(play_rollover);                                           
            nextbutton.setRolloverIcon(next_rollover);
            previousbutton.setRolloverIcon(previous_rollover);;
            nowplayingbutton.setRolloverIcon(libraryrollover);
            mutebutton.setRolloverIcon(mute_disabled_rollover);
            repetebutton.setRolloverIcon(repete_enabled_rollover);
            sufflebutton.setRolloverIcon(suffle_disabled_rollover);;
            playbutton.setOpaque(false);
            stopbutton.setOpaque(false);                
            nextbutton.setOpaque(false);
            previousbutton.setOpaque(false);                        
            nowplayingbutton.setOpaque(false);
            mutebutton.setOpaque(false);
            repetebutton.setOpaque(false);
            sufflebutton.setOpaque(false);
            playbutton.setFocusPainted(false);
            stopbutton.setFocusPainted(false);
            nextbutton.setFocusPainted(false);
            previousbutton.setFocusPainted(false);
            nowplayingbutton.setFocusPainted(false);
            mutebutton.setFocusPainted(false);
            repetebutton.setFocusPainted(false);
            sufflebutton.setFocusPainted(false);
            playbutton.setBorderPainted(false);
            stopbutton.setBorderPainted(false);
            nextbutton.setBorderPainted(false);
            previousbutton.setBorderPainted(false);
            nowplayingbutton.setBorderPainted(false);
            mutebutton.setBorderPainted(false);
            repetebutton.setBorderPainted(false);
            sufflebutton.setBorderPainted(false);
            playbutton.setContentAreaFilled(false);
            stopbutton.setContentAreaFilled(false);
            nextbutton.setContentAreaFilled(false);
            previousbutton.setContentAreaFilled(false);
            nowplayingbutton.setContentAreaFilled(false);
            mutebutton.setContentAreaFilled(false);
            repetebutton.setContentAreaFilled(false);
            sufflebutton.setContentAreaFilled(false);
            buttonpanellib.setOpaque(false);
            lowpanel.setOpaque(false);
            subpanel.setOpaque(false);
            mainpanel.setOpaque(false);
            lowpanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            subpanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            mainpanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            buttonpanellib.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            playbutton.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            stopbutton.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            nextbutton.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            previousbutton.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            nowplayingbutton.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            mutebutton.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            repetebutton.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            sufflebutton.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            buttonpanellib.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
             progress_lib.setUI(new CustomSliderUI(progress_lib));
            Color clo=new Color(244,248,252);
            playlist.setBackground(clo);

            info_image=new JLabel(new ImageIcon(property+"/images/play/idle.png"));
            info_text=new JLabel("LastPlayed");      
            info_panel.add(info_image);           
            info_panel.add(info_text);            
            menubar.add(menu1);
            menubar.add(menu2);                  
            menubar.add(menu3);                    
            menubar.add(menu4);         
            menu1.add(item1);
            menu1.add(item7);                      
            menu1.add(item2);                    
            menu1.add(item3);                       
            menu1.addSeparator();
            menu1.add(item4);               
            menu1.add(item5);                    
            menu1.addSeparator();                        
            menu1.add(item6);
            menu2.add(item8);                       
            menu2.add(item9);                     
            menu2.add(item10);                   
            menu2.add(item11);
            menu2.add(item12);             
            menu2.add(item15);                   
            menu2.addSeparator();                     
            menu2.add(item13);
            menu2.add(item14);              
            menu3.add(item16);               
            menu3.add(item17);                    
            menu3.addSeparator();
            menu3.add(item18);                     
            menu3.add(item19);                       
            menu3.add(item20);                          
            menu3.add(item21);
            menu3.addSeparator();                   
            menu3.add(item22);                        
            menu4.add(item23);                        
            menu4.add(item24);
            menu4.add(item25);                       
            menu4.add(item26);                         
            buttonpanellib.add(repetebutton);           
            buttonpanellib.add(sufflebutton);
            buttonpanellib.add(stopbutton);           
            buttonpanellib.add(previousbutton);        
            buttonpanellib.add(playbutton);
            buttonpanellib.add(nextbutton);       
            lowpanel.add(timelib);                
            buttonpanellib.add(mutebutton);
            buttonpanellib.add(volume_lib);   
            buttonpanellib.add(nowplayingbutton);
            subpanel.add(progress_lib);
            lowpanel.add(buttonpanellib);        
            playlist_panel.add(info_panel,"North");
            playlist_panel.add(scroll,"East");
            mainpanel.add("Center",lowpanel);     
            mainpanel.add("North",subpanel);       
            librarywindow.setJMenuBar(menubar);
            librarywindow.add(playlist_panel,"East");
            librarywindow.add(mainpanel,"South");   
            //np begins here    
            nowplaying=new JFrame("AC Player");
            nowplaying.setLayout(new GridLayout());
            supereme=new JWindow(nowplaying);
            nowplaying.setIconImage(icon.getImage());
            nowplaying.setSize(1000,700);                
            timenp=new JLabel(0+":"+0);                   
            timenp.setOpaque(false);   
            player = mediaPlayerFactory.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(nowplaying));
            player.setEnableMouseInputHandling(false);             
            player.setEnableKeyInputHandling(true); 
            player.setVideoSurface(mediaPlayerFactory.newVideoSurface(playercanvas));



            progress_np=new JSlider(0,100,0);                 
            volume_np=new JSlider(0,150,75);
            volume_np.setPreferredSize(new Dimension(80,18));
            volume_np.setOpaque(false);
            playbutton_np=new JButton(play);
            stopbutton_np=new JButton(stop_enabled);
            nextbutton_np=new JButton(next);
            previousbutton_np=new JButton(previous);
            nowplayingbutton_np=new JButton(now_playing);
            mutebutton_np=new JButton(mute_disabled);
            repetebutton_np=new JButton(repete_enabled);
            sufflebutton_np=new JButton(suffle_disabled);
            playbutton_np.setRolloverIcon(play_rollover);          
            nextbutton_np.setRolloverIcon(next_rollover);
            previousbutton_np.setRolloverIcon(previous_rollover);
            nowplayingbutton_np.setRolloverIcon(libraryrollover);
            mutebutton_np.setRolloverIcon(mute_disabled_rollover);
            repetebutton_np.setRolloverIcon(repete_enabled_rollover);
            sufflebutton_np.setRolloverIcon(suffle_disabled_rollover);
            playbutton_np.setOpaque(false);    
            stopbutton_np.setOpaque(false);                 
            nextbutton_np.setOpaque(false);
            previousbutton_np.setOpaque(false);
            nowplayingbutton_np.setOpaque(false);
            mutebutton_np.setOpaque(false);
            repetebutton_np.setOpaque(false);
            sufflebutton_np.setOpaque(false);
            playbutton_np.setFocusPainted(false);
            stopbutton_np.setFocusPainted(false);
            nextbutton_np.setFocusPainted(false);
            previousbutton_np.setFocusPainted(false);
            nowplayingbutton_np.setFocusPainted(false);
            mutebutton_np.setFocusPainted(false);
            repetebutton_np.setFocusPainted(false);
            sufflebutton_np.setFocusPainted(false);
            playbutton_np.setBorderPainted(false);
            stopbutton_np.setBorderPainted(false);
            nextbutton_np.setBorderPainted(false);
            previousbutton_np.setBorderPainted(false);
            nowplayingbutton_np.setBorderPainted(false);
            mutebutton_np.setBorderPainted(false);
            repetebutton_np.setBorderPainted(false);
            sufflebutton_np.setBorderPainted(false);
            playbutton_np.setContentAreaFilled(false);
            stopbutton_np.setContentAreaFilled(false);
            nextbutton_np.setContentAreaFilled(false);
            previousbutton_np.setContentAreaFilled(false);
            nowplayingbutton_np.setContentAreaFilled(false);
            mutebutton_np.setContentAreaFilled(false);
            repetebutton_np.setContentAreaFilled(false);
            sufflebutton_np.setContentAreaFilled(false);   
            playbutton_np.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            stopbutton_np.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            nextbutton_np.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            previousbutton_np.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            nowplayingbutton_np.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            mutebutton_np.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            repetebutton_np.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            sufflebutton_np.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            buttonpanellib.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            progress_np.setUI(new CustomSliderUI(progress_np));
            progress_np.setOpaque(false);
            

            subpanel_np.setOpaque(false);
            mainpanel_np.setOpaque(false);
            buttonpanelnowplayong.setOpaque(false);
            this.setOpaque(false);

            new Thread(new Runnable(){public void run(){basics();}}).start();
            buttonpanelnowplayong.add(repetebutton_np);
            buttonpanelnowplayong.add(sufflebutton_np);
            buttonpanelnowplayong.add(stopbutton_np);
            buttonpanelnowplayong.add(previousbutton_np);
            buttonpanelnowplayong.add(playbutton_np);
            buttonpanelnowplayong.add(nextbutton_np);
            this.add(timenp);
            buttonpanelnowplayong.add(volume_np);
            buttonpanelnowplayong.add(nowplayingbutton_np);
            buttonpanelnowplayong.add(mutebutton_np);        
            subpanel_np.add(progress_np);
            this.add(buttonpanelnowplayong);
            supereme.setLayout(new BorderLayout());
            mainpanel_np.setLayout(new BorderLayout());
            mainpanel_np.add(subpanel_np,"North");
            mainpanel_np.add(this,"South");
            supereme.add(mainpanel_np,"South");
            supereme.setBackground(new Color(0,0,0,2));
            player.setOverlay(supereme);
            //addedfromhereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee   
            //player intialisationnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
            System.out.println("done");
            nowplaying.getContentPane().add(playercanvas,"Center");
            nowplaying.setMinimumSize(new Dimension(290,276));
            librarywindow.setDefaultCloseOperation(librarywindow.EXIT_ON_CLOSE);
            nowplaying.setDefaultCloseOperation(nowplaying.EXIT_ON_CLOSE);   
            return true;
        }
        public void start()
        {
            librarywindow.setVisible(true);
            backg.start();
            if(openwith==true)
            {
                add=true;
                createplayer();
            }
        }        
        public void basics()
        {
            openfile=new JFileChooser("open music");
            openfile.setMultiSelectionEnabled(true);
            createext();
            FileNameExtensionFilter filter= new FileNameExtensionFilter("Media File", ext);
            openfile.setFileFilter(filter);            
            playbutton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){if(playing==false){play();}else{pause();}}});
            stopbutton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){reset();}});
            nextbutton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){next();}});
            previousbutton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){pre();}});
            nowplayingbutton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){nowplaying(playmode);}});
            mutebutton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){mute();}});
            repetebutton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){replay();}});
            sufflebutton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){}});
            progress_lib.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){time();if(progress_lib.getValue()!=(int)player.getTime())skip(progress_lib.getValue());}});
            item1.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){load();}});
            volume_lib.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){vol1();}});
            playlist.addKeyListener(new KeyAdapter(){public void keyPressed(KeyEvent e){if(e.getKeyCode()==e.VK_DELETE){remove();}else if(e.getKeyCode()==e.VK_ENTER){skipselect();}}}); 
            playlist.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){int click=e.getClickCount();if(click==2){skipselect();}}});
            timelib.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){int click=e.getClickCount();timeset(click);}});     
            librarywindow.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){close();}}); 
            librarywindow.addKeyListener(new KeyAdapter(){public void keyPressed(KeyEvent e){System.out.println(e.getKeyCode());}});

            
            
            timenp.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){int click=e.getClickCount();timeset(click);}}); 
            playbutton_np.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){if(playing==false){play();}else{pause();}}});
            stopbutton_np.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){reset();}});
            nextbutton_np.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){next();}});
            previousbutton_np.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){pre();}});
            nowplayingbutton_np.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){nowplaying(playmode);}});
            mutebutton_np.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){mute();}});
            repetebutton_np.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){replay();}});
            sufflebutton_np.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){}});
            progress_np.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){time();if(progress_np.getValue()!=(int)player.getTime())skip(progress_np.getValue());}});
            volume_np.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){vol2();}});
            this.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){int click=e.getClickCount();fullwin(click);}});
            mainpanel_np.addMouseListener(new MouseAdapter(){public void mouseExited(MouseEvent e){panelinvisi();}});
            subpanel_np.addMouseListener(new MouseAdapter(){public void mouseExited(MouseEvent e){panelinvisi();}});
            progress_np.addMouseListener(new MouseAdapter(){public void mouseExited(MouseEvent e){panelinvisi();}});
            progress_np.addKeyListener(new KeyAdapter(){@Override public void keyPressed(KeyEvent e){keyinterface(e.getKeyCode());}});
            supereme.addKeyListener(new KeyAdapter(){@Override public void keyPressed(KeyEvent e){keyinterface(e.getKeyCode());}});
            mainpanel_np.addKeyListener(new KeyAdapter(){@Override public void keyPressed(KeyEvent e){keyinterface(e.getKeyCode());}});
            playbutton_np.addKeyListener(new KeyAdapter(){@Override public void keyPressed(KeyEvent e){keyinterface(e.getKeyCode());}});
            buttonpanelnowplayong.addKeyListener(new KeyAdapter(){@Override public void keyPressed(KeyEvent e){keyinterface(e.getKeyCode());}});
            this.addKeyListener(new KeyAdapter(){@Override public void keyPressed(KeyEvent e){keyinterface(e.getKeyCode());}});
            playbutton_np.addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){assign(true);}public void mouseExited(MouseEvent e){assign(false);}});
            stopbutton_np.addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){assign(true);}public void mouseExited(MouseEvent e){assign(false);}});
            nextbutton_np.addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){assign(true);}public void mouseExited(MouseEvent e){assign(false);}});
            previousbutton_np.addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){assign(true);}public void mouseExited(MouseEvent e){assign(false);}});
            nowplayingbutton_np.addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){assign(true);}public void mouseExited(MouseEvent e){assign(false);}});
            mutebutton_np.addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){assign(true);}public void mouseExited(MouseEvent e){assign(false);}});
            repetebutton_np.addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){assign(true);}public void mouseExited(MouseEvent e){assign(false);}});
            sufflebutton_np.addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){assign(true);}public void mouseExited(MouseEvent e){assign(false);}});  
            progress_np.addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){assign(true);}public void mouseExited(MouseEvent e){assign(false);}});  
            volume_np.addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){assign(true);}public void mouseExited(MouseEvent e){assign(false);}});
            buttonpanelnowplayong.addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){assign(true);}public void mouseExited(MouseEvent e){assign(false);}});
            this.addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){assign(true);}public void mouseExited(MouseEvent e){assign(false);}});
            subpanel_np.addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){assign(true);}public void mouseExited(MouseEvent e){assign(false);}});
            timenp.addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){assign(true);}public void mouseExited(MouseEvent e){assign(false);}});
            nowplaying.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){close();}});
            supereme.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){if(e.getButton()==e.BUTTON1){int click=e.getClickCount();fullwin(click);}else{popup.setLocation(e.getLocationOnScreen()); popup.setVisible(true);}}});            
            nowplaying.addWindowListener(new WindowAdapter(){public void windowIconified(WindowEvent e){overlay();}public void windowDeiconified(WindowEvent e){overlay();}});
            supereme.addMouseMotionListener(new MouseMotionListener(){public void mouseDragged(MouseEvent e){gesture(e.getY());}public void mouseMoved(MouseEvent e){panelvisi1();}});
            mainpanel_np.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){if(e.getButton()==e.BUTTON1){int click=e.getClickCount();fullwin(click);}else{popup.setLocation(e.getLocationOnScreen()); popup.setVisible(true);}}});
            DropTarget target[]=new DropTarget[25];
            target[0]=new DropTarget(playlist,DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[1]=new DropTarget(playbutton, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[2]=new DropTarget(stopbutton, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[3]=new DropTarget(nextbutton, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[4]=new DropTarget(previousbutton, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[5]=new DropTarget(nowplayingbutton, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[6]=new DropTarget(mutebutton, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[7]=new DropTarget(repetebutton, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[8]=new DropTarget(sufflebutton, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[9]=new DropTarget(playbutton_np, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[10]=new DropTarget(nextbutton_np, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[11]=new DropTarget(stopbutton_np, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[12]=new DropTarget(sufflebutton_np, DnDConstants.ACTION_COPY_OR_MOVE, null); 
            target[13]=new DropTarget(repetebutton_np, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[14]=new DropTarget(previousbutton_np, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[15]=new DropTarget(nowplayingbutton_np, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[16]=new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[17]=new DropTarget(subpanel_np, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[18]=new DropTarget(mainpanel_np, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[19]=new DropTarget(lowpanel, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[20]=new DropTarget(subpanel, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[21]=new DropTarget(mainpanel, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[22]=new DropTarget(buttonpanelnowplayong, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[23]=new DropTarget(buttonpanellib, DnDConstants.ACTION_COPY_OR_MOVE, null);
            target[24]=new DropTarget(playercanvas, DnDConstants.ACTION_COPY_OR_MOVE, null);
            try{
                for(int i=0;i<target.length;i++)
                {
                    target[i].addDropTargetListener(this);
                }
            }
            catch(Exception e){}
        }
        public void load()
        {
            openwith=false;
            int ki=openfile.showOpenDialog(librarywindow); 
            if(ki==JFileChooser.APPROVE_OPTION)
            {   
                playlist.removeAll();
                remover=0;
                try
                {
                    if(reselect==true)
                    {
                        tempstop();
                    }
                    file=openfile.getSelectedFiles();
                    add=true;
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(librarywindow,e,"ERROR from selection",JOptionPane.ERROR_MESSAGE);
                }
                createplayer();
            }
        }
        public void createplayer()
        {
            try
            {        
                playlist.setSelectedIndex(index);
                nowplaying(false);      
                reselect=true;
                currentplaying=index; 
                String[] optionns = {"audio-visual=visual", "effect-list=scope", "effect-width=1000", "effect-height=700"};
                player.prepareMedia(file[index].getAbsolutePath(),optionns);
                preparedmedia=true;
            }
            catch(Exception e)
            {
                System.out.println("error");
            }
            catch(OutOfMemoryError e){}
            
            if(isplay==true)
            {
                play();  
            }
            else
            {
                pause();
            }
            
            if(file!=null&&player!=null)
            {
                duration=(int)player.getLength();
                progress_lib.setMaximum(duration);
                progress_np.setMaximum(duration);
                boolean hhhh=true;
                while(hhhh)
                {
                    int volkk=(player.getVolume());
                    if(volkk>0)
                    {
                        hhhh=false;
                        player.setVolume(volume_lib.getValue());
                    }
                    player.setVolume(volume_lib.getValue());
                }
                player.setVolume(volume_lib.getValue());
            }
            Thread thk=new Thread(new Runnable(){public void run(){int tra=player.getAudioTrack();if(tra==0)tra+=1; addpopus();setlang(player.getAudioTrack(),tra);}});
            thk.start();
            if(add==true)
            {
                Thread tk=new Thread(new Runnable(){public void run(){additem();}});
                tk.start();
            }
        }
        public void play()//to play
        {
            if(file!=null&&player!=null&&preparedmedia==true)
            {                         
                player.start();                         
                player.setVolume(volume_lib.getValue());                        
                progress_set.start();                                  
                playing=true;
                playbutton.setIcon(pause);
                playbutton_np.setIcon(pause);
                stopbutton.setIcon(stop_disabled);
                stopbutton_np.setIcon(stop_disabled);
                info_text.setText("Playing");
                stopbutton.setRolloverIcon(stop_disabled_rollover);
                playbutton.setRolloverIcon(pause_rollover);
                playbutton_np.setRolloverIcon(pause_rollover);
                isplay=true;
            }
            else if(file!=null&&player!=null&&file.length>=0&&playing==false&&preparedmedia==false)
            {
                index=0;
                createplayer();
            }
        }
    
                    public void reset(){
                        if(file!=null&&player!=null){
                        index=0;
                        player.setPosition(0);
                        player.stop();
                        progress_set.stop();
                        progress_lib.setValue(0);
                        progress_np.setValue(0);
                        playing = false;
                        playbutton.setIcon(play);
                        playbutton_np.setIcon(play);
                        stopbutton.setIcon(stop_enabled);
                        stopbutton_np.setIcon(stop_enabled);
                        info_text.setText("Stopped");
                        stopbutton.setRolloverIcon(null); 
                        playbutton.setRolloverIcon(play_rollover);
                        playbutton_np.setRolloverIcon(play_rollover);
                        resetplay();                        
                        }
                    }
                    public void tempstop(){
                        if(file!=null&&player!=null){
                            index=0;
                            player.setPosition(0);
                            player.pause();
                            progress_set.stop();
                            progress_lib.setValue(0);
                            progress_np.setValue(0);
                            playing = false;
                            playbutton.setIcon(play);
                            playbutton_np.setIcon(play);
                            stopbutton.setIcon(stop_enabled);
                            stopbutton_np.setIcon(stop_enabled);
                            playbutton.setRolloverIcon(play_rollover);
                            playbutton_np.setRolloverIcon(play_rollover);
                            info_text.setText("Stopped");
                        }
                     }
                    public void pause(){               
                          if(file!=null&&player!=null)
                               {
                                    isplay=false;
                                    progress_set.stop();
                                    player.pause();
                                    playing = false;
                                    reselect=true;
                                    playbutton.setIcon(play);
                                    playbutton_np.setIcon(play);
                                    info_text.setText("Paused");
                                    playbutton.setRolloverIcon(play_rollover);
                                    playbutton_np.setRolloverIcon(play_rollover);
                            }
                      }
                
                    public void tick()
                    {    
                        if(file!=null&&player!=null)
                        {                         
                        if(progress_lib.getMaximum()==0){
                        duration=(int)player.getLength();
                        progress_lib.setMaximum(duration);
                        progress_np.setMaximum(duration);}
                        int ti=(int)player.getTime();
                        progress_lib.setValue(ti);
                        progress_np.setValue(ti);
                        if(player.isPlaying()==false){if(index==playlist.getModel().getSize()){index=0;}
                            next();}                 
                        }
                    }
                    public void time()
                    {
                        if(file!=null&&player!=null)
                            {                                
                                int pro=(int)player.getTime();
                                int tp=(int)player.getMediaMeta().getLength()/1000;  
                                int min=pro/1000;int sec=pro/1000;
                                int hour=pro/1000;
                                sec=sec%60;min=min/60;min=min%60;
                                if(style==1)
                                    {
                                        if((int)(tp/3600)==0)
                                        set=(String.valueOf(min+":"+sec));
                                        else
                                        set=(String.valueOf(hour/3600+":"+min+":"+sec));
                                    }
                                else if(style==2)
                                    {
                                        if((int)(tp/3600)==0)
                                        set=(String.valueOf(min+":"+sec+"/"+(int)((tp/60)%60)+":"+(int)((tp%60))));
                                        else
                                        set=(String.valueOf(hour/3600+":"+min+":"+sec+"/"+(int)(tp/3600)+":"+(int)((tp/60)%60)+":"+(int)((tp%60))));
                                    }
                                 else if(style==3)                                    
                                    {
                                        int pr=tp-pro;
                                        pr=pr/1000;
                                        if((int)(tp/3600)==0)
                                        set=(String.valueOf(((pr/60)%60)+":"+(pr%60)));
                                        else
                                        set=(String.valueOf(pr/3600+":"+((pr/60)%60)+":"+(pr%60)));
                                    }
                                 else {style=1;time();}
                                timelib.setText(set);
                                timenp.setText(set);
                            }
                    }

                            
            
                    public void skip(int value25){
                        if(file!=null&&player!=null)
                        {
                            position=value25;
                            player.setTime((long)position);
                            progress_lib.setValue(position);
                            progress_np.setValue(position);
                        }
                    }
                
                    public void nowplaying(boolean mode){
                            this.setVisible(true);
                            subpanel_np.setVisible(true);
                            buttonpanelnowplayong.setVisible(true);
                             if (mode==false)
                                {
                                    playmode=true;
                                    nowplaying.setVisible(true);
                                    player.enableOverlay(true);
                                    nowplaying.repaint();
                                    librarywindow.setVisible(false);
                                }
                            else 
                                {
                                    playmode=false;
                                    librarywindow.setVisible(true);
                                    nowplaying.setVisible(false);
                                }
                                ;
                            if(file!=null&&player!=null){
                                    playlist.setSelectedIndex(index);}
                    }
                
                    public void additem(){ 
                        listModel.removeAllElements();
                        String g,addit=null;
                           for(int add=0;add<file.length;add++)
                           { 
                               MediaMeta mm=mediaPlayerFactory.getMediaMeta(file[add].getAbsolutePath(),true);
                               addit=mm.getTitle();
                               addit.trim();
                               if(addit.equals(null)||addit==null||addit.equals(""))
                               {
                                   addit=file[add].getName().substring(0,file[add].getName().lastIndexOf("."));
                                }
                                if(addit.length()>24)
                                addit=addit.substring(0,24);
                                   addit=addit+"   "+givlen((int)mm.getLength()/1000);  
                                 listModel.addElement(addit);
                                }                                                               
                            listModel.addElement("                         ");
                            playlist.setModel(listModel);                
                            librarywindow.setSize(librarywindow.getSize());
                            
                        }                                   
    
                    public void vol1(){
                               volume_np.setValue(volume_lib.getValue());
                              volume=volume_lib.getValue();                              
                            }
               
                    public void vol2(){                    
                             volume_lib.setValue(volume_np.getValue());
                             volume=volume_np.getValue();
                             if(file!=null&&player!=null){      
                             player.setVolume((int)Math.ceil(volume));    
                                }  
                           }           
                
                    public void next()
                        {
                            if(file!=null&&player!=null)
                                {
                                    pause();
                                    add=false;
                                    ++index;
                                    progress_lib.setValue(0);
                                    progress_np.setValue(0);
                                    isplay=true;
                                    if(index<file.length)
                                    {
                                        if(file[index]==null)
                                            {
                                                index++;
                                            }
                                    }
                                    if(index>=file.length)
                                    {
                                        if(replay==true){index=0;}
                                        else reset();
                                    }
                                    if(file[index]!=null&&index<file.length){
                                        createplayer();
                                    }
                                }
                    }
                    public void pre(){
                            if(file!=null&&player!=null){
                                 pause();
                                 add=false;
                                 --index;
                                 progress_lib.setValue(0);
                                 progress_np.setValue(0);
                                 isplay=true;
                                 if(index<=-1){
                                      if(replay==true){index=file.length-1;}
                                     else reset();
                                 }
                                  if(index>-1){
                                 if(file[index]==null){index--;
                                  }
                                 }
                                 
                                 if(file[index]!=null&&index>-1){
                                     createplayer();
                                    }
                               }
                    }
                            
                    
                   public void close()
                    {
                        try{
                        DataOutputStream os=new DataOutputStream((new FileOutputStream("data4.txt")));
                        os.writeBoolean(false);
                        os.close();
                        os=new DataOutputStream((new FileOutputStream("sizeandlocation.txt")));
                        os.writeInt(new Point(librarywindow.getLocation()).x);
                        os.writeChar('\n');
                        os.writeInt(new Point(librarywindow.getLocation()).y);
                        os.writeChar('\n');
                        os.writeInt(librarywindow.getWidth());
                        os.writeChar('\n');       
                        os.writeInt(librarywindow.getHeight()); 
                        os.writeInt(new Point(nowplaying.getLocation()).x);
                        os.writeChar('\n');
                        os.writeInt(new Point(nowplaying.getLocation()).y);
                        os.writeChar('\n');
                        os.writeInt(nowplaying.getWidth());
                        os.writeChar('\n');       
                        os.writeInt(nowplaying.getHeight()); 
                    }catch(Exception e){}          
                    System.exit(-1);
                   } 
                        
                   public void skipselect(){
                       if(playlist.getSelectedIndex()!=playlist.getModel().getSize()-1)
                       {
                           index=playlist.getSelectedIndex();
                           add=false;
                           if(file!=null&&player!=null)
                           player.stop();
                           progress_lib.setValue(0);
                           progress_np.setValue(0);
                           isplay=true;                           
                           createplayer();
                        }
                    }
                   public void resetplay(){
                            if(file!=null&&player!=null){
                                add=false;
                                isplay=false;
                            }
                        }   
                   public void remove(){
                            String message="Sorry! You cannot delete a item which is playing now";
                            int a[]=playlist.getSelectedIndices();
                            for(int delid=0;delid<a.length;delid++){
                            if(currentplaying==a[delid])
                                JOptionPane.showMessageDialog(librarywindow,message,"ERROR",JOptionPane.ERROR_MESSAGE);
                            else
                                {                            
                                   int check=a[delid];
                                   file[check]=null;  
                                   listModel.removeElementAt(a[delid]);
                                   int it=0;
                                   for(int i=0;i<file.length;i++)
                                   {
                                       if(file[i]!=null)it++;
                                   }
                                   
                                   File tmp[]=file;
                                   file=new File[it];
                                   for(int i=0,y=0;i<tmp.length&&y<file.length;i++)
                                   {
                                       if(tmp[i]!=null){file[y]=tmp[i];y++;}
                                   }
                                   playlist.setModel(listModel);
                               }
                         }
                        } 
                            
                   public void timeset(int cl)
                   {
                       style=cl;       
                    }
                                
                   public void fullwin(int click)
                    {        
                        /*
                        player.setMarqueeText("$N");
                        player.setMarqueeSize(100);
                        player.setMarqueeOpacity(1.0f);
                        player.setMarqueeColour(Color.blue);
                        player.setMarqueeTimeout(30000000);
                        player.setMarqueePosition(libvlc_marquee_position_e.bottom);
                        player.enableMarquee(true);*/
                        
                        if(click==2)
                            { 
                                if(fullcheck)
                                {
                                    player.setFullScreen(false);
                                    player.enableOverlay(false);
                                    player.enableOverlay(true);
                                    fullcheck=false;                                
                                }
                                else
                                {
                                    player.setFullScreen(true);
                                    player.enableOverlay(false);
                                    player.enableOverlay(true);
                                    fullcheck=true;
                                }
                            }
                    }      
                    
                    public void mute()
                        {
                            if(file!=null&&player!=null)
                                {
                                    if(mutecheck==false)
                                        {
                                            volumeval=volume_lib.getValue();
                                            player.setVolume(0);
                                            volume_lib.setValue(0);
                                            volume_np.setValue(0);
                                            mutecheck=true;
                                        }
                                    else
                                        {
                                            mutecheck=false;
                                            volume_lib.setValue(volumeval);
                                            volume_np.setValue(volumeval);
                                            player.setVolume(volume_lib.getValue());
                                        }
                                }
                            else
                                {
                                    if(mutecheck==false)
                                        {
                                            volumeval=volume_lib.getValue();
                                            volume_lib.setValue(0);
                                            volume_np.setValue(0);
                                            mutecheck=true;
                                        }
                                    else
                                        {
                                            mutecheck=false;
                                            volume_lib.setValue(volumeval);
                                            volume_np.setValue(volumeval);
                                        }
                                }
                        }
                        
                    public void replay(){
                        if(replay==true)
                            {
                                repetebutton.setIcon(repeate_disabled);
                                repetebutton_np.setIcon(repeate_disabled);
                                replay=false;
                            }
                        else
                            {
                                repetebutton.setIcon(repete_enabled);
                                repetebutton_np.setIcon(repete_enabled);
                                replay=true;
                            }
                        }     
                        public void removecomponent()
                        {
                            int size=nowplaying.getWidth();
                            if(size<440)
                            {                   
                            buttonpanelnowplayong.remove(repetebutton_np);
                            buttonpanelnowplayong.remove(sufflebutton_np);                 
                            buttonpanelnowplayong.remove(volume_np);                            
                            this.remove(timenp);
                            }
                            if(size>440)
                            {
                            buttonpanelnowplayong.setBackground(Color.black);
                            buttonpanelnowplayong.add(repetebutton_np);
                            buttonpanelnowplayong.add(sufflebutton_np);
                            buttonpanelnowplayong.add(stopbutton_np);
                            buttonpanelnowplayong.add(previousbutton_np);
                            buttonpanelnowplayong.add(playbutton_np);
                            buttonpanelnowplayong.add(nextbutton_np);
                            this.add(timenp);
                            buttonpanelnowplayong.add(volume_np);
                            buttonpanelnowplayong.add(nowplayingbutton_np);
                            buttonpanelnowplayong.add(mutebutton_np);                   
                            subpanel_np.add(progress_np);
                            this.add(buttonpanelnowplayong);
                            mainpanel_np.add("Center",this);
                            mainpanel_np.add("North",subpanel_np);
                            nowplaying.add(mainpanel_np,"South");
                            progress_np.setUI(new CustomSliderUI(progress_np));                          
                            }
                        }
                        public void panelvisi()
                        {
                            this.setVisible(true);
                            playercanvas.repaint();
                            subpanel_np.setVisible(true);
                            visibility.stop();
                            visibiltycount=0;
                        }
                        public void panelvisi1()
                        {
                            this.setVisible(true);
                            playercanvas.repaint();
                            subpanel_np.setVisible(true);
                            visibility.start();
                            visibiltycount=0;
                        }
                        public void panelinvisi()
                        {
                            if(visicheck==false)
                            visibility.start();
                        }
                        public void visibile()
                        {
                            visibiltycount++;
                            if(visibiltycount==2)
                            {
                                this.setVisible(false);                                
                            }
                        }
                        public void assign(boolean bl)
                        {
                            visibility.stop();
                            visicheck=bl;
                            panelinvisi();
                        }
                        public int countlines()
                        {
                            int li=0;
                            try{
                                BufferedReader bri=new BufferedReader(new FileReader("support/extensions supported.dat"));
                                String g=null;
                                while((g=bri.readLine())!=null)
                                {
                                li++;
                                }                            
                            }
                            catch(Exception e){}
                            return li;
                        }
                        public void createext()
                        {
                            ext=new String[countlines()];
                            try{
                                int inty=0;
                                BufferedReader bri=new BufferedReader(new FileReader("support/extensions supported.dat"));
                                String g=null;
                                while((g=bri.readLine())!=null)
                                {
                                ext[inty]=g;
                                inty++;
                                }                            
                            }
                            catch(Exception e){}
                        }
                        public boolean isaudio(File f)
                        {
                            String pathn=f.getName();
                            String extension=pathn.substring(pathn.lastIndexOf(".")+1,pathn.length());
                            String audioext[]=null;
                            int li=0;
                            try{
                                BufferedReader bri=new BufferedReader(new FileReader("support/audio extensions supported.dat"));
                                String g=null;
                                while((g=bri.readLine())!=null)
                                {
                                li++;
                                }                            
                            }
                            catch(Exception e){}
                            audioext=new String[li];
                            try{
                                int inty=0;
                                BufferedReader bri=new BufferedReader(new FileReader("support/audio extensions supported.dat"));
                                String g=null;
                                while((g=bri.readLine())!=null)
                                {
                                audioext[inty]=g;
                                inty++;
                                }                            
                            }
                            catch(Exception e){}
                            boolean ret=false;
                            for(int i=0;i<audioext.length;i++)
                            if(audioext[i]==extension)ret=true;
                            else ret=false;
                            return ret;                            
                        } 
                        public boolean isvideo(File f)
                        {
                            String pathn=f.getName();
                            String extension=pathn.substring(pathn.lastIndexOf(".")+1,pathn.length());
                            String audioext[]=null;
                            int li=0;
                            try{
                                BufferedReader bri=new BufferedReader(new FileReader("support/video extensions supported.dat"));
                                String g=null;
                                while((g=bri.readLine())!=null)
                                {
                                li++;
                                }                            
                            }
                            catch(Exception e){}
                            audioext=new String[li];
                            try{
                                int inty=0;
                                BufferedReader bri=new BufferedReader(new FileReader("support/video extensions supported.dat"));
                                String g=null;
                                while((g=bri.readLine())!=null)
                                {
                                audioext[inty]=g;
                                inty++;
                                }                            
                            }
                            catch(Exception e){}
                            boolean ret=false;
                            for(int i=0;i<audioext.length;i++)
                            if(audioext[i]==extension)ret=true;
                            else ret=false;
                            return ret;                            
                        }
                        public  void background()
                        {
                            backgr++;
                            if(backgr==background.length){backgr=0;}
                            playercanvas.backg(background[backgr]);
                        }
                        public boolean addatthelast(String g)
                        {                              
                            boolean ret=false;
                            if(g!=null)
                            {
                                opencheck=true;
                                if(file!=null&&player!=null)
                                {
                                    ret=true;
                                    File temp[]=file;
                                    file=new File[temp.length+1];         
                                    for(int i=0;i<temp.length;i++)
                                    {
                                        file[i]=temp[i];          
                                    }
                                    file[file.length-1]=new File(g);
                                    index=file.length-1;
                                    add=true;
                                    if(file!=null&&player!=null)
                                    {
                                        isplay=true;
                                    }         
                                    openwithcount=0;       
                                    openwitht.start();             
                                }
                                else if(file==null)
                                {
                                    ret=true;
                                    file=new File[1];
                                    file[0]=new File(g);
                                    index=0;
                                    add=true;     
                                    openwithcount=0; 
                                    openwitht.start();     
                                }
                            }
                            return ret;
                        }
                        public void openwithcall()
                        {
                            if(openwithcount==2){          
                                int tmp=index;
                                index=tmp;
                                if(ACPlayer.tich==true)
                                {                                   
                                    createplayer();
                                    openwitht.stop();
                                }
                            }
                            openwithcount++;
                        }
                        public String givlen(int len)
                        {
                            int leni=len;
                            String ret="";
                            if((int)(leni/3600)==0)
                            {
                                ret=String.valueOf((int)((leni/60)%60)+":"+(int)((leni%60)));
                            }
                            else 
                                ret=String.valueOf((int)(leni/3600)+":"+(int)((leni/60)%60)+":"+(int)((leni%60)));
                            return ret;
                        }
                        public void addpopus()
                        {                           
                            if(file!=null&&player!=null)
                            {  
                                menu5.removeAll();
                                List<TrackDescription> l=player.getAudioDescriptions();
                                popups=new JRadioButtonMenuItem[player.getAudioTrackCount()];
                                for(int po=0;po<player.getAudioTrackCount();po++)
                                {
                                    final int to=l.get(po).id();
                                    final int te=po;
                                    System.out.println("current id =>"+to+" current index =>"+po);
                                    popups[po]=new JRadioButtonMenuItem(l.get(po).description());
                                    popups[po].addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){setlang(to,te);}});
                                    menu5.add(popups[po]);
                                }
                            }                            
                        }
                        public void setlang(int lan,int sel)
                        {
                            player.setAudioTrack(lan);
                            for(int po=0;po<popups.length;po++)
                            {
                                if(po==sel)popups[po].setSelected(true);
                                else popups[po].setSelected(false);
                            }
                        }
                        protected void processDrag(DropTargetDragEvent dtde) {
                            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                                dtde.acceptDrag(DnDConstants.ACTION_COPY);
                            } else {
                                dtde.rejectDrag();
                            }
                        }
            
                        @Override
                        public void dragEnter(DropTargetDragEvent dtde) {
                            processDrag(dtde);
                            SwingUtilities.invokeLater(new DragUpdate(true, dtde.getLocation()));
            
                        }
            
                        @Override
                        public void dragOver(DropTargetDragEvent dtde) {
                            processDrag(dtde);
                            SwingUtilities.invokeLater(new DragUpdate(true, dtde.getLocation()));
            
                        }
            
                        @Override
                        public void dropActionChanged(DropTargetDragEvent dtde) {
                        }
            
                        @Override
                        public void dragExit(DropTargetEvent dte) {
                            SwingUtilities.invokeLater(new DragUpdate(false, null));
            
                        }
            
                        @Override
                        public void drop(DropTargetDropEvent dtde) {  
                            SwingUtilities.invokeLater(new DragUpdate(false, null));  
                            final Transferable transferable = dtde.getTransferable();
                            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) 
                            {
                                dtde.acceptDrop(dtde.getDropAction());
                                boolean ch=false;
                                if(dtde.getDropTargetContext().getComponent()==playlist){ch=false;}else ch=true;
                                final boolean che=ch;
                                try {
                                    final List tr = (List) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                                    if (tr != null && tr.size() >= 0)
                                    {                               
                                        dtde.dropComplete(true);
                                        adddrag(tr,che);
                                    }
                                }                            
                                catch (Exception ex) 
                                {
                                    ex.printStackTrace();
                                }
                            }
                            else 
                            {
                                dtde.rejectDrop();
                            }
                        }
                        public void adddrag(List transferData,boolean ch )
                        {
                            try {
                                    File tmp[]=file;
                                    File fi[];
                                    if(file==null){index=0;}
                                        fi=new File[transferData.size()];
                                        int ii=0;
                                        for (Object item : transferData) {
                                            File fle = (File) item;
                                            if(fle.isDirectory())
                                            {
                                                fi=fle.listFiles();
                                                ii=fi.length-1;
                                            }
                                            else
                                            fi[ii]=fle;
                                            ii++;
                                        }
                                        int kr=0;
                                        if(tmp!=null&&player!=null)
                                        {
                                            file=new File[tmp.length+transferData.size()];
                                            for(int i=0;i<tmp.length;i++)
                                            file[i]=tmp[i];                                             
                                            for(int i=tmp.length,y=0;i<file.length&&y<fi.length;i++,y++)
                                            {
                                                file[i]=fi[y];
                                            }
                                            kr=tmp.length;
                                        }
                                        else
                                        {
                                            file=fi;
                                            kr=0;
                                        }
                                        if(ch==true){index=kr;isplay=true; int temp=index;tempstop();index=temp;createplayer();}
                                        Thread thk=new Thread(new Runnable(){public void run(){additem();}});
                                        thk.start();                                              
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                        }
                        public void getbackgrounds()
                        {
                            final File tempi[]=new File("images/np/background").listFiles();
                            background=new String[tempi.length];
                            new Thread(new Runnable(){public void run(){
                                for(int i=0;i<tempi.length;i++)
                                {
                                    background[i]=tempi[i].getAbsolutePath();
                                }}        
                            }).start();
                        }
                       public void overlay()
                       {
                           SwingUtilities.invokeLater(new Runnable(){public void run(){player.enableOverlay(false);player.enableOverlay(true);}});                        
                       }
                       int cv=0;
                       public void gesture(int xc)
                       {
                           if(xc<cv)
                           {
                               cv=xc;
                               if(volume_lib.getValue()!=volume_lib.getMaximum())
                               volume_lib.setValue(volume_lib.getValue()+1);
                            }
                            else
                            {
                                cv=xc;
                                if(volume_lib.getValue()!=volume_lib.getMinimum())                                
                                volume_lib.setValue(volume_lib.getValue()-1);
                            }
                        }
                        public void keyinterface(int keycode)
                        {
                            if(keycode==KeyEvent.VK_SPACE)
                            {
                                if(playing==false)
                                {
                                    play();
                                }
                                else
                                {
                                    pause();
                                }
                            }                            
                            else if(keycode==KeyEvent.VK_LEFT)
                            {
                                if(progress_lib.getValue()!=progress_lib.getMinimum())
                                {
                                    progress_lib.setValue(progress_lib.getValue()-2);
                                    progress_np.setValue(progress_lib.getValue()-2);
                                }
                            }                         
                            else if(keycode==KeyEvent.VK_RIGHT)
                            {
                                if(progress_lib.getValue()!=progress_lib.getMaximum())
                                {
                                    progress_lib.setValue(progress_lib.getValue()+2);
                                    progress_np.setValue(progress_lib.getValue()+2);
                                }
                            }                         
                            else if(keycode==KeyEvent.VK_UP)
                            {
                               if(volume_lib.getValue()!=volume_lib.getMaximum())
                               volume_lib.setValue(volume_lib.getValue()+1);
                            }                         
                            else if(keycode==KeyEvent.VK_DOWN)
                            {
                                if(volume_lib.getValue()!=volume_lib.getMinimum()) 
                                volume_lib.setValue(volume_lib.getValue()-1);
                            }
                        }
        }
