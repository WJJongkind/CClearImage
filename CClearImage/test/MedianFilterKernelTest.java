
import cclearimage.image.filters.FilterKernel;
import cclearimage.image.filters.KernelColumn;
import java.util.Collections;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wessel
 */
public class MedianFilterKernelTest 
{
    private FilterKernel kernel;
    private int[] startingValues;
    private int[] sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private KernelColumn column1, column2;
    
    @Before
    public void setup()
    {
        kernel = new FilterKernel()
        {
            @Override
            public int getValue() 
            {
                Collections.sort(values);
                return values.get(values.size() / 2);
            }
        };
        
        startingValues = new int[9];
        startingValues[0] = 1;//{1, 5, 3, 7, 2, 2, 5, 1, 0};
        startingValues[1] = 3;//{1, 5, 3, 7, 2, 2, 5, 1, 0};
        startingValues[2] = 2;//{1, 5, 3, 7, 2, 2, 5, 1, 0};
        startingValues[3] = 4;//{1, 5, 3, 7, 2, 2, 5, 1, 0};
        startingValues[4] = 6;//{1, 5, 3, 7, 2, 2, 5, 1, 0};
        startingValues[5] = 5;//{1, 5, 3, 7, 2, 2, 5, 1, 0};
        startingValues[6] = 9;//{1, 5, 3, 7, 2, 2, 5, 1, 0};
        startingValues[7] = 8;//{1, 5, 3, 7, 2, 2, 5, 1, 0};
        startingValues[8] = 7;//{1, 5, 3, 7, 2, 2, 5, 1, 0};
        
        column1 = new KernelColumn();
        
        for(int i : startingValues)
            column1.addValue(i);
        kernel.addColumn(column1);
        
        column2 = new KernelColumn();
        column2.addValue(5);
        column2.addValue(8);
        column2.addValue(6);
        column2.addValue(9);
        column2.addValue(7);
        column2.addValue(11);
        column2.addValue(13);
        column2.addValue(12);
        column2.addValue(10);
    }
    
    @Test
    public void testGetValue()
    {
        assertEquals(kernel.getValue(), 5);
        kernel.addColumn(column2);
        assertEquals(kernel.getValue(), 7);
        kernel.removeColumn();
        System.out.println(kernel.getValues());
        assertEquals(kernel.getValue(), 9);
        
    }
}
