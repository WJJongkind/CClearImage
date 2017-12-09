/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclearimage.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import util.RGBAImageConverter;

/**
 *
 * @author Wessel
 */
public class Layer 
{
    private BufferedImage image;
    private double opacity;
    private CAffineTransform scale;
    private CAffineTransform rotate;
    private CAffineTransform window;
    

    public Layer(BufferedImage image)
    {
        this.image = image;
    }

    public void setWidth(int width) {
        setSize(width, image.getHeight());
    }

    public void setHeight(int height) {
        setSize(image.getWidth(), height);
    }
    
    public void setSize(int width, int height)
    {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = newImage.createGraphics();
        g2.drawImage(image, null, null);
        image = newImage;
    }

    public void setPixel(int x, int y, Color color) {
        if(containsLocation(x, y))
            image.setRGB(x, y, color.getRGB());
    }
    
    public void setImage(BufferedImage image)
    {
        this.image = image;
    }
    
    public void setRGBAImage(RGBAImage image)
    {
        this.image = RGBAImageConverter.copyToBufferedImage(image);
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public RGBAImage getRGBAImage()
    {
        return RGBAImageConverter.copyToRGBA(image);
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public Color getPixel(int x, int y) {
        if(containsLocation(x, y))
            return new Color(image.getRaster().getSample(x, y, 0));
        else
            return null;
    }
    
    public boolean containsLocation(int x, int y)
    {
        return x >= 0 && y >= 0 && x < getWidth() && y < getHeight();
    }
}
