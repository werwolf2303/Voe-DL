package com.voedl.graphical;

import com.voedl.Downloader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.lang.model.element.Element;
import javax.swing.*;

public class MainPanel extends JPanel {
    private JList todownload;
    private JButton remove;
    private JButton add;
    private JLabel jcomp4;
    private JTextArea howto;
    private JButton download;

    public MainPanel(JFrame frame) {
        //construct preComponents
        DefaultListModel model = new DefaultListModel();

        //construct components
        todownload = new JList (model);
        remove = new JButton ("Remove");
        add = new JButton ("Add");
        jcomp4 = new JLabel ("Voe-DL UI ");
        howto = new JTextArea ("Click on Add then enter url\n then click on ok. \nWhen you done click on \nDownload");
        download = new JButton ("Download");
        howto.setEditable(false);
        howto.setBackground(this.getBackground());
        //adjust size and set layout
        setPreferredSize (new Dimension (944, 601));
        setLayout (null);

        //add components
        add (todownload);
        add (remove);
        add (add);
        add (jcomp4);
        add (howto);
        add (download);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddWindow().init(todownload, model, frame);
            }
        });
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.remove(todownload.getSelectedIndex());
            }
        });
        download.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < model.size(); i++) {
                    String url = model.get(i).toString();
                    new Downloader(url);
                }
            }
        });
        //set component bounds (only needed by Absolute Positioning)
        todownload.setBounds (95, 55, 600, 455);
        remove.setBounds (755, 170, 100, 25);
        add.setBounds (755, 105, 100, 25);
        jcomp4.setBounds (15, 10, 690, 30);
        howto.setBounds (730, 220, 150, 200);
        download.setBounds (755, 460, 100, 25);
    }
}
