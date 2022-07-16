package com.voedl.graphical;

import jdk.nashorn.internal.scripts.JO;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.event.*;

public class AddPanel extends JPanel {
    private JTextField url;
    private JButton ok;
    public AddPanel(JList list, DefaultListModel model, JFrame frame) {
        //construct components
        url = new JTextField (5);
        ok = new JButton ("Ok");

        //adjust size and set layout
        setPreferredSize (new Dimension (630, 220));
        setLayout (null);

        //add components
        add (url);
        add (ok);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!url.getText().equals("")) {
                    boolean contains = false;
                    for(String s : model.toString().replace("[", "").replace("]", "").split(",")) {
                        if(s.equals(url.getText())) {
                            contains = true;
                        }
                    }
                    if(!contains) {
                        model.addElement(url.getText());
                        url.setText("");
                    }else{
                        JOptionPane.showMessageDialog(frame, "Url already in queue", "Url error", JOptionPane.ERROR_MESSAGE);
                        url.setText("");
                    }
                }
            }
        });
        //set component bounds (only needed by Absolute Positioning)
        url.setBounds (25, 25, 560, 25);
        ok.setBounds (255, 95, 100, 25);
    }
}
