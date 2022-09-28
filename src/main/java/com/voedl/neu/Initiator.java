package com.voedl.neu;

import com.voedl.neu.events.onBegin;
import com.voedl.neu.utils.Ansi;
import com.voedl.neu.utils.Logger;
import com.voedl.neu.utils.MTApi;
import org.apache.commons.cli.*;

public class Initiator {
    public static void main(String[] args) {
        //new onBegin();
        /*args = new String[] {"--url", "https://tinycat-voe-fashion.com/e/r2vjlaabd31l", "--title", "Test.mp4"};
        Options options = new Options();
        Option url = new Option("u", "url", true, "URL to download");
        url.setRequired(true);
        options.addOption(url);
        Option title = new Option("t", "title", true, "Title for video (with extension)");
        title.setRequired(false);
        options.addOption(title);
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Voe-DL Help", options);
            System.exit(1);
        }
        String u = cmd.getOptionValue("url");
        String t = cmd.getOptionValue("title");
        if(!t.equals("")) {
            new Download(t,u);
        }else{
            new Download(u);
        }*/

        new MTApi(467);
    }
}
