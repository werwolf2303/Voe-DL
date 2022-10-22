package com.voedl.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MTApi {
    String workingDir = "test";
    ArrayList<Thread> threads = new ArrayList<>();
    boolean br = false;
    public MTApi(int files) {
        initThreads(files);
    }
    Thread t = new Thread();
    int worked = 1;
    void initThreads(int files) {
        int amount = files/6;
        int r = files-amount*6;
        System.out.println("Init other threads to download " + amount + " files each");
        for(int i = 1; i < amount; i++) {
            final int curr = i;
            System.out.println("Init thread: " + i);
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    int min = worked;
                    int max = worked+amount;
                    while(min!=max) {
                        try {
                            new File(workingDir, "F"+min).createNewFile();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        min++;
                    }
                }
            };
            t = new Thread(run);
            threads.add(t);
            t.start();
            worked=worked+amount;
        }
        System.out.println("Init main thread to download " + r + " files");
        Thread main = new Thread(new Runnable() {
            @Override
            public void run() {
                int min = worked;
                int max = r;
                while(min!=max+1) {
                    try {
                        new File(workingDir, "F"+min).createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    min++;
                }
            }
        });
        main.start();
        int counter = 0;
        for(;;) {
            if(counter==300) {
                br = true;
                break;
            }
            counter++;
        }
        threads.clear();
        System.out.println("Threads in list after clearing: " + threads.size());
    }
}
