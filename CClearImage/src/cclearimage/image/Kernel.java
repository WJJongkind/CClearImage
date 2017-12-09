/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicum7;

import java.awt.Point;
import java.awt.image.Raster;
import java.util.ArrayList;

/**
 *
 * @author MemeMeister
 */
public class Kernel
{
    private final boolean[][] kernel;
    private final int originX, originY;
    private Point position;
    
    public Kernel(boolean[][] kernel, int originX, int originY)
    {
        this.originX = originX;
        this.originY = originY;
        this.kernel = kernel;
    }
    
    public boolean hits(Raster raster, int greyVal)
    {
        for(int yi = Math.max(0, position.y - originY); yi < position.y + getHeight() - originY && yi < raster.getHeight(); yi++)
        {
            for(int xi = Math.max(0, position.x - originX); xi < position.x + getWidth() - originX && xi < raster.getWidth(); xi++)
            {
                //System.out.println(xi + "  " + yi);
                if(raster.getSample(xi, yi, 0) == greyVal)
                    return true;
            }
        }
        
        return false;
    }
    
    public boolean fits(Raster raster, int greyVal)
    {
        for(int yi = position.y - originY; yi < position.y + getHeight() - originY; yi++)
        {
            for(int xi = position.x - originX; xi < position.x + getWidth() - originX; xi++)
            {
                if(yi < 0 || xi < 0 || yi >= raster.getHeight() || xi >= raster.getWidth())
                    return false;
                else if(raster.getSample(xi, yi, 0) != greyVal)
                    return false;
            }
        }
        
        return true;
    }
    
    private int getWidth()
    {
        return kernel[0].length;
    }
    
    private int getHeight()
    {
        return kernel.length;
    }
    
    public void setPosition(Point position)
    {
        this.position = position;
    }
}
