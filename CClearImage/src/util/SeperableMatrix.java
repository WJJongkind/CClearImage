/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Wessel
 */
public class SeperableMatrix 
{
    private double[] veca;
    private double[] vecb;
        
    public SeperableMatrix(double[] veca, double[] vecb)
    {
        this.veca = veca;
        this.vecb = vecb;
    }
    
    public static void main(String[] args)
    {
        double[] matrix = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
        create(matrix, 3);
    }
        
    public static SeperableMatrix create(double[] matrix, int width)
    {
        double[] veca = new double[matrix.length / width];
        double[] vecb = new double[width];
        double[] reprod = new double[matrix.length];
        
        double origin = matrix[0];
        for(int i = 0; i <  matrix.length; i += width)
        {
            double a = matrix[i];
            double b = matrix[i / width] / origin;
            
            veca[i / width] = a;
            vecb[i / width] = b;
        }
        
        for(int i = 0; i < veca.length; i++)
            for(int j = 0; j < vecb.length; j++)
                reprod[j + i * vecb.length] = veca[i] * vecb[j];
        
        if(checkEqual(matrix, reprod))
            return new SeperableMatrix(veca, vecb);
        else
            return null;
    }
    
    private static boolean checkEqual(double[] a, double b[])
    {
        for(int i = 0; i < a.length; i++)
        {
            if(a[i] == b[i])
                continue;
            
            double val = a[i] / b[i];
            if(val < 0.95 || val > 1.05)
                return false;
        }
        
        return true;
    }
    
    public void makeCircular()
    {
        for(int x = 0; x < vecb.length; x++)
        {
            double weight = ExtendedMath.calculateProportionInCircle(x - vecb.length / 2.0, vecb.length / 2.0);
            vecb[x] = vecb[x] * weight;
            veca[x] = veca[x] * weight;
        }
    }

    public double[] getVeca() {
        return veca;
    }

    public double[] getVecb() {
        return vecb;
    }
    
    
}
