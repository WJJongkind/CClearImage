
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wessel
 */
public class test {
    public static void main(String[] args)
    {
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        for(int i = 1; i < 2; i++)
            a.remove(i);
        System.out.println(a);
    }
}
