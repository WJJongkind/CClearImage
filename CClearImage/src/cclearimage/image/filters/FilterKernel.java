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
public abstract class FilterKernel 
{
    protected int[] values = new int[256];
    protected ArrayList<KernelColumn> columns = new ArrayList<>();
    
    public void addColumn(KernelColumn col)
    {
        columns.add(col);
        
        int[] colValues = col.getValues();
        for(int i = 0; i < colValues.length; i++)
            values[i] += colValues[i];
    }
    
    public void removeColumn()
    {
        KernelColumn col = columns.get(0);
        
        int[] colValues = col.getValues();
        for(int i = 0; i < colValues.length; i++)
            values[i] -= colValues[i];
        
        columns.remove(col);
    }
    
    public void removeColumns()
    {
        columns = new ArrayList<>();
        values = new int[256];
    }
    
    public abstract int getValue();
    
    public int[] getValues()
    {
        return values;
    }
    
    public ArrayList<KernelColumn> getColumns()
    {
        return columns;
    }
}
