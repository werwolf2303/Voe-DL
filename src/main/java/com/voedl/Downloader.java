package com.voedl;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Downloader {
    String title = "";
    public Downloader(String url) {
        if(!PublicValues.title.equals("")) {
            if(!PublicValues.title.contains(".mp4")) {
                System.out.println(new Language().get("voedl.error.wrongtitle"));
                new Utils().finalClean();
                System.exit(1);
            }
            if(new File(PublicValues.title).exists()) {
                System.out.println(new Language().get("voedl.error.exists"));
                new Utils().finalClean();
                System.exit(1);
            }
        }
        if (new File("down").exists()) {
            new Utils().clean();
        }
        try {
            downloadM3U8(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void downloadM3U8(String url) throws IOException {
        if(PublicValues.debug) {
            System.out.println("M3U8 Url: " + url);
        }
        System.out.println(new Language().get("voedl.download.index"));
        new Legacy().UrlToFile(url, "tmp.txt");
        String hls = "";
        try {
            File myObj = new File("tmp.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.contains("\"hls\"")) {
                    hls = data;
                }
                if(PublicValues.title.equals("")) {
                    if (data.contains("<title>")) {
                        title = data.replace("<title>", "").replace("</title>", "").replace("Watch ", "");
                    }
                }else{
                    title = PublicValues.title;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        hls = hls.replace("            \"hls\": \"", "").replace("\",","").replace(",.urlset", "").replace("/,", "/").replace("master.m3u8", "index-v1-a1.m3u8");
        new Legacy().UrlToFile(hls, "master.m3u8");
        download(hls);
    }
    public void download(String hls) {
        new Converter("master.m3u8", hls.replace("/master.m3u8", ""), title);
        if(new File("master.m3u8").exists()) {
            new File("master.m3u8").delete();
        }
        if(new File("tmp.txt").exists()) {
            new File("tmp.txt").delete();
        }
    }
}
