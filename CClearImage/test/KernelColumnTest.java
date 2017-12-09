
import cclearimage.image.filters.KernelColumn;
import java.util.ArrayList;
import static junit.framework.Assert.assertTrue;
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
public class KernelColumnTest 
{
    private KernelColumn column;
    private ArrayList<Integer> testVals = new ArrayList<>();
    private ArrayList<Integer> subset = new ArrayList<>();
        
    @Before
    public void setup()
    {
        column = new KernelColumn();
        for(int i = 0; i < 30; i++)
        {
            column.addValue(i);
            testVals.add(i);
            if(i >= 15)
                subset.add(i);
        }
    }
    
    @Test
    public void testAddValue()
    {
        column.getValues().clear();
        for(int i = 0; i < testVals.size(); i++)
            column.addValue(testVals.get(i));
        
        assertTrue(column.getValues().equals(testVals));
    }
    
    @Test
    public void testRemoveValue()
    {
        for(int i = 0; i < 15; i++)
            column.removeValue();
        
        assertTrue(column.getValues().equals(subset));
    }
}
