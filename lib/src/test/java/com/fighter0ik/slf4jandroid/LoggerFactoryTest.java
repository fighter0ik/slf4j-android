package com.fighter0ik.slf4jandroid;

import org.junit.Test;

import java.util.Enumeration;
import java.util.logging.LogManager;

/**
 * Created by DS on 26.05.2016.
 */
public class LoggerFactoryTest extends Assert
{
    private static final String sName = LoggerFactoryTest.class.getName();

    //

    @Test( expected = IllegalArgumentException.class)
    public void testConstructor()
    {
        new LoggerFactory( null );
    }

    @Test
    public void testGetLogger()
    {
        String name = sName;

        org.slf4j.Logger l1 = org.slf4j.LoggerFactory.getLogger( name );
        org.slf4j.Logger l2 = org.slf4j.LoggerFactory.getLogger( name );

        assertNotNull( l1 );
        assertNotNull( l2 );

        assertEquals( Logger.class.getName(), new Object[]{l1.getClass().getName(), l2.getClass().getName()} );

        assertSame( l1, l2 );
        assertEquals( l1.getName(), l2.getName() );

        int i = 0;
        Enumeration<String> names = LogManager.getLogManager().getLoggerNames();
        while ( names.hasMoreElements() ) if ( name.equals( names.nextElement() ) ) i++;
        assertEquals( i, 1 );
    }
}
