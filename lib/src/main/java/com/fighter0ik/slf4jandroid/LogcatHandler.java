package com.fighter0ik.slf4jandroid;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogcatHandler extends Handler
{
    private static final String sDefaultLevel = Level.ALL.getName();
    private static final String sDefaultFilter = null;
    private static final String sDefaultFormatter = Formatter.class.getName();
    private static final String sDefaultEncoding = null;

    //

    private static final int sMaxTagLength = 23;

    //

    public LogcatHandler()
    {
        super();
        initProperties();
    }

    @Override
    public void close()
    {}

    @Override
    public void flush()
    {}

    @Override
    public void publish( LogRecord record )
    {
        if ( record==null ) return;
        if ( !isLoggable( record ) ) return;

        Filter filter = getFilter();
        if ( filter!=null && !filter.isLoggable( record ) ) return;

        Level julLevel = record.getLevel();
        int logcatLevel;

        if ( julLevel==Level.FINEST ) logcatLevel = Log.VERBOSE;
        else if ( julLevel==Level.FINE || julLevel==Level.FINER ) logcatLevel = Log.DEBUG;
        else if ( julLevel==Level.INFO || julLevel==Level.CONFIG ) logcatLevel = Log.INFO;
        else if ( julLevel==Level.WARNING )  logcatLevel = Log.WARN;
        else if ( julLevel==Level.SEVERE )  logcatLevel = Log.ERROR;
        else return;

        String tag = record.getLoggerName();
        if ( tag.length()>sMaxTagLength ) tag = tag.substring( 0, sMaxTagLength );

        String msg;
        java.util.logging.Formatter formatter = getFormatter();
        if ( formatter!=null ) msg = formatter.format( record );
        else msg = new Formatter().format( record );

        Log.println( logcatLevel, tag, msg );
    }

    //

    private void initProperties()
    {
        try
        {
            Class<?> handlerClass = Handler.class;
            Class<?> stringClass = String.class;

            Method m = handlerClass.getDeclaredMethod( "initProperties", stringClass, stringClass, stringClass, stringClass );

            m.setAccessible( true );
            m.invoke( this, sDefaultLevel, sDefaultFilter, sDefaultFormatter, sDefaultEncoding );
        }
        catch ( Exception ex )
        {
            System.err.println( getClass().getName()+" initProperties() got exception:\n"+ex );
        }
    }
}
