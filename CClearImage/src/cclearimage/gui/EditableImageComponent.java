/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclearimage.gui;

import cclearimage.image.Layer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Wessel
 */
public class EditableImageComponent extends JPanel implements MouseListener, MouseMotionListener
{
    private final ArrayList<Layer> layers;
    
    private final BufferedImage BACKGROUND = new BufferedImage(256, 256, BufferedImage.TYPE_BYTE_GRAY);
    private BufferedImage combined;
    private boolean track = false, ready = true;
    private Point previousLocation = null;
    private final int[] EMPTY_COLORS = {
        255,
        128
    };
    private final double EMPTY_SIZE = 30;
    private Dimension oldSize;
    
    public EditableImageComponent()
    {
        layers = new ArrayList<>();
        layers.add(new Layer(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB)));
        init();
    }
    
    public EditableImageComponent(String path) throws Exception
    {
        this(new File(path));
    }
    
    public EditableImageComponent(File f) throws Exception
    {
        layers = new ArrayList<>();
        layers.add(new Layer(ImageIO.read(f)));
        init();
    }
    
    private void init()
    {
        setSizes(layers.get(0).getWidth(), layers.get(0).getHeight());
        super.addMouseListener(this);
        super.addMouseMotionListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        if(oldSize == null || !oldSize.equals(getSize()))
        {
            oldSize = getSize();
            drawBackground();
        }
        
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(BACKGROUND, null, null);
        
        g2.drawImage(combined, null, null);
        ready = true;
    }
    
    private void drawBackground()
    {
        int activeColor = EMPTY_COLORS[0];
        int offset = 0;
        WritableRaster raster = (WritableRaster) BACKGROUND.getRaster();
        for(int y = 0; y < combined.getHeight(); y++)
        {
            offset = (int)Math.round((y % EMPTY_SIZE)  / EMPTY_SIZE);
            System.out.println(y % EMPTY_SIZE + 1 + "   " +offset);
            for(int x = 0; x < combined.getWidth(); x++)
            {
                //System.out.println(x % EMPTY_SIZE + 1 + "   " + (x % (EMPTY_SIZE) / (EMPTY_SIZE)));
                activeColor = EMPTY_COLORS[((int)Math.round((x % (EMPTY_SIZE)  / (EMPTY_SIZE))) + offset) % 2];
                raster.setSample(x, y, 0, activeColor);
            }
        }
    }
    
    private void setSizes(int width, int height)
    {
        Dimension d = new Dimension(width, height);
        super.setSize(width, height);
        super.setMinimumSize(d);
        super.setPreferredSize(d);
        super.setMaximumSize(d);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        combined.setRGB(e.getPoint().x, e.getPoint().y, Color.BLACK.getRGB());
        track = true;
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        track = false;
        previousLocation = null;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        if(track)
        {
            Point p = e.getPoint();
            
            if(!layers.get(0).containsLocation(p))
                previousLocation = null;
            
            if(previousLocation == null)
                line(p.x, p.y, p.x, p.y);
            else
            {
                line(previousLocation.x, previousLocation.y, p.x, p.y);
            }
            previousLocation = p;
            
            if(ready)
            {
                ready = false;
                repaint();
            }
        }
    }
    
    private void line(int x,int y,int x2, int y2) 
        {
            int w = x2 - x ;
            int h = y2 - y ;
            int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
            if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
            if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
            if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
            int longest = Math.abs(w) ;
            int shortest = Math.abs(h) ;
            if (!(longest>shortest)) {
                longest = Math.abs(h) ;
                shortest = Math.abs(w) ;
                if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
                dx2 = 0 ;            
            }
            int numerator = longest >> 1 ;
            for (int i=0;i<=longest;i++) {
                if(containsPoint(new Point(x, y)))
                    image.setRGB(x, y, Color.BLACK.getRGB());
                //world.getMap().addPixel(new Pixel(x,y,Color.BLACK)) ;
                numerator += shortest ;
                if (!(numerator<longest)) {
                    numerator -= longest ;
                    x += dx1 ;
                    y += dy1 ;
                } else {
                    x += dx2 ;
                    y += dy2 ;
                }
            }
        }
    
    @Override
    public void mouseMoved(MouseEvent e) 
    {
    }
}
