package com.voedl;

import com.voedl.graphical.MainWindow;

import java.io.File;

public class VoeDL {
    public static void main(String[] args) {
        if(new DetectFFMPEG().detect()) {
            if (args.length == 1) {
                //if(args[0].equals("ui")) {
                //    new MainWindow().init();
                //}else{
                if(args[0].contains("https://v-o-e-unblock.com/")) {
                    new Downloader(args[0]);
                }else{
                    System.out.println("Wrong URL Expected 'v-o-e-unblock.com' But it was: " + args[0]);
                }
                //}
            } else {
                if (args.length == 2) {
                    PublicValues.debug = Boolean.parseBoolean(args[1]);
                    new Downloader(args[0]);
                } else {
                    System.out.println(PublicValues.voe_usage);
                }
            }
        }else{
            if(new DetectOS().OS().toLowerCase().contains("windows")) {
                System.out.println("Voe-DL Cant find ffmpeg, please install it on your windows system");
            }else {
                System.out.println("Voe-DL Cant find ffmpeg, please install it on your linux or unix system");
            }
        }
    }
}
