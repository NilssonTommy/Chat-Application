package com.example;

import java.awt.*;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
    private Image img;
    private String author;
    private int stringheight, imgWidth, imgHeight, width, height;
    private float imgRatio;
    private int verticalSpace = 5;
    public ImagePanel(ImageMessage msg, int parentwidth){
        this.img = msg.getContent();
        imgWidth = img.getWidth(null);
        imgHeight = img.getHeight(null);
        imgRatio = ((float)imgWidth)/((float)imgHeight);
        if(imgWidth >= (parentwidth-60)){
            width = parentwidth-60;
            height = (int)(((float)width)/imgRatio);
        } else {
            width = imgWidth;
            height = imgHeight;
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        stringheight = (g.getFontMetrics()).getHeight();
        g.setColor(Color.BLACK);
        g.drawString(author + ":", 0, verticalSpace + stringheight);
        setPreferredSize(new Dimension(width + 20,height+40+ (2*verticalSpace)+stringheight+1));
        g.setColor(Color.LIGHT_GRAY);
        g.fillRoundRect(0, verticalSpace + stringheight+1, width+20, height+40, 10, 10);
        g.drawImage(img, 10, verticalSpace + stringheight + 20, width, height, null);

    }
}
