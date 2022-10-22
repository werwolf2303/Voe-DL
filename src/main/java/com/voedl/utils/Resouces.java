package com.voedl.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class Resouces {
    public String read(String path) {
        Scanner s = new Scanner(getFileFromResourceAsStream(path)).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        return result;
    }
    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }
}
