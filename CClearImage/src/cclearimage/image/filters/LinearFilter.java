/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclearimage.image.filters;

import cclearimage.image.RGBAImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import util.SeperableMatrix;

/**
 *
 * @author Wessel
 */
public abstract class LinearFilter extends Filter
{
    protected SeperableMatrix seperated;
    
    @Override
    public void filterImage(RGBAImage in, RGBAImage out, int startX, int startY, int endX, int endY)
    {
        if(seperated == null)
        {
            filterImage(in, out, filter, width, startX, startY, endX, endY);
        }
        else
        {
            RGBAImage temp = new RGBAImage(new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_4BYTE_ABGR));
            filterImage(in, temp, seperated.getVeca(), 1, startX, startY, endX, endY);
            filterImage(temp, out, seperated.getVecb(), width, startX, startY, endX, endY);
        }
    }
    
    @Override
    public void filterImage(RGBAImage in, RGBAImage out) throws Exception
    {
        if(seperated == null)
        {
            filterImage(in, out, filter, width);
        }
        else
        {
            RGBAImage temp = new RGBAImage(new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_4BYTE_ABGR));
            filterImage(in, temp, seperated.getVeca(), 1);
            filterImage(temp, out, seperated.getVecb(), width);
        }
    }
    
    protected void filterImage(RGBAImage in, RGBAImage out, double[] matrix, int matrixWidth)
    {
        filterImage(in, out, matrix, matrixWidth, 0, 0, in.getWidth(), in.getHeight());
    }
    
    protected void filterImage(RGBAImage in, RGBAImage out, double[] matrix, int matrixWidth, int startX, int startY, int endX, int endY)
    {
        if(in.getLength() != out.getLength())
            throw new IllegalArgumentException("In should be the same size as out");
        
        int matrixHeight = matrix.length / matrixWidth;
        int halfWidth = matrixWidth / 2;
        int halfHeight = matrixHeight / 2;
        
        for(int y = startY; y < endY; y++)
        {
            for(int x = startX; x < endX; x++)
            {
                double r = 0;
                double g = 0;
                double b = 0;
                double a = 0;
                double total = 0;
                for(int ix = -1 * halfWidth; ix < matrixWidth - halfWidth; ix++)
                {
                    int nx = x + ix;
                    for(int iy = -1 * halfHeight; iy < matrixHeight - halfHeight; iy++)
                    {
                        int ny = y + iy;
                        if(nx >= 0 && ny >= 0 && nx < in.getWidth() && ny < in.getHeight())
                        {
                            double filterValue = matrix[(ix + halfWidth) + matrixWidth * (iy + halfHeight)];
                        //System.out.println((ix + halfWidth) + "   " + (iy + halfHeight) + "   " + ix + "   " + iy + "  " + filterValue);
                            r += (in.getRed(nx, ny) * filterValue);
                            g += (in.getGreen(nx, ny) * filterValue);
                            b += (in.getBlue(nx, ny) * filterValue);
                            a += (in.getAlpha(nx, ny) * filterValue);
                            total += filterValue;
                        }
                    }
                    //System.out.println(x + "   " + y);
                    //if(x == 0)
                    //    System.out.println(r + "  " + g + "  " + b + "  " + a + "  " + total);
                    out.setRed(x, y, (byte)Math.round(r / total));
                    out.setGreen(x, y, (byte)Math.round(g / total));
                    out.setBlue(x, y, (byte)Math.round(b / total));
                    out.setAlpha(x, y, (byte)Math.round(a / total));
                }
            }
        }
    }
    
    @Override
    public Color getFilteredColor(RGBAImage image, int posX, int posY)
    {
        int halfWidth = width / 2;
        int halfHeight = getHeight() / 2;
        int r = 0;
        int g = 0;
        int b = 0;
        int a = 0;
        double total = 0;
        
        for(int y = -1 * halfHeight; y < getHeight() - halfHeight; y++)
        {
            int iy = posY + y;
            for(int x = -1 * halfWidth; x < getWidth() - halfWidth; x++)
            {
                int ix = posX + x;
                if(ix >= 0 && iy >= 0 && ix < image.getWidth() && iy < image.getHeight())
                {
                    int nx = x + halfWidth;
                    int ny = y + halfHeight;
                    double filterValue = filter[nx + getWidth()* ny];
                    r += (image.getRed(ix, iy) * filterValue);
                    g += (image.getGreen(ix, iy) * filterValue);
                    b += (image.getBlue(ix, iy) * filterValue);
                    
                    a += (image.getAlpha(ix, iy) * filterValue);
                    total += filterValue;
                }
            }
        }
        
        r = (int) Math.round(r / total);
        g = (int) Math.round(g / total);
        b = (int) Math.round(b / total);
        a = (int) Math.round(a / total);
        
        return new Color(r, g, b, a);
    }
    
    @Override
    public Color getFilteredColor(BufferedImage image, int posX, int posY)
    {
        return getFilteredColor(new RGBAImage(image), posX, posY);
    }
}
