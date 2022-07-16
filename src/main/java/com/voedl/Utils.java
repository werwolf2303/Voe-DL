package com.voedl;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Utils {
    public void clean() {
        try {
            FileUtils.deleteDirectory(new File("down"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
