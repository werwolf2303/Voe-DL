package com.voedl.neu.utils;

import com.voedl.neu.GlobalValues;

public class Logger {
    public void info(String message) {
        if(GlobalValues.isLinux) {
            System.out.println(new Ansi(Ansi.BLUE).colorize("[INFO] " + message));
        }else{
            System.out.println("[INFO] " + message);
        }
    }
    public void panic(String message) {
        if(GlobalValues.isLinux) {
            System.out.println(new Ansi(Ansi.BACKGROUND_WHITE, Ansi.RED).colorize("[PANIC] " + message));
        }else{
            System.out.println("[PANIC] " + message);
        }
        System.exit(ExitCodes.PANIC.getCode());
    }
    public void error(String message) {
        if(GlobalValues.isLinux) {
            System.out.println(new Ansi(Ansi.RED).colorize("[ERROR] " + message));
        }else{
            System.out.println("[ERROR] " + message);
        }
    }
    public void debug(String message) {
        if(GlobalValues.isLinux) {
            System.out.println(new Ansi(Ansi.GREEN).colorize("[DEBUG] " + message));
        }else{
            System.out.println("[DEBUG] " + message);
        }
    }
}
