package com.fighter0ik.slf4jandroid;

import org.junit.Test;
import org.slf4j.ILoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

/**
 * Created by DS on 26.05.2016.
 */
public class StaticLoggerBinderTest extends Assert
{
    @Test
    public void test()
    {
        StaticLoggerBinder b = StaticLoggerBinder.getSingleton();

        assertNotNull( b );
        assertSame( b, StaticLoggerBinder.getSingleton() );

        ILoggerFactory f = b.getLoggerFactory();
        String s = b.getLoggerFactoryClassStr();

        assertNotNull( f );
        assertNotNull( s );

        assertSame( f, b.getLoggerFactory() );
        assertSame( s, b.getLoggerFactoryClassStr() );

        assertEquals( LoggerFactory.class.getName(), new Object[]{f.getClass().getName(), s} );
    }
}
