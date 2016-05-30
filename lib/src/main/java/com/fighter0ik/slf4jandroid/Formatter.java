package com.fighter0ik.slf4jandroid;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Locale;
import java.util.logging.LogRecord;

/**
 * Created by DS on 24.05.2016.
 */
public class Formatter extends java.util.logging.Formatter
{
    private static final String sDefaultFormat = "%1$s: %2$tF %2$tT.%2$tL | %3$s | %4$s %5$s() | %6$s";
    private static final String sDefaultOsThreadName = "unknown thread. id=";

    //

    private final String mFormat;

    //

    protected Formatter( String format )
    {
        mFormat = format;
    }

    public Formatter()
    {
        this( sDefaultFormat );
    }

    //

    @Override
    public final String format( LogRecord r )
    {
        if ( r==null ) throw new IllegalArgumentException();

        StringBuilder builder = new StringBuilder();

        java.util.Formatter formatter = new java.util.Formatter( builder, Locale.US );
        formatter.format( mFormat, parseLogRecord( r ) );
        formatter.flush();
        formatter.close();

        Throwable t = r.getThrown();
        if ( t!=null )
        {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter( stringWriter );
            t.printStackTrace( printWriter );

            builder.append( "\n" );
            builder.append( stringWriter.toString() );
        }

        builder.append( "\n" );
        return builder.toString();
    }

    protected Object[] parseLogRecord( LogRecord record )
    {
        Object[] objects = new Object[6];
        objects[0] = record.getLevel();
        objects[1] = new Date( record.getMillis() );
        objects[2] = getOsThreadName( record );
        objects[3] = record.getSourceClassName();
        objects[4] = record.getSourceMethodName();
        objects[5] = formatMessage( record );
        return objects;
    }

    //

    private String getOsThreadName( LogRecord record )
    {
        if ( record.getClass()!=com.fighter0ik.slf4jandroid.LogRecord.class ) return (sDefaultOsThreadName+record.getThreadID());
        Thread thread = ((com.fighter0ik.slf4jandroid.LogRecord)record).getOsThread();
        if ( thread==null ) return (sDefaultOsThreadName+record.getThreadID());
        return thread.getName();
    }
}
