/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclearimage.image;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 *
 * @author Wessel
 */
public class CAffineTransform 
{
    public static void rotateNearestNeighbour(Layer in, double angle)
    {
        BufferedImage img = in.getImage();
        
        AffineTransform trans = new AffineTransform();
        trans.rotate(Math.toRadians(angle), img.getWidth() / 2, img.getHeight() / 2);
        
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g22 = out.createGraphics();
        g22.drawImage(img, trans, null);
        
        in.setImage(out);
    }
    
    public static void rotateBilinear(Layer in, double angle)
    {
        BufferedImage img = in.getImage();
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        
        AffineTransform trans = new AffineTransform();
        trans.rotate(Math.toRadians(angle), img.getWidth() / 2, img.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BILINEAR);
        
        in.setImage(op.filter(img, out));
    }
    
    public static void rotateBicubic(Layer in, double angle)
    {
        BufferedImage img = in.getImage();
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        
        AffineTransform trans = new AffineTransform();
        trans.rotate(Math.toRadians(angle), img.getWidth() / 2, img.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BICUBIC);
        
        in.setImage(op.filter(img, out));
    }
   
    public static void shearNearestNeighbour(Layer in, double amountX, double amountY)
    {
        BufferedImage img = in.getImage();
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        
        AffineTransform trans = new AffineTransform();
        trans.shear(amountX, amountY);
        
        Graphics2D g2 = out.createGraphics();
        g2.drawImage(img, trans, null);
        
        in.setImage(out);
    }
   
    public static void shearBilinear(Layer in, double amountX, double amountY)
    {
        BufferedImage img = in.getImage();
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        
        AffineTransform trans = new AffineTransform();
        trans.shear(amountX, amountY);
        AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BILINEAR);
        
        in.setImage(op.filter(img, out));
    }
   
    public static void shearBicubic(Layer in, double amountX, double amountY)
    {
        BufferedImage img = in.getImage();
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        
        AffineTransform trans = new AffineTransform();
        trans.shear(amountX, amountY);
        AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BICUBIC);
        
        in.setImage(op.filter(img, out));
    }
    
    public static void scaleBilinear(Layer in, double amountX, double amountY)
    {
        BufferedImage img = in.getImage();
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        
        AffineTransform trans = new AffineTransform();
        trans.scale(amountX, amountY);
        AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BILINEAR);
        
        in.setImage(op.filter(img, out));
    }
    
    public static void scaleBicubic(Layer in, double amountX, double amountY)
    {
        BufferedImage img = in.getImage();
        BufferedImage out = new BufferedImage((int)Math.round(in.getWidth() * amountX), (int)Math.round(in.getHeight() * amountY), BufferedImage.TYPE_4BYTE_ABGR);
        
        AffineTransform trans = new AffineTransform();
        trans.scale(amountX, amountY);
        AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BICUBIC);
        
        in.setImage(op.filter(img, out));
    }
    
    public static void scaleNearestNeighbour(Layer in, double amountX, double amountY)
    {
        BufferedImage img = in.getImage();
        
        AffineTransform tx = new AffineTransform();
        tx.scale(amountX, amountY);
        
        BufferedImage out = new BufferedImage((int)Math.round(in.getWidth() * amountX), (int)Math.round(in.getHeight() * amountY), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = out.createGraphics();
        g2.drawImage(img, tx, null);
        
        in.setImage(out);
    }
    
    public static void translate(Layer in, double amountX, double amountY)
    {
        BufferedImage img = in.getImage();
        
        int newWidth = (int) Math.round(img.getWidth() * amountX);
        int newHeight = (int) Math.round(img.getHeight() * amountY);
        BufferedImage out = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_4BYTE_ABGR);
        
        AffineTransform tx = new AffineTransform();
        tx.translate(amountX, amountY);
        
        Graphics2D g2 = out.createGraphics();
        g2.drawImage(img, tx, null);
        
        in.setImage(out);
    }
    
    /*private static BufferedImage image;
    private static int i = 0;
    private static int count = 0;
    private static long start, end;
    public static void main(String[] args)
    {
        image = new BufferedImage(1280, 720, BufferedImage.TYPE_4BYTE_ABGR);
        
        for(int x = 0; x < image.getWidth(); x++)
            for(int y = 0; y < image.getHeight(); y++)
                image.setRGB(x, y, new Color(0, 255, 0, 128).getRGB());
        
        double dy = image.getHeight() / (double)image.getWidth();
        for(int x = 0; x < image.getWidth(); x++)
            image.setRGB(x, (int)Math.round(x * dy), Color.BLACK.getRGB());
        
        JFrame frame = new JFrame();
        frame.setSize(1280, 720);
        JComponent pane = new JComponent(){
            @Override
            public void paintComponent(Graphics g)
            {
                try{
                    i++;
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    //g2.setColor(Color.BLACK);
                    //g2.fillRect(0, 0, image.getWidth() / 2, image.getHeight() / 2);
                    //BufferedImage mean = scaleMean(new Layer(image), 3, 3);
                    //System.out.println(mean.getWidth());
                    //g2.drawImage(mean, 0, 0, this);
                    g2.drawImage(scaleMean(new Layer(image), 5, 5), 0, 0, this);
                }catch(Exception e){
                    e.printStackTrace();
                }
                
                //UNCOMMENT CODE BELOW IF YOU WANT TO TEST JAVA'S AFFINETRANSFORM
                //AffineTransform trans = new AffineTransform();
                //trans.rotate(Math.toRadians(30), image.getWidth() / 2, image.getHeight() / 2);
                //BufferedImage rotated = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                //Graphics2D g22 = rotated.createGraphics();
                //g22.drawImage(image, trans, null);
                //g2.drawImage(rotated, null, null);
                if(i > 5)
                {
                     end = System.currentTimeMillis();
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }
                else
                    repaint();
            }
        };
        
        frame.add(pane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            double time = (end - start) / 1000.0;
            System.out.println("FPS: " + (i / time));
            count++;
            i = 0;
            if(count < 10)
                main(null);
        }
        });
        
        start = System.currentTimeMillis();
        frame.setVisible(true);
    }*/
}
