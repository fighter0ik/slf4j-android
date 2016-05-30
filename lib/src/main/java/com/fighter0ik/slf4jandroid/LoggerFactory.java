package com.fighter0ik.slf4jandroid;

import org.slf4j.*;
import org.slf4j.impl.StaticLoggerBinder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by DS on 23.05.2016.
 */
public class LoggerFactory implements ILoggerFactory
{
    private final Map<String, org.slf4j.Logger> mLoggers;
    private final ReentrantLock mLock;

    //

    public LoggerFactory( StaticLoggerBinder.LoggerFactoryAccessor accessor )
    {
        if ( accessor==null ) throw new IllegalArgumentException();
        mLoggers = new HashMap<>();
        mLock = new ReentrantLock();
    }

    //

    @Override
    public org.slf4j.Logger getLogger( String name )
    {
        org.slf4j.Logger logger;

        mLock.lock();

        logger = mLoggers.get( name );
        if ( logger==null )
        {
            java.util.logging.Logger l = java.util.logging.Logger.getLogger( name );
            logger = new com.fighter0ik.slf4jandroid.Logger( l );
            mLoggers.put( name, logger );
        }

        mLock.unlock();

        return logger;
    }
}
