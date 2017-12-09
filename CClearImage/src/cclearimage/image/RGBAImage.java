/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclearimage.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 *
 * @author Wessel
 */
public class RGBAImage {
    public static final int R = 3;
    public static final int G = 2;
    public static final int B = 1;
    public static final int A = 0;
    private BufferedImage image;
    private byte[] rgba;
    private int width;
    private int height;
    
    public RGBAImage(BufferedImage image)
    {
        this.image = image;
        rgba = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
        this.width = image.getWidth();
        this.height = image.getHeight();
    }
    
    public RGBAImage(int width, int height)
    {
        image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        rgba = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
        this.width = width;
        this.height = height;
        //rgba = new int[width * height * A];
    }
    
    public void setRed(int x, int y, byte val)
    {
        rgba[(x + y * width) * 4 + R] = val;
    }
    
    public void setGreen(int x, int y, byte val)
    {
        rgba[(x + y * width) * 4 + G] = val;
    }
    
    public void setBlue(int x, int y, byte val)
    {
        rgba[(x + y * width) * 4 + B] = val;
    }
    
    public void setAlpha(int x, int y, byte val)
    {
        rgba[(x + y * width) * 4 + A] = val;
    }
    
    public int[] getColorValues()
    {
        int[] colorValues = new int[width * height];
        
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
            {
                if(x == 0)
                {
                    //System.out.println(getRed(x, y) + "  " + getGreen(x, y) + "  " + getBlue(x, y) + " " + getAlpha(x, y));
                    colorValues[x + y * width] = new Color(getRed(x, y), getGreen(x, y), getBlue(x, y), getAlpha(x, y)).getRGB();
                    //System.out.println(colorValues[x + y * width]);
                    
                }
                else
                    colorValues[x + y * width] = new Color(getRed(x, y), getGreen(x, y), getBlue(x, y), getAlpha(x, y)).getRGB();
            }
        
        return colorValues;
    }
    
    public int getRed(int x, int y)
    {
        return rgba[(x + y * width) * 4 + R] & 0xFF;
    }
    
    public int getGreen(int x, int y)
    {
        return rgba[(x + y * width) * 4 + G] & 0xFF;
    }
    
    public int getBlue(int x, int y)
    {
        return rgba[(x + y * width) * 4 + B] & 0xFF;
    }
    
    public int getAlpha(int x, int y)
    {
        return rgba[(x + y * width) * 4 + A] & 0xFF;
    }
    
    public byte getRedByte(int x, int y)
    {
        return rgba[(x + y * width) * 4 + R];
    }
    
    public byte getGreenByte(int x, int y)
    {
        return rgba[(x + y * width) * 4 + G];
    }
    
    public byte getBlueByte(int x, int y)
    {
        return rgba[(x + y * width) * 4 + B];
    }
    
    public byte getAlphaByte(int x, int y)
    {
        return rgba[(x + y * width) * 4 + A];
    }
    
   // private int getInt(byte value)
    //{
      //  if(value < 0)
            
   // }
    
    public int getLength()
    {
        return rgba.length;
    }

    public byte[] getRgba() {
        return rgba;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public BufferedImage getImage()
    {
        return image;
    }
}
