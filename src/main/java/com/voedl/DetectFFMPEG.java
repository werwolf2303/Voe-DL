package com.voedl;

import java.io.File;

public class DetectFFMPEG {
    public boolean detect() {
        if(new DetectOS().OS().equals("WINDOWS")) {
            return System.getenv("PATH").toLowerCase().contains("ffmpeg");
        }else{
            return new File("/usr/bin/ffmpeg").exists();
        }
    }
}
