package com.voedl;

import com.voedl.Instances.InstanceManager;
import com.voedl.utils.Language;
import com.voedl.utils.Utils;
import com.voedl.utils.ExitCodes;
import com.voedl.utils.libDownload;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;

import java.io.File;
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
import java.util.Random;

public class Download {
    private boolean hasCustomTitle = false;
    public Download(String title, String url) {
        begin(title, url);
    }
    public Download(String url) {
        begin("", url);
    }
    public void begin(String title, String url) {
        if(!title.equals("")) {
            if(!title.toLowerCase().contains(".mp4")) {
                InstanceManager.getLogger().warning("Title has no valid video extension. SKIP");
            }else{
                hasCustomTitle = true;
            }
        }else{
            title = getTitle(url);
        }
        String curl = extractContentUrl(url);
        downloadAllForM3U8(curl, "cache");
        putTogether("cache", title);
    }
    public void putTogether(String to, String title) {
        String files = "";
        int min = 1;
        int max = new File(to).listFiles().length;
        while(!(min==max)) {
            if(files.equals("")) {
                files = to + "\\seg-" + min + "-v1-a1.ts";
            }else{
                files = files+"|"+to+"\\seg-" + min + "-v1-a1.ts";
            }
            min++;
        }
        if(new Utils().OS().equals("WINDOWS")) {
            if(PublicValues.debug) {
                System.out.println("Windows");
            }
            winffmpeg(files, title);
        }else{
            if(new Utils().OS().equals("MAC")) {
                if (PublicValues.debug) {
                    System.out.println("MacOS");
                }
                macffmpeg(files, title);
            }else {
                if (PublicValues.debug) {
                    System.out.println("Linux");
                }
                linuxffmpeg(files, title);
            }
        }
    }
    public void downloadAllForM3U8(String curl, String to) {
        String m3u8 = new libDownload().getContent(curl+"index-v1-a1.m3u8");
        String files = "";
        for(String data : m3u8.split("\n")) {
            if(data.contains("#EXT-X-ENDLIST")) {
                break;
            }
            if(!data.contains("#EXTINF") && !data.contains("#EXT-X-MEDIA-SEQUENCE") && !data.contains("#EXT-X-VERSION") && !data.contains("#EXT-X-PLAYLIST-TYPE") && !data.contains("#EXT-X-ALLOW-CACHE") && !data.contains("#EXT-X-TARGETDURATION") && !data.contains("#EXTM3U")) {
                if(files.equals("")) {
                    files = curl + "/" + data;
                }else{
                    files = files + "<SPLIT>" + curl + "/" + data;
                }
            }
        }
        if(!new File(to).exists()) {
            new File(to).mkdir();
        }
        int min = 1;
        int all = files.split("<SPLIT>").length;
        try (ProgressBar pb = new ProgressBar(new Language().get("voedl.download.info2"), all, 1000, false, System.err, ProgressBarStyle.ASCII, " " + new Language().get("voedl.download.info1"), 1L, false, (DecimalFormat)null, ChronoUnit.SECONDS, 0L, Duration.ZERO)) {
            for (String s : files.split("<SPLIT>")) {
                try {
                    min++;
                    InputStream in = new URL(s).openStream();
                    Files.copy(in, Paths.get(to + "/" + s.split("/")[s.split("/").length - 1]), StandardCopyOption.REPLACE_EXISTING);
                } catch (MalformedURLException url) {
                    url.printStackTrace();
                } catch (IOException e) {
                }
                pb.step();
            }
        }
    }
    public String getTitle(String url) {
        for(String s : new libDownload().getContent(url).split("\n")) {
            if(s.toLowerCase().contains("<title>")) {
                return s.replace("<title>", "").replace("</title>", "");
            }
        }
        InstanceManager.getLogger().error("Failed to extract video name. GENERATE");
        return "video" + new Random().nextInt(4500) + ".mp4";
    }
    public String extractContentUrl(String url) {
        String toret = "";
        String website = new libDownload().getContent(url);
        for(String s : website.split("\n")) {
            if(s.contains("\"hls\": \"")) {
                toret = s;
            }
        }
        toret = toret.replace("            \"hls\": \"", "").replace("\",","").replace(",.urlset", "").replace("/,", "/").replace("master.m3u8", "index-v1-a1.m3u8");
        toret = toret.replace("index-v1-a1.m3u8", "");
        return toret;
    }
    public void getWebsiteType(String url) {
        if(!url.contains("/e/")) {
            if(url.contains("aniworld.to/redirect")) {
                System.out.println("Downloading from redirect currently not supported");
            }else{
                System.exit(ExitCodes.UNHANDLED.getCode());
            }
        }
    }
    public void winffmpeg(String files, String title) {
        if(PublicValues.debug) {
            System.out.println("Files:" + files);
        }
        try {
            Process pr = Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /c ffmpeg -i \"concat:" + files + "\" -c copy \"" + title + "\"");
            pr.waitFor();
        }catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void linuxffmpeg(String files, String title) {
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
            ex.printStackTrace();
        }
    }
    public void macffmpeg(String files, String title) {
        System.out.println(new Language().get("voedl.mac.experimental"));
        try {
            if(PublicValues.debug) {
                System.out.println("/usr/local/bin/ffmpeg -i \"concat:" + files.replace("\\", "/") + "\" -c copy \"" + title + "\"");
            }
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("/usr/local/bin/ffmpeg", "-i \"concat:" + files.replace("\\", "/") + "\" -c copy \"" + title.replace("    ", "") + "\"");
            Process process = processBuilder.start();
            process.waitFor();
            new Utils().clean();
        }catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
