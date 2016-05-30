package com.fighter0ik.slf4jandroid;

/**
 * Created by DS on 26.05.2016.
 */
public class Assert extends org.junit.Assert
{
    public void assertEquals( String message, Object expected, Object... actual )
    {
        for ( Object a : actual ) assertEquals( message, expected, a );
    }

    public void assertEquals( Object expected, Object... actual )
    {
        assertEquals( null, expected, actual );
    }
}
