package com.voedl.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Language {
    String getUserLanguage() {
        Locale locale = Locale.getDefault();
        String lang = locale.getCountry();
        return lang;
    }
    public boolean isGerman() {
        return getUserLanguage().equals("DE");
    }
    public String get(String key) {
        String toreturn = "";
        if(isGerman()) {
            try {
                File myObj = null;
                if(new Utils().isIDE()) {
                    myObj = new File("src/main/resources/resources/de.txt");
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        if(data.contains(key)) {
                            toreturn = data.replace(key+":", "");
                        }
                    }
                    myReader.close();
                }else{
                    for(String data : new Resouces().read("resources/de.txt").split("\n")) {
                        if(data.contains(key)) {
                            toreturn = data.replace(key+":", "");
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            try {
                File myObj = null;
                if(new Utils().isIDE()) {
                    myObj = new File("src/main/resources/resources/en.txt");
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        if(data.contains(key)) {
                            toreturn = data.replace(key+":", "");
                        }
                    }
                    myReader.close();
                }else{
                    for(String data : new Resouces().read("resources/en.txt").split("\n")) {
                        if(data.contains(key)) {
                            toreturn = data.replace(key+":", "");
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(!toreturn.equals("")) {
            return toreturn;
        }else{
            return key;
        }
    }
}
