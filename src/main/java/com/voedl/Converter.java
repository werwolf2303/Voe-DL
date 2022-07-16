package com.voedl;

import net.bramp.ffmpeg.FFmpeg;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.Scanner;

public class Converter {
    String title = "output.mp4";
    File toconvert;
    String streamurl;
    String files = "";
    String[] forFiles = new String[] {};
    String downloadlocation = "down";
    File loc = new File(downloadlocation);
    OSTypes os;
    public Converter(String file, String contenturl) {
        os = OSTypes.valueOf(new DetectOS().OS());
        toconvert = new File(file);
        streamurl = contenturl;
        if(!loc.exists()) {
            readFile();
            if(PublicValues.debug) {
                System.out.println("Download M3U8 Playlist files");
            }
            download();
        }
        putTogether();
        if(PublicValues.debug) {
            System.out.println("Clean");
        }
        new Utils().clean();
    }
    public Converter(String file, String contenturl, String tit) {
        os = OSTypes.valueOf(new DetectOS().OS());
        title = tit;
        toconvert = new File(file);
        streamurl = contenturl;
        if(PublicValues.debug) {
            System.out.println("LOC exists: " + loc.exists());
            System.out.println("Title: " + tit);
            System.out.println("File: " + file);
            System.out.println("ContentURL: " + contenturl);
        }
        if(!loc.exists()) {
            if(PublicValues.debug) {
                System.out.println("Read M3U8");
            }
            readFile();
            if(PublicValues.debug) {
                System.out.println("Download M3U8 Playlist files");
            }
            download();
        }
        if(PublicValues.debug) {
            System.out.println("Put all playlist files into one");
        }
        putTogether();
    }
    public void readFile() {
        try {
            Scanner myReader = new Scanner(toconvert);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(PublicValues.debug) {
                    System.out.println("Data: " + data);
                }
                if(data.contains("#EXT-X-ENDLIST")) {
                    break;
                }
                if(!data.contains("#EXTINF") && !data.contains("#EXT-X-MEDIA-SEQUENCE") && !data.contains("#EXT-X-VERSION") && !data.contains("#EXT-X-PLAYLIST-TYPE") && !data.contains("#EXT-X-ALLOW-CACHE") && !data.contains("#EXT-X-TARGETDURATION") && !data.contains("#EXTM3U")) {
                    if(files.equals("")) {
                        files = streamurl + "/" + data;
                    }else{
                        files = files + "<SPLIT>" + streamurl + "/" + data;
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        forFiles = files.split("<SPLIT>");
    }
    public void download() {
        if(!loc.exists()) {
            loc.mkdir();
        }
        if(!PublicValues.debug) {
            System.out.println("Please wait " + new UserName().get() + " VoeDL downloads files");
        }
        int min = 1;
        int all = forFiles.length;
        for(String s : forFiles) {
            try {
                if(PublicValues.debug) {
                    System.out.println("Download " + min + " from " + all + " files");
                }
                min++;
                InputStream in = new URL(s).openStream();
                Files.copy(in, Paths.get(downloadlocation + "/" + s.split("/")[s.split("/").length-1]), StandardCopyOption.REPLACE_EXISTING);
            }catch (MalformedURLException url) {
                System.out.println("Invalid URL");
                url.printStackTrace();
            } catch (IOException e) {
            }
        }
    }
    public void putTogether() {
        String files = "";
        int min = 1;
        int max = loc.listFiles().length;
        while(!(min==max)) {
            if(files.equals("")) {
                files = "down\\seg-" + min + "-v1-a1.ts";
            }else{
                files = files+"|"+"down\\seg-" + min + "-v1-a1.ts";
            }
            min++;
        }
        if(new DetectOS().OS().equals("WINDOWS")) {
            if(PublicValues.debug) {
                System.out.println("Windows");
            }
            winffmpeg(files);
        }else{
            if(PublicValues.debug) {
                System.out.println("Linux");
            }
            linuxffmpeg(files);
        }
    }
    public void winffmpeg(String files) {
        if(PublicValues.debug) {
            System.out.println("Files:" + files);
        }
        try {
            Process pr = Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /c ffmpeg -i \"concat:" + files + "\" -c copy \"" + title + "\"");
            pr.waitFor();
        }catch (IOException | InterruptedException ex) {
            System.out.println("!!FAILURE!!");
            ex.printStackTrace();
        }
    }
    public void linuxffmpeg(String files) {
        try {
            if(PublicValues.debug) {
                System.out.println("ffmpeg -i \"concat:" + files.replace("\\", "/") + "\" -c copy \"" + title + "\"");
            }
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", "ffmpeg -i \"concat:" + files.replace("\\", "/") + "\" -c copy \"" + title.replace("    ", "") + "\"");
            Process process = processBuilder.start();
            process.waitFor();
            new Utils().clean();
        }catch (IOException | InterruptedException ex) {
            System.out.println("!!FAILURE!!");
            ex.printStackTrace();
        }
    }
}

