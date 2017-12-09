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
public class ExtendedMath
{
    public static double getGaussian(double x, double std, double avg)
    {
        return (1/(std * Math.sqrt(2 * Math.PI))) * Math.pow(Math.E, -1 * 0.5 * Math.pow((x - avg) / std, 2));
    }
    
    public static double calculateProportionInCircle(double ix, double radius)
    {
        double x = Math.abs(ix);
        return (Math.sqrt(radius * radius - x * x) + Math.sqrt(radius * radius - (x-0.5) * (x-0.5))) / (2 * radius);
    }
    
    public static void main(String[] args)
    {
        System.out.println(calculateProportionInCircle(0, 7.5));
    }
}
