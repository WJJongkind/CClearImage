/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclearimage.gui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 *
 * @author Wessel
 */
public class MainMenu extends JMenuBar
{
    //private JMenu file, edit, image, math, analyze, window, help;
    
    public MainMenu()
    {
        super.add(new FileMenu());
        super.add(new EditMenu());
        super.add(new ImageMenu());
        super.add(new MathMenu());
        super.add(new AnalyzeMenu());
        super.add(new WindowMenu());
        super.add(new HelpMenu());
    }
}
