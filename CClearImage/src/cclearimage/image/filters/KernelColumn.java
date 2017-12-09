/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclearimage.image.filters;

import java.util.ArrayList;

/**
 *
 * @author Wessel
 */
public class KernelColumn 
{
    private int[] values = new int[256];
    ArrayList<Integer> queue = new ArrayList<>();
    
    public void addValue(int value)
    {
        values[value]++;
        queue.add(value);
    }
    
    public void removeValue()
    {
        //System.out.println("REMOVING VAL..." + values.size());
        values[queue.get(0)]--;
        queue.remove(0);
        //System.out.println("VAL REMOVED..." + values.size());
    }
    
    public int[] getValues()
    {
        return values;
    }
}
