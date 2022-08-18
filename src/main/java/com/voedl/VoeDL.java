package com.voedl;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import java.io.File;

public class VoeDL {
    public static void main(String[] args) {
        boolean debug = false;
        String title = "";
        String url = "";
        int count = 0;
        if(!new File(".voetemp").exists()) {
            String source = "Voe-DL.jar";
            String destination = ".voetemp";
            try {
                ZipFile zipFile = new ZipFile(source);
                zipFile.extractAll(destination);
            } catch (ZipException e) {
                e.printStackTrace();
            }
        }else{
            new Utils().finalClean();
            String source = "Voe-DL.jar";
            String destination = ".voetemp";
            try {
                ZipFile zipFile = new ZipFile(source);
                zipFile.extractAll(destination);
            } catch (ZipException e) {
                e.printStackTrace();
            }
        }
        if(new Utils().enoughSpace()) {
            if (new Utils().detectFFMPEG()) {
                if(args.length==0) {
                    System.out.println(PublicValues.voe_usage);
                    System.exit(1);
                }else {
                    if (!(args.length > 4)) {
                        for (String s : args) {
                            if (count == 0) {
                                // url = s
                                if(s.contains("/e/")) {
                                    url = s;
                                }else{
                                    System.out.println(new Language().get("voedl.error.wrongurl").replace("[arg]", s));
                                }
                            }
                            if (s.contains("--title")) {
                                if (!args[count + 1].equals("--debug") && !args[count + 1].equals("--title")) {
                                    title = args[count + 1];
                                } else {
                                    System.out.println(PublicValues.voe_usage);
                                    System.exit(1);
                                }
                            }
                            if (s.contains("--debug")) {
                                debug = true;
                            }
                            count++;
                        }
                    } else {
                        System.out.println(PublicValues.voe_usage);
                        System.exit(1);
                    }
                }
                PublicValues.debug = debug;
                PublicValues.title = title;
                new Downloader(url);
            }else {
                if (new Utils().OS().toLowerCase().contains("windows")) {
                    System.out.println(new Language().get("voedl.ffmpeg.win.error"));
                } else {
                    System.out.println(new Language().get("voedl.ffmpeg.linux.error"));
                }
            }
        }else{
            System.out.println(new Language().get("voedl.error.space"));
        }
    }
}
