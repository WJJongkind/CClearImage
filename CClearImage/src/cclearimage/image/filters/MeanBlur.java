/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclearimage.image.filters;

import util.SeperableMatrix;

/**
 *
 * @author Wessel
 */
public class MeanBlur extends LinearFilter
{
    public MeanBlur(double radius)
    {
        int areaOfInterest = (int)Math.ceil(radius) * 2;
        filter = new double[areaOfInterest * areaOfInterest];
        this.width = areaOfInterest;
        
        for(int y = 0; y < areaOfInterest; y++)
            for(int x = 0; x < areaOfInterest; x++)
                filter[x + areaOfInterest * y] = 1;
        
        seperated = SeperableMatrix.create(filter, width);
        //seperated.makeCircular();
        //for(int i = 0; i < seperated.getVecb().length; i++)
         //   System.out.println(seperated.getVecb()[i]);
    }
}
