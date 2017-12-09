/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclearimage.gui;

import cclearimage.gui.menu.MainMenu;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

/**
 *
 * @author Wessel
 */
public class GUI extends JFrame
{
    private JMenuBar mainMenu;
    private JTabbedPane imagesPane;
    private ImageHolder activeImage;
    
    
    public GUI()
    {
        super("CowLite Clear Image");
        super.setLayout(new GridBagLayout());
        
        mainMenu = new MainMenu();
        imagesPane = new JTabbedPane();
        
        activeImage = new ImageHolder();
        activeImage.setSize(100, 100);
        activeImage.setPreferredSize(new Dimension(100, 100));
        activeImage.setMinimumSize(new Dimension(100, 100));
        
        imagesPane.addTab("Unnamed", activeImage);
        
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = c.NORTH;
        c.weightx = 0;
        c.weighty = 0;
        c.gridwidth = Byte.MAX_VALUE;
        c.gridheight = 1;
        
        super.add(mainMenu, c);
        
        c.gridy++;
        c.fill = c.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = c.CENTER;
        
        super.add(imagesPane, c);
    }
}
