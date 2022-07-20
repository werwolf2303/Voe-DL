package com.voedl;

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
    public String get(String key, String forceLanguage) {
        String toreturn = "";
        if(forceLanguage.equals("DE")) {
            try {
                File myObj = null;
                if(new Utils().isIDE()) {
                    myObj = new File("src/main/resources/resources/de.txt");
                }else{
                    myObj = new Utils().getFromResources("resources/de.txt");
                }
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if(data.contains(key)) {
                        toreturn = data.replace(key+":", "");
                    }
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            try {
                File myObj = null;
                if(new Utils().isIDE()) {
                    myObj = new File("src/main/resources/resources/en.txt");
                }else{
                    myObj = new Utils().getFromResources("resources/en.txt");
                }
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if(data.contains(key)) {
                        toreturn = data.replace(key+":", "");
                    }
                }
                myReader.close();
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
    public String get(String key) {
        String toreturn = "";
        if(isGerman()) {
            try {
                File myObj = null;
                if(new Utils().isIDE()) {
                    myObj = new File("src/main/resources/resources/de.txt");
                }else{
                    myObj = new Utils().getFromResources("de.txt");
                }
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if(data.contains(key)) {
                        toreturn = data.replace(key+":", "");
                    }
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            try {
                File myObj = null;
                if(new Utils().isIDE()) {
                    myObj = new File("src/main/resources/resources/en.txt");
                }else{
                    myObj = new Utils().getFromResources("en.txt");
                }
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if(data.contains(key)) {
                        toreturn = data.replace(key+":", "");
                    }
                }
                myReader.close();
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
