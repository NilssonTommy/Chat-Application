package main.java.com.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.*;

public class TextPanel extends JPanel {
    String text;
    int stringwidth;
    public TextPanel(String text){
        this.text = text;
        repaint();
        revalidate();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        stringwidth = (g.getFontMetrics()).stringWidth(text);
        setPreferredSize(new Dimension(stringwidth + 20,30));
        g.setColor(Color.LIGHT_GRAY);
        g.fillRoundRect(0,0, stringwidth + 20, 30, 10,10);
        g.setColor(Color.BLACK);
        g.drawString(text, 10, 20);
    }
}
