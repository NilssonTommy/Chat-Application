package com.example;
import java.awt.*;
import java.util.List;

import javax.swing.*;

public class UserWindowPanel extends JPanel implements Observer{
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
            List<?> list = (List<?>)obj;
            if(!list.isEmpty()){
                if(list.get(0) instanceof UserInterface){
                    @SuppressWarnings("unchecked")
                    List<UserInterface> users = (List<UserInterface>)list;
                    removeAll();
                    for(UserInterface u : users){
                        add(new JLabel(u.getUsername()), maingbc);
                    }
                    add(invisisblelabel, invisiblegbc);
                    revalidate();
                } else {
                    System.out.println("List submitted to UserWindowPanel does not consist of UserInterface");
                }
            }
            
            
        } else if (obj instanceof UserInterface){
            UserInterface user = (UserInterface)obj;
            remove(invisisblelabel);
            add(new JLabel(user.getUsername()), maingbc);
            add(invisisblelabel, invisiblegbc);
            revalidate();
        }
    }
}
