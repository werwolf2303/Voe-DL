package com.voedl;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Converter {
    String title = "output.mp4";
    File toconvert;
    String streamurl;
    String files = "";
    String[] forFiles = new String[] {};
    String downloadlocation = "down";
    File loc = new File(downloadlocation);
    public Converter(String file, String contenturl, String tit) {
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
        int min = 1;
        int all = forFiles.length;
        try (ProgressBar pb = new ProgressBar(new Language().get("voedl.download.info2"), all, 1000, false, System.err, ProgressBarStyle.ASCII, " " + new Language().get("voedl.download.info1"), 1L, false, (DecimalFormat)null, ChronoUnit.SECONDS, 0L, Duration.ZERO)) {

            for (String s : forFiles) {
                try {
                    min++;
                    InputStream in = new URL(s).openStream();
                    Files.copy(in, Paths.get(downloadlocation + "/" + s.split("/")[s.split("/").length - 1]), StandardCopyOption.REPLACE_EXISTING);
                } catch (MalformedURLException url) {
                    url.printStackTrace();
                } catch (IOException e) {
                }
                pb.step();
            }
        }
    }
    public void putTogether() {
        System.out.println(new Language().get("voedl.convert"));
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
        if(new Utils().OS().equals("WINDOWS")) {
            if(PublicValues.debug) {
                System.out.println("Windows");
            }
            winffmpeg(files);
        }else{
            System.out.println("This version is only intended for use on Windows XP");
        }
    }
    public void winffmpeg(String files) {
        if (PublicValues.debug) {
            System.out.println("Files:" + files);
        }
        if (!title.contains(".mp4")) {
            title = title + ".mp4";
        }
        if (PublicValues.debug) {
            System.out.println("cmd.exe /c start cmd.exe /c ffmpeg -i \"concat:" + files + "\" -c copy \"" + title + "\"");
        }
        try {
            new ProcessBuilder("cmd", "/c", "ffmpeg -i \"concat:" + files + "\"" + " -bsf:a aac_adtstoasc -c copy \"" + title + "\"").inheritIO().start().waitFor();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(new File("down").exists()) {
            new Legacy().deleteDirectory("down");
        }
        int loopcount = 0;
        while(new File(".voetemp").exists()) {
            if(loopcount>5) {
                System.out.println("Failed to delete temp files");
                break;
            }
            new Legacy().deleteDirectory(".voetemp");
            loopcount++;
        }
    }
}

