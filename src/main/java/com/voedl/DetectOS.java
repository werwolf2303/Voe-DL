package com.voedl;

public class DetectOS {
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
            return "LINUX";
        }
    }
}
