package com.voedl;

import java.io.File;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class Initiator {
    public static void main(String[] args) {
        System.out.println("This Java application is running in Legacy mode");
        if(!new File(".voetemp").exists()) {
            String source = "Voe-DL.jar";
            String destination = ".voetemp";
            new Legacy().unzip(source, destination);
        }
        if(new Utils().enoughSpace()) {
            if(new Utils().detectFFMPEG()) {
                try {
                    if (args[1].toLowerCase().contains("debug")) {
                        PublicValues.debug = true;
                    }
                    new Downloader(args[0]);
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    try {
                        new Downloader(args[0]);
                    }catch (ArrayIndexOutOfBoundsException aiopbe2) {
                        System.out.print(PublicValues.voe_usage);
                    }
                }
            }
        }
    }
}
