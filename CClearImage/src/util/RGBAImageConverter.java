/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import cclearimage.image.RGBAImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

/**
 *
 * @author Wessel
 */
public class RGBAImageConverter 
{
    /*public static RGBAImage copyToRGBA(BufferedImage image)
    {
        RGBAImage data = new RGBAImage(image.getWidth(), image.getHeight());
        
        for(int y = 0; y < image.getHeight(); y++)
        {
            for(int x = 0; x < image.getWidth(); x++)
            {
                Color c = new Color(image.getRGB(x, y), true);
                data.setRed(x, y, c.getRed());
                data.setGreen(x, y, c.getGreen());
                data.setBlue(x, y, c.getBlue());
                data.setAlpha(x, y, c.getAlpha());
            }
        }
        
        return data;
    }
    */
    public static BufferedImage copyToBufferedImage(RGBAImage data)
    {
        BufferedImage image = new BufferedImage(data.getWidth(), data.getHeight(), BufferedImage.TYPE_INT_ARGB);

        int[] a = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        int[] c = data.getColorValues();
        for(int i = 0; i < c.length; i++)
            a[i] = c[i];
            
       // image.getRaster().setDataElements(0, 0, data.getWidth(), data.getHeight(), data.getColorValues());
        return image;
    }
}
