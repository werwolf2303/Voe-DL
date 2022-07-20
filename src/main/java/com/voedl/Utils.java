package com.voedl;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Utils {
    public void finalClean() {
        try {
            FileUtils.deleteDirectory(new File(".voetemp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clean() {
        try {
            FileUtils.deleteDirectory(new File("down"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public File getFromResources(String file) {
        return new File(".voetemp/resources", file);
    }
    public boolean isIDE() {
        return new File("pom.xml").exists();
    }
    private static String OS = null;
    String getOsName()
    {
        if(OS == null) { OS = System.getProperty("os.name"); }
        return OS;
    }
    public String OS()
    {
        if(getOsName().startsWith("Windows")) {
            return "WINDOWS";
        }else{
            if(getOsName().indexOf("mac")>=0) {
                return "MAC";
            }else {
                return "LINUX";
            }
        }
    }
    public boolean enoughSpace() {
        long freemb = new File(System.getProperty("user.dir")).getFreeSpace()/ (1024*1024);
        return freemb>3000;
    }
    public boolean detectFFMPEG() {
        if(OS().equals("WINDOWS")) {
            return System.getenv("PATH").toLowerCase().contains("ffmpeg");
        }else{
            return new File("/usr/bin/ffmpeg").exists();
        }
    }
}
