package com.example;

import java.awt.*;
import javax.swing.*;

public class TextPanel extends JPanel {
    private String text, author;
    private int stringwidth, stringheight;
    private int verticalSpace = 5;
    public TextPanel(TextMessage msg){
        this.text = msg.getContent();
        this.author = msg.getAuthor();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int authorwidth = (g.getFontMetrics()).stringWidth(author + ":");
        stringwidth = (g.getFontMetrics()).stringWidth(text);
        stringheight = (g.getFontMetrics()).getHeight();
        if((authorwidth> stringwidth+20)){
            setPreferredSize(new Dimension(authorwidth,stringheight + 30 + 2*verticalSpace));
        } else {
            setPreferredSize(new Dimension(stringwidth + 20,stringheight + 30 + 2*verticalSpace));
        }
        g.setColor(Color.BLACK);
        g.drawString(author + ":", 0, verticalSpace + stringheight);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRoundRect(0,verticalSpace + stringheight + 1, stringwidth + 20, 30, 10,10);
        g.setColor(Color.BLACK);
        g.drawString(text, 10, verticalSpace + stringheight+20);
    }
}
