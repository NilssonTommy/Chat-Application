package com.example;
import java.io.File;

import javax.imageio.ImageIO;
/**
 * Testklass för att testa guin.
 */
public class testclass{

    public static void main(String[] arg) throws Exception{
        ChatroomBuilder builder = new BasicChatroomBuilder();
        ChatroomDirector director = new ChatroomDirector();
        director.ConstructChatroomGUI(builder);
        ChatroomGUI gui = builder.getResult();
        gui.getUserwindow().addText("hej");
        gui.getUserwindow().addText("test");
        for(int i=0; i<15; i++){/*
            gui.getUserwindow().addText("hej");
            gui.getUserwindow().addText("test");
            gui.getUserwindow().addText("hej");
            gui.getUserwindow().addText("test");
            gui.getUserwindow().addText("hej");
            gui.getUserwindow().addText("test");
            gui.getUserwindow().addText("hej");
            gui.getUserwindow().addText("test");
            gui.getChatwindow().addText("11111");
            gui.getChatwindow().addText("22222");
            gui.getChatwindow().addText("33333");
            gui.getChatwindow().addText("44444");
            gui.getChatwindow().addText("55555");
            gui.getChatwindow().addText("66666");
            gui.getChatwindow().addText("77777");
            gui.getChatwindow().addText("88888");
            gui.getChatwindow().addText("99999");*/
            gui.getUserwindow().addText("hej");
            gui.getUserwindow().addText("test");
            gui.getChatwindow().addText("101010101010101010");
            //gui.chatscroll.getVerticalScrollBar().setValue(gui.chatscroll.getVerticalScrollBar().getMaximum());
            Thread.sleep(5);

            gui.chatwindow.revalidate();
            gui.userwindow.revalidate();
        }
        gui.getChatwindow().addImg(ImageIO.read(new File("/Users/jespernyberg/Pictures/icon.png")));
        gui.getChatwindow().addImg(ImageIO.read(new File("/Users/jespernyberg/Pictures/icon.png")));
        gui.sendbutton.addActionListener(e->{
            gui.chatwindow.revalidate();
            gui.userwindow.revalidate();
        });
        //gui.chatscroll.setPreferredSize(gui.chatscroll.getPreferredSize());
        //gui.chatwindow.setPreferredSize(gui.chatwindow.getPreferredSize());
        //gui.getChatwindow().addGlue();
        //gui.getChatwindow().addText("101010101010101010");
    }
}
