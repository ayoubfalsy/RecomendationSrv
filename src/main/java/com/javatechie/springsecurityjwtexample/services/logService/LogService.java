package com.javatechie.springsecurityjwtexample.services.logService;

import com.javatechie.springsecurityjwtexample.utils.FileUtils;
import com.javatechie.springsecurityjwtexample.utils.LevelBasedFileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.*;

/**
 * Created by moufid1010 on 2/6/2017.
 */
@Service
public class LogService {


   


    public static final Logger LOGGER = Logger.getGlobal();
    private static LevelBasedFileHandler FINE;

    @Autowired
    public LogService() {

        FileHandler fh;

        try {

            ResourceBundle bundle = ResourceBundle.getBundle("application"); //creates bundle from file `config.properties`

            String debug =null;

            if(bundle.containsKey("debugMode"))
            {
                debug= bundle.getString("debugMode");
            }
            fh = new FileHandler("log");

            if (debug != null && debug.equals("true")) {
                LOGGER.setFilter(new Filter() {
                    @Override
                    public boolean isLoggable(LogRecord record) {

                        if (record.getLevel().equals(Level.INFO)) {
                            return true;
                        }else if (record.getLevel().equals(Level.SEVERE)) {
                            return true;}
                        else if (record.getLevel().equals(Level.WARNING)) {
                            return true;}
                        else if (record.getLevel().equals(Level.FINE)) {
                            return true;}

                        else {
                            return false;
                        }
                    }

                });

            }

            FileUtils.getInstance().createFolder("tmp");

            FINE=new LevelBasedFileHandler("tmp/credentials.log", Level.FINE);
            LOGGER.addHandler(FINE);
            LOGGER.addHandler(new LevelBasedFileHandler("tmp/info.log", Level.INFO));
            LOGGER.addHandler(new LevelBasedFileHandler("tmp/warn.log", Level.WARNING));
            LOGGER.addHandler(new LevelBasedFileHandler("tmp/server.log", Level.SEVERE));

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }


    }


    public static void log(Level level, String msg, Throwable thrown) {

        try {
            if (thrown != null) {
                LOGGER.log(level, msg, thrown);
            } else {
                LOGGER.log(new LogRecord(level, msg));
                if (level.equals(Level.FINE)) {
                    FINE.publish(new LogRecord(level, msg));
                }

            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }


    public static void log(Level level, String msg) {

        try {

                LOGGER.log(new LogRecord(level,msg));
                if(level.equals(Level.FINE))
                {
                    FINE.publish(new LogRecord(level,msg));
                }


        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }


    }


}
