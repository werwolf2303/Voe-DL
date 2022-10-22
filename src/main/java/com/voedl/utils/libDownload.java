package com.voedl.utils;

import com.voedl.Instances.InstanceManager;
import org.apache.commons.io.IOUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class libDownload {
    public String getContent(String url) {
        String content2 = "";
        try {
            InputStream in = new URL(url).openStream();
            try {
                content2 = IOUtils.toString(in, StandardCharsets.UTF_8);
            } finally {
                IOUtils.closeQuietly(in);
            }
        }catch (IOException ex) {
            InstanceManager.getLogger().panic("Cant get contents of url: " + url);
        }
        return content2;
    }
    public void downloadFile(String url, String path) {
        String content2 = "";
        try {
            InputStream in = new URL(url).openStream();
            try {
                content2 = IOUtils.toString(in, StandardCharsets.UTF_8);
            } finally {
                IOUtils.closeQuietly(in);
            }
            FileWriter writer1 = new FileWriter(path);
            writer1.write(content2);
            writer1.close();
        }catch (IOException ex) {
            InstanceManager.getLogger().panic("Cant download file: " + url);
        }
    }
}
