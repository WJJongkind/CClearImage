/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclearimage.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Wessel
 */
public class ImageHolder extends JPanel
{
    private final EditableImageComponent image;
    
    public ImageHolder()
    {
        image = new EditableImageComponent();
        init();
    }
    
    public ImageHolder(String path) throws Exception
    {
        image = new EditableImageComponent(path);
        init();
    }
    
    public ImageHolder(File file) throws Exception
    {
        image = new EditableImageComponent(file);
        init();
    }
    
    private void init()
    {
        super.setLayout(new GridBagLayout());
        super.setBackground(Color.LIGHT_GRAY);
        
        GridBagConstraints c = new  GridBagConstraints();
        
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = c.CENTER;
        c.fill = c.NONE;
        
        super.add(image, c);
    }
    
    /*@Override
    public void paintComponent(Graphics g)
    {
        g.setColor(super.getBackground());
        g.fillRect(0, 0, super.getWidth(), super.getHeight());
        super.paintComponent(g);
    }*/
}
