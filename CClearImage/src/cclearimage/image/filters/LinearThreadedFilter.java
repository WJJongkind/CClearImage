/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclearimage.image.filters;

import cclearimage.image.RGBAImage;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;
import util.SeperableMatrix;

/**
 *
 * @author Wessel
 */
public class LinearThreadedFilter extends LinearFilter
{
    private final LinearFilter actualFilter;
    public LinearThreadedFilter(LinearFilter filter)
    {
        this.actualFilter = filter;
    }
    
    @Override
    public void filterImage(RGBAImage in, RGBAImage out) throws Exception 
    {
        filterImage(in, out, 0, 0, in.getWidth(), in.getHeight());
    }
    
    @Override
    public void filterImage(RGBAImage in, RGBAImage out, int startX, int startY, int endX, int endY)
    {
        try{
            final RGBAImage tempImage = new RGBAImage(new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_4BYTE_ABGR));
            int coreCount = Runtime.getRuntime().availableProcessors();

            int width = endX - startX;
            int height = endY - startY;
            final int blockWidth = width / coreCount;

            final CountDownLatch latchA;
            final CountDownLatch latchB;
            if(actualFilter.seperated != null)
            {
                latchA = new CountDownLatch(coreCount);
                latchB = new CountDownLatch(coreCount);
            }
            else
            {
                latchA = new CountDownLatch(coreCount);
                latchB = null;
            }

            for(int i = 1; i < coreCount; i++)
            {
                final int index = i;
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        filterImage(in, out, tempImage, (index - 1) * blockWidth, startY, index * blockWidth, height, latchA, latchB);
                    }
                }).start();
            }

            filterImage(in, out, tempImage, (coreCount - 1) * blockWidth, startY, in.getWidth(), height, latchA, latchB);
            if(latchB != null)
                latchB.await();
            else
                latchA.await();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void filterImage(RGBAImage in, RGBAImage out, RGBAImage temp, int startX, int startY, int endX, int endY, CountDownLatch latchA, CountDownLatch latchB)
    {
        try{
            if(actualFilter.seperated == null)
            {
                System.out.println("Starting sequence");
                actualFilter.filterImage(in, out, actualFilter.filter, actualFilter.width, startX, startY, endX, endY);
                latchA.countDown();
            }
            else
            {
                //System.out.println("Starting seperated sequence");
                actualFilter.filterImage(in, temp, actualFilter.seperated.getVeca(), 1, startX, startY, endX, endY);
                //System.out.println("stage A completed");
                latchA.countDown();
                latchA.await();
                //System.out.println("stage B starting... ");
                actualFilter.filterImage(temp, out, actualFilter.seperated.getVecb(), actualFilter.width, startX, startY, endX, endY);
                //System.out.println("Stage B completed");
                latchB.countDown();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
