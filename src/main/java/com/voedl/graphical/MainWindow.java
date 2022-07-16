package com.voedl.graphical;

import javax.swing.*;

public class MainWindow extends JFrame {
    public void init() {
        this.setTitle("Voe-DL");
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(new MainPanel(this));
        this.pack();
        this.setVisible(true);
    }
}
