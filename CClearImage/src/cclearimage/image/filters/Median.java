/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclearimage.image.filters;

import cclearimage.image.RGBAImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import util.RGBAImageConverter;

/**
 *
 * @author Wessel
 */
public class Median extends LinearFilter
{
    private int radius;
    
    public Median(int radius)
    {
        this.radius = radius;
    }
    
    @Override
    public void filterImage(RGBAImage in, RGBAImage out, double[] filter, int filterWidth, int startx, int starty, int endx, int endy)
    {
        filterImage(in, out, startx, starty, endx, endy);
    }
    
    @Override
    public void filterImage(RGBAImage in, RGBAImage out, int startx, int starty, int endx, int endy)
    {
        //INITIALIZATION
        MedianKernel kernelR, kernelG, kernelB, kernelA;
        kernelR = new MedianKernel();
        kernelG = new MedianKernel();
        kernelB = new MedianKernel();
        kernelA = new MedianKernel();
        ArrayList<KernelColumn> colr = new ArrayList<>();
        ArrayList<KernelColumn> colg = new ArrayList<>();
        ArrayList<KernelColumn> colb = new ArrayList<>();
        ArrayList<KernelColumn> cola = new ArrayList<>();
        for(int x = startx - radius; x < endx + radius; x++)
        {
            KernelColumn r = new KernelColumn();
            KernelColumn g = new KernelColumn();
            KernelColumn b = new KernelColumn();
            KernelColumn a = new KernelColumn();
            colr.add(r);
            colg.add(g);
            colb.add(b);
            cola.add(a);
            
            int nx = Math.abs(x);
            if(nx >= in.getWidth())
                nx = nx - (nx % in.getWidth() + 1);
            for(int y = starty - radius; y < starty + radius; y++)
            {
                int ny = Math.abs(y);
                
                if(ny >= in.getHeight())
                    ny = ny - (ny % in.getHeight() + 1);
                r.addValue(in.getRed(nx, ny));
                g.addValue(in.getGreen(nx, ny));
                b.addValue(in.getBlue(nx, ny));
                a.addValue(in.getAlpha(nx, ny));
            }
            
            if(x <= startx + radius)
            {
                kernelR.addColumn(r);
                kernelG.addColumn(g);
                kernelB.addColumn(b);
                kernelA.addColumn(a);
            }
        }
        //System.out.println(kernelR.getValues().size());
        
       // System.out.println((endx - startx) + "  " + colr.size());
        
        //FIRST FILTER IS NESSECARY
        out.setRed(startx, starty, (byte)kernelR.getValue());
        out.setGreen(startx, starty, (byte)kernelG.getValue());
        out.setBlue(startx, starty, (byte)kernelB.getValue());
        out.setAlpha(startx, starty, (byte)kernelA.getValue());
        
        //FILTERING
        for(int y = starty; y < endy; y++)
        {
            for(int x = startx + 1; x < endx; x++)
            {
                int nx = x - startx + radius - 1;
               // if(nx == 159)
                  //  System.out.println(colr.size() + "  " + startx  + "  " + endx);
                colr.get(nx).removeValue();
                colg.get(nx).removeValue();
                colb.get(nx).removeValue();
                cola.get(nx).removeValue();
                
                int ix = x + radius;
                if(ix >= in.getWidth())
                    ix = ix - (ix % in.getWidth() + 1);
                
                int iy = y + radius;
                if(iy >= in.getHeight())
                    iy = iy - (iy % in.getHeight() + 1);
                
                colr.get(nx).addValue(in.getRed(ix, iy));
                colg.get(nx).addValue(in.getGreen(ix, iy));
                colb.get(nx).addValue(in.getBlue(ix, iy));
                cola.get(nx).addValue(in.getAlpha(ix, iy));
                
                kernelR.removeColumn();
                kernelG.removeColumn();
                kernelB.removeColumn();
                kernelA.removeColumn();
                
                kernelR.addColumn(colr.get(nx));
                kernelG.addColumn(colg.get(nx));
                kernelB.addColumn(colb.get(nx));
                kernelA.addColumn(cola.get(nx));
                
                out.setRed(x, y, (byte)kernelR.getValue());
                out.setGreen(x, y, (byte)kernelG.getValue());
                out.setBlue(x, y, (byte)kernelB.getValue());
                out.setAlpha(x, y, (byte)kernelA.getValue());
            }
            
            //KERNEL WILL MOVE BACK TO THE LEFT
            kernelR.removeColumns();
            kernelG.removeColumns();
            kernelB.removeColumns();
            kernelA.removeColumns();
            
            if(y != endy - 1)
            {
                for(int x = startx - radius; x < startx + radius; x++)
                {
                    int nx = x - startx + radius;

                    colr.get(nx).removeValue();
                    colg.get(nx).removeValue();
                    colb.get(nx).removeValue();
                    cola.get(nx).removeValue();

                    int ix = Math.abs(x);
                    int iy = y + radius + 1;
                    if(iy >= in.getHeight())
                        iy = iy - (iy % in.getHeight() + 1);

                    colr.get(nx).addValue(in.getRed(ix, iy));
                    colg.get(nx).addValue(in.getGreen(ix, iy));
                    colb.get(nx).addValue(in.getBlue(ix, iy));
                    cola.get(nx).addValue(in.getAlpha(ix, iy));

                    kernelR.addColumn(colr.get(nx));
                    kernelG.addColumn(colg.get(nx));
                    kernelB.addColumn(colb.get(nx));
                    kernelA.addColumn(cola.get(nx));
                }

                out.setRed(startx, y + 1, (byte)kernelR.getValue());
                out.setGreen(startx, y + 1, (byte)kernelG.getValue());
                out.setBlue(startx, y + 1, (byte)kernelB.getValue());
                out.setAlpha(startx, y + 1, (byte)kernelA.getValue());
            }
        }
    }

    @Override
    public Color getFilteredColor(BufferedImage image, int x, int y)
    {
        return getFilteredColor(new RGBAImage(image), x, y);
    }

    @Override
    public Color getFilteredColor(RGBAImage in, int x, int y)
    {
        /*
        ArrayList<Integer> r = new ArrayList<>();
        ArrayList<Integer> g = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        ArrayList<Integer> a = new ArrayList<>();

        for(int ix = -1 * halfWidth; ix < width - halfWidth; ix++)
        {
            int nx = x - ix;
            for(int iy = -1 * halfHeight; iy < height - halfHeight; ix++)
            {
                int ny = y - iy;
                if(nx > 0 && ny > 0 && nx < width && ny < height)
                {
                    r.add(in.getRed(nx, ny));
                    g.add(in.getGreen(nx, ny));
                    b.add(in.getBlue(nx, ny));
                    a.add(in.getAlpha(nx, ny));
                }
            }
        }

        Collections.sort(r);
        Collections.sort(g);
        Collections.sort(b);
        Collections.sort(a);

        int rc = r.get(r.size() / 2);
        int gc = g.get(g.size() / 2);
        int bc = b.get(b.size() / 2);
        int ac = a.get(a.size() / 2);

        return new Color(rc, gc, bc, ac);*/
        return null;
    }
    
    private class MedianKernel extends FilterKernel
    {
        @Override
        public int getValue() 
        {
            //System.out.println(values.size());
            //System.out.println(columns.size());
            //values = new ArrayList<>();
            //for(KernelColumn c : columns)
             //   values.addAll(c.getValues());
            //System.out.println(name + values.size());
            int cummul = 0;
            int valCount = ((radius * 2 + 1) * (radius * 2 + 1)) / 2 + 1;
            //System.out.println(valCount);
            for(int i = 0; i < values.length; i++)
            {
                cummul += values[i];
                if(cummul >= valCount)
                    return i;
            }
            System.out.println("REUTURNING -1");
            return -1;
        }
        
    }
}
