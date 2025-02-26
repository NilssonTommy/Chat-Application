package com.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{
    Image img;
    int width, height, imgWidth, imgHeight; 
    float imgRatio;
    public ImagePanel(Image img){
        this.img = img;
        imgWidth = img.getWidth(null);
        imgHeight = img.getHeight(null);
        imgRatio = ((float)imgWidth)/((float)imgHeight);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(imgWidth >= (getWidth()-60)){
            width = getWidth()-60;
            height = (int)(((float)width)/imgRatio);
        } else {
            width = imgWidth;
            height = imgHeight;
        }
        setPreferredSize(new Dimension(width + 20,height+20));
        g.setColor(Color.LIGHT_GRAY);
        g.fillRoundRect(0, 0, width+20, height+20, 10, 10);
        g.drawImage(img, 10, 20, width, height, null);

    }
}
