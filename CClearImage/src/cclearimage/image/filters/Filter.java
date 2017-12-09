/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclearimage.image.filters;

import cclearimage.image.RGBAImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author Wessel
 */
public abstract class Filter
{
    protected double[] filter;
    protected int width;
    
    public abstract void filterImage(RGBAImage in, RGBAImage out) throws Exception;
    
    public abstract void filterImage(RGBAImage in, RGBAImage out, int startX, int startY, int endX, int endY);
    
    public abstract Color getFilteredColor(RGBAImage image, int posX, int posY);
    
    public abstract Color getFilteredColor(BufferedImage image, int posX, int posY);
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return filter.length / width;
    }
}
