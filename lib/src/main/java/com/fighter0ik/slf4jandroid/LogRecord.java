package com.fighter0ik.slf4jandroid;

import java.lang.ref.WeakReference;
import java.util.logging.Level;

/**
 * Created by DS on 24.05.2016.
 */
public class LogRecord extends java.util.logging.LogRecord
{
    private final WeakReference<Thread> mOsThread;

    //

    public LogRecord( Level level, String msg )
    {
        super( level, msg );
        Thread osThread = Thread.currentThread();
        mOsThread = new WeakReference<Thread>( osThread );
    }

    //

    public Thread getOsThread()
    {
        return mOsThread.get();
    }
}
