package com.fighter0ik.slf4jandroid;

import org.slf4j.helpers.MarkerIgnoringBase;

import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DS on 24.05.2016.
 */
public class Logger extends MarkerIgnoringBase
{
    private final java.util.logging.Logger mLogger;

    //

    Logger( java.util.logging.Logger logger )
    {
        mLogger = logger;
    }

    //

    @Override
    public String getName()
    {
        return mLogger.getName();
    }

    @Override
    public boolean isTraceEnabled()
    {
        return mLogger.isLoggable( Level.FINEST );
    }

    @Override
    public void trace( String s )
    {
        log( Level.FINEST, "{}", new Object[]{s}, null );
    }

    @Override
    public void trace( String s, Object o )
    {
        log( Level.FINEST, s, new Object[]{o}, null );
    }

    @Override
    public void trace( String s, Object o1, Object o2 )
    {
        log( Level.FINEST, s, new Object[]{o1,o2}, null );
    }

    @Override
    public void trace( String s, Object... objects )
    {
        log( Level.FINEST, s, objects, null );
    }

    @Override
    public void trace( String s, Throwable t )
    {
        log( Level.FINEST, "{}", new Object[]{s}, t );
    }

    @Override
    public boolean isDebugEnabled()
    {
        return mLogger.isLoggable( Level.FINE ) || mLogger.isLoggable( Level.FINER );
    }

    @Override
    public void debug( String s )
    {
        log( Level.FINE, "{}", new Object[]{s}, null );
    }

    @Override
    public void debug( String s, Object o )
    {
        log( Level.FINE, s, new Object[]{o}, null );
    }

    @Override
    public void debug( String s, Object o1, Object o2 )
    {
        log( Level.FINE, s, new Object[]{o1,o2}, null );
    }

    @Override
    public void debug( String s, Object... objects )
    {
        log( Level.FINE, s, objects, null );
    }

    @Override
    public void debug( String s, Throwable t )
    {
        log( Level.FINE, "{}", new Object[]{s}, t );
    }

    @Override
    public boolean isInfoEnabled()
    {
        return mLogger.isLoggable( Level.INFO ) || mLogger.isLoggable( Level.CONFIG );
    }

    @Override
    public void info( String s )
    {
        log( Level.INFO, "{}", new Object[]{s}, null );
    }

    @Override
    public void info( String s, Object o )
    {
        log( Level.INFO, s, new Object[]{o}, null );
    }

    @Override
    public void info( String s, Object o1, Object o2 )
    {
        log( Level.INFO, s, new Object[]{o1,o2}, null );
    }

    @Override
    public void info( String s, Object... objects )
    {
        log( Level.INFO, s, objects, null );
    }

    @Override
    public void info( String s, Throwable t )
    {
        log( Level.INFO, "{}", new Object[]{s}, t );
    }

    @Override
    public boolean isWarnEnabled()
    {
        return mLogger.isLoggable( Level.WARNING );
    }

    @Override
    public void warn( String s )
    {
        log( Level.WARNING, "{}", new Object[]{s}, null );
    }

    @Override
    public void warn( String s, Object o )
    {
        log( Level.WARNING, s, new Object[]{o}, null );
    }

    @Override
    public void warn( String s, Object o1, Object o2 )
    {
        log( Level.WARNING, s, new Object[]{o1,o2}, null );
    }

    @Override
    public void warn( String s, Object... objects )
    {
        log( Level.WARNING, s, objects, null );
    }

    @Override
    public void warn( String s, Throwable t )
    {
        log( Level.WARNING, "{}", new Object[]{s}, t );
    }

    @Override
    public boolean isErrorEnabled()
    {
        return mLogger.isLoggable( Level.SEVERE );
    }

    @Override
    public void error( String s )
    {
        log( Level.SEVERE, "{}", new Object[]{s}, null );
    }

    @Override
    public void error( String s, Object o )
    {
        log( Level.SEVERE, s, new Object[]{o}, null );
    }

    @Override
    public void error( String s, Object o1, Object o2 )
    {
        log( Level.SEVERE, s, new Object[]{o1,o2}, null );
    }

    @Override
    public void error( String s, Object... objects )
    {
        log( Level.SEVERE, s, objects, null );
    }

    @Override
    public void error( String s, Throwable t )
    {
        log( Level.SEVERE, "{}", new Object[]{s}, t );
    }

    //

    private void log( Level level, String format, Object[] objects, Throwable throwable )
    {
        if ( !mLogger.isLoggable( level ) ) return;
        LogRecord record = new LogRecord( level, slf4jFormatToJulFormat( format ) );
        record.setLoggerName( mLogger.getName() );
        record.setParameters( objects );
        record.setThrown( throwable );
        setLogRecordSource( record );
        mLogger.log( record );
    }

    //

    private String slf4jFormatToJulFormat( String format )
    {
        int i = 0;
        StringBuffer buffer = new StringBuffer();
        Pattern pattern = Pattern.compile( "\\{\\s*\\}" );
        Matcher matcher = pattern.matcher( format );
        while ( matcher.find() ) matcher.appendReplacement( buffer, ("{"+(i++)+"}") );
        matcher.appendTail( buffer );
        return buffer.toString();
    }

    private void setLogRecordSource( LogRecord record )
    {
        String thisClassName = getClass().getName();
        boolean thisClassFound = false;

        new Throwable().printStackTrace();

        StackTraceElement[] elements = new Throwable().getStackTrace();
        for ( StackTraceElement element : elements )
        {
            if ( element.getClassName().startsWith( thisClassName ) )
            {
                thisClassFound = true;
            }
            else if ( thisClassFound )
            {
                record.setSourceClassName( element.getClassName() );
                record.setSourceMethodName( element.getMethodName() );
            }
        }
    }
}
