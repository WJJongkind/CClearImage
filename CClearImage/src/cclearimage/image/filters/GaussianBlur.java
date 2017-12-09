/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclearimage.image.filters;

import cclearimage.image.RGBAImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import util.ExtendedMath;
import util.SeperableMatrix;

/**
 *
 * @author Wessel
 */
public class GaussianBlur extends LinearFilter
{
    static RGBAImage in, out;
    static int i = 0;
    static long start;
    static Filter blur;
    public static void main(String[] args) throws Exception
    {
        File fl = new File("C:\\Users\\Wessel\\Desktop\\Knipsel.PNG");
        in = new RGBAImage(ImageIO.read(fl));
        blur = new Median(7);
        out = new RGBAImage(in.getWidth(), in.getHeight());
        blur.filterImage(in, out);
        
        JFrame f = new JFrame();
        f.setSize(1600, 900);
        f.add(new JPanel(){
            @Override
            public void paintComponent(Graphics g)
            {
                System.out.println("painting...");
                Graphics2D g2 = (Graphics2D) g;
                try{
                    blur.filterImage(in, out);
                }catch(Exception e){
                    e.printStackTrace();
                }
                g2.drawImage(out.getImage(), 0, 0, this);
                //g2.drawImage(RGBAImageConverter.copyToBufferedImage(in), 0, 0, this);
                /*for(int y = 0; y < out.getHeight(); y++)
                {
                    for(int x = 0; x < out.getWidth(); x++)
                    {
                        g2.setColor(new Color(out.getRed(x, y), out.getGreen(x, y), out.getBlue(x, y), out.getAlpha(x, y)));
                        g2.drawRect(x, y, 1, 1);
                    }
                }*/
                i++;
                repaint();
            }
        });
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        f.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            long end = System.currentTimeMillis();
            System.out.println("FPS: " + ((double)i / ((end - start) / 1000)));
        }
        });
        f.setVisible(true);
        start = System.currentTimeMillis();
    }
    
    public GaussianBlur(double stddev)
    {
        double areaOfInterest = stddev * 3;
        filter = new double[(int)Math.ceil(areaOfInterest * areaOfInterest)];
        this.width = (int)Math.ceil(areaOfInterest);
        
        int halfWidth = width / 2;
        int halfHeight = width / 2;
        for(int y = 0; y < width; y++)
        {
            int dy = Math.abs(y - halfHeight);
            for(int x = 0; x < width; x++)
            {
                int dx = Math.abs(x - halfWidth);
                double distance = Math.sqrt(dx * dx + dy * dy);
                    filter[x + width * y] = ExtendedMath.getGaussian(distance, stddev, 0);
            }
        }
        
        seperated = SeperableMatrix.create(filter, width);
    }
}
