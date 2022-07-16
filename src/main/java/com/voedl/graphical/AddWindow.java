package com.voedl.graphical;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class AddWindow extends JDialog {
    public void init(JList list, DefaultListModel model, JFrame frame) {
        this.setTitle("Add Voe Url");
        this.getContentPane().add (new AddPanel(list, model, frame));
        this.pack();
        this.setVisible (true);
    }
}
