package com.example;
import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class UserWindowPanel extends JPanel implements Observer{
    private List<String> users;
    private JLabel invisisblelabel;
    private GridBagConstraints maingbc, invisiblegbc;
    public UserWindowPanel(){
        setLayout(new GridBagLayout());
        maingbc = new GridBagConstraints();
        invisiblegbc = new GridBagConstraints();
        maingbc.weightx = 1;
        maingbc.anchor = GridBagConstraints.WEST;
        maingbc.gridwidth = GridBagConstraints.REMAINDER;
        invisiblegbc.weighty = 1;
        invisisblelabel = new JLabel();
        invisisblelabel.setPreferredSize(new Dimension(50,10));
        add(invisisblelabel);
    }
    public void update(Object obj){
        if(obj instanceof List){
            @SuppressWarnings("unchecked")
            List<String> users = (List<String>)obj;
            removeAll();
            for(String u : users){
                add(new JLabel(u), maingbc);
            }
            add(invisisblelabel, invisiblegbc);
            revalidate();
        } else if (obj instanceof String){
            String name = (String)obj;
            remove(invisisblelabel);
            add(new JLabel(name), maingbc);
            add(invisisblelabel, invisiblegbc);
            revalidate();
        }
    }
}
