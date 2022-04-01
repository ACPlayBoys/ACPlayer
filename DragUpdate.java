import mp3.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.dnd.*;
import javax.imageio.*;
import java.io.*;
import javax.swing.plaf.basic.BasicSliderUI;

import java.util.List;
import java.awt.datatransfer.*;

class DragUpdate implements Runnable {

            private boolean dragOver;
            private Point dragPoint;

            public DragUpdate(boolean dragOver, Point dragPoint) {
                this.dragOver = dragOver;
                this.dragPoint = dragPoint;
            }

            @Override
            public void run() {
            }
        }


