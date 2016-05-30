package com.fighter0ik.slf4jandroid;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;

/**
 * Created by DS on 24.05.2016.
 */
public class ExtFilesDirHandler extends FileHandler
{
    private static final String sContextProviderKey = "contextProvider";
    private static final String sContextProviderMethodKey = "contextProviderMethod";

    //

    private static final String sDefaultContextProvider = "android.app.ActivityThread";
    private static final String sDefaultContextProviderMethod = "currentApplication";

    //

    private static final String sDefaultPattern = "log.%u.txt";

    //

    public ExtFilesDirHandler() throws IOException, NoContextException
    {
        super( getExtFilesDirPattern( null ) );
    }

    public ExtFilesDirHandler( String pattern ) throws IOException, NoContextException
    {
        super( getExtFilesDirPattern( pattern ) );
    }

    public ExtFilesDirHandler( String pattern, boolean append ) throws IOException, NoContextException
    {
        super( getExtFilesDirPattern( pattern ), append );
    }

    public ExtFilesDirHandler( String pattern, int limit, int count ) throws IOException, NoContextException
    {
        super( getExtFilesDirPattern( pattern ), limit, count );
    }

    public ExtFilesDirHandler( String pattern, int limit, int count, boolean append ) throws IOException, NoContextException
    {
        super( getExtFilesDirPattern( pattern ), limit, count, append );
    }

    public ExtFilesDirHandler( Context context ) throws IOException
    {
        super( getExtFilesDirPattern( context, null ) );
    }

    public ExtFilesDirHandler( Context context, String pattern ) throws IOException
    {
        super( getExtFilesDirPattern( context, pattern ) );
    }

    public ExtFilesDirHandler( Context context, String pattern, boolean append ) throws IOException
    {
        super( getExtFilesDirPattern( context, pattern ), append );
    }

    public ExtFilesDirHandler( Context context, String pattern, int limit, int count ) throws IOException
    {
        super( getExtFilesDirPattern( context, pattern ), limit, count );
    }

    public ExtFilesDirHandler( Context context, String pattern, int limit, int count, boolean append ) throws IOException
    {
        super( getExtFilesDirPattern( context, pattern ), limit, count, append );
    }

    //

    private static String getExtFilesDirPattern( String pattern ) throws NoContextException
    {
        Context context = getContext();
        if ( context==null ) throw new NoContextException();
        return getExtFilesDirPattern( context, pattern );
    }

    private static String getExtFilesDirPattern( Context context, String pattern )
    {
        if ( context==null )
        {
            throw new IllegalArgumentException();
        }

        if ( pattern==null || pattern.isEmpty() )
        {
            pattern = LogManager.getLogManager().getProperty( ExtFilesDirHandler.class.getName()+".pattern" );
            if ( pattern==null || pattern.isEmpty() ) pattern = sDefaultPattern;
        }

        File dir = context.getExternalFilesDir( null );
        File file = new File( dir, pattern );
        Log.d( ExtFilesDirHandler.class.getName(), file.getAbsolutePath() );
        return file.getAbsolutePath();
    }

    private static Context getContext()
    {
        try
        {
            LogManager manager = LogManager.getLogManager();

            String thisClassName = ExtFilesDirHandler.class.getName();

            String contextProvider = manager.getProperty( thisClassName+"."+sContextProviderKey );
            if ( contextProvider==null ) return getContextDefault();
            String contextProviderMethod = manager.getProperty( thisClassName+"."+sContextProviderMethodKey );
            if ( contextProviderMethod==null ) return getContextDefault();


            Class<?> cls = Class.forName( contextProvider );
            Method m = cls.getDeclaredMethod( contextProviderMethod );
            m.setAccessible( true );
            return (Context) m.invoke( null );
        }
        catch ( Exception ex )
        {
            System.err.println( ExtFilesDirHandler.class.getName()+" getContext() got exception:\n"+ex );
            return getContextDefault();
        }
    }

    private static Context getContextDefault()
    {
        try
        {
            Class<?> cls = Class.forName( sDefaultContextProvider );
            Method m = cls.getDeclaredMethod( sDefaultContextProviderMethod );
            m.setAccessible( true );
            return (Context) m.invoke( null, ((Object[]) null) );
        }
        catch ( Exception ex )
        {
            System.err.println( ExtFilesDirHandler.class.getName()+" getContextDefault() got exception:\n"+ex );
            return null;
        }
    }

    //

    public static final class NoContextException extends Exception {}
}
