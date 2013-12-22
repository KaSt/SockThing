package sockthing;

import java.io.PrintStream;
import java.io.FileOutputStream;

import java.text.SimpleDateFormat;


public class EventLog
{   
    private boolean log_enabled=false;
    private PrintStream log_stream = null;
    private SimpleDateFormat sdf;

    public EventLog(Config conf)
        throws java.io.IOException
    {   
        conf.require("event_log_enabled");

        log_enabled = conf.getBoolean("event_log_enabled");

        if (log_enabled)
        {
            conf.require("event_log_path");
            log_stream = new PrintStream(new FileOutputStream(conf.get("event_log_path"), true));

            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        }




    }

    public void log(String msg)
    {
        if (!log_enabled) return;

        synchronized(log_stream)
        {
            String line = sdf.format(new java.util.Date()) + " - " + msg;
            log_stream.println(line);
            log_stream.flush();
        }
        

    }

}

