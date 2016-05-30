package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

import com.fighter0ik.slf4jandroid.LoggerFactory;

/**
 * Created by DS on 23.05.2016.
 */
public class StaticLoggerBinder implements LoggerFactoryBinder
{
    public static String REQUESTED_API_VERSION = "1.7";

    //

    private static final StaticLoggerBinder sSingleton = new StaticLoggerBinder();

    public static StaticLoggerBinder getSingleton()
    {
        return sSingleton;
    }

    //

    private final ILoggerFactory mFactory;
    private final String mFactoryClassStr;

    //

    private StaticLoggerBinder()
    {
        mFactory = new LoggerFactory( new LoggerFactoryAccessor() );
        mFactoryClassStr = LoggerFactory.class.getName();
    }

    //

    @Override
    public ILoggerFactory getLoggerFactory()
    {
        return mFactory;
    }

    @Override
    public String getLoggerFactoryClassStr()
    {
        return mFactoryClassStr;
    }

    //

    public static final class LoggerFactoryAccessor
    {
        private LoggerFactoryAccessor() {}
    }
}
