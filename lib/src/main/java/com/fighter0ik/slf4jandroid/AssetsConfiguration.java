package com.fighter0ik.slf4jandroid;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

/**
 * Created by DS on 24.05.2016.
 */
public class AssetsConfiguration implements Configuration
{
    private final Context mContext;
    private final String mName;

    //

    public AssetsConfiguration( Context context, String name )
    {
        mContext = context;
        mName = name;
    }

    //

    @Override
    public void apply()
    {
        InputStream stream = null;
        try
        {
            stream = mContext.getAssets().open( mName );
            LogManager.getLogManager().readConfiguration( stream );
        }
        catch ( IOException ex )
        {}
        finally
        {
            if ( stream!=null )
            {
                try { stream.close(); }
                catch ( IOException e ) {}
            }
        }
    }
}
