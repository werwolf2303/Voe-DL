package com.voedl;

import com.voedl.events.onBegin;
import com.voedl.events.onEnd;
import org.apache.commons.cli.*;

public class Initiator {
    public static void main(String[] args) {
        new onBegin();
        Options options = new Options();
        Option url = new Option("u", "url", true, "URL to download");
        url.setRequired(true);
        options.addOption(url);
        Option title = new Option("t", "title", true, "Title for video (with extension)");
        title.setRequired(false);
        options.addOption(title);
        Option debug = new Option("d", "debug", false, "Enable debugging");
        debug.setRequired(false);
        options.addOption(debug);
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
        if(cmd.hasOption("debug")) {
            GlobalValues.debug = true;
        }
        PublicValues.debug = true;
        if(!t.equals("")) {
            new Download(t,u);
        }else{
            new Download(u);
        }
        new onEnd();
    }
}
