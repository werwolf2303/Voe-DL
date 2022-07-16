package com.voedl;

import org.apache.commons.io.IOUtils;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

public class Downloader {
    String title = "";
    public Downloader(String url) {
        if(new File("down").exists()) {
            new Utils().clean();
        }
        try {
            downloadM3U8(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void downloadM3U8(String url) throws IOException {
        // https://delivery-node-bata.voe-network.net/hls/6oarme6g5a23cszcr3emzizpwvmo5ydm6co3jrdtjsw5cedefeb6wytq2jia/master.m3u8
        InputStream in = new URL(url).openStream();
        String content;
        boolean out = false;
        try {
            content = IOUtils.toString(in, StandardCharsets.UTF_8);
        } finally {
            IOUtils.closeQuietly(in);
        }
        FileWriter writer = new FileWriter("tmp.txt");
        writer.write(content);
        writer.close();
        String hls = "";
        try {
            File myObj = new File("tmp.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.contains("\"hls\"")) {
                    hls = data;
                }
                if(data.contains("<title>")) {
                    title = data.replace("<title>", "").replace("</title>", "").replace("Watch ", "");
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        hls = hls.replace("            \"hls\": \"", "").replace("\",","").replace(",.urlset", "").replace("/,", "/").replace("master.m3u8", "index-v1-a1.m3u8");
        InputStream in2 = new URL(hls).openStream();
        String content2;
        try {
            content2 = IOUtils.toString(in2, StandardCharsets.UTF_8);
        } finally {
            IOUtils.closeQuietly(in);
        }
        FileWriter writer1 = new FileWriter("master.m3u8");
        writer1.write(content2);
        writer1.close();
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
