package com.fighter0ik.slf4jandroid;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by DS on 26.05.2016.
 */
@RunWith( Parameterized.class )
public class LevelConfigurationTest extends ConfigurationTest
{
    private static final String sName = LoggerFactoryTest.class.getName();

    //

    private final Level mLevel;

    //

    public LevelConfigurationTest( Level level )
    {
        mLevel = level;
    }

    @Override
    public void check()
    {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger( sName );

        if ( mLevel==null )
        {
            // TODO check null case
            return;
        }

        int level = mLevel.intValue();

        if ( level>Level.SEVERE.intValue() )
        {
            assertFalse( logger.isTraceEnabled() );
            assertFalse( logger.isDebugEnabled() );
            assertFalse( logger.isInfoEnabled() );
            assertFalse( logger.isWarnEnabled() );
            assertFalse( logger.isErrorEnabled() );
        }
        else if ( level>Level.WARNING.intValue() )
        {
            assertFalse( logger.isTraceEnabled() );
            assertFalse( logger.isDebugEnabled() );
            assertFalse( logger.isInfoEnabled() );
            assertFalse( logger.isWarnEnabled() );
            assertTrue( logger.isErrorEnabled() );
        }
        else if ( level>Level.INFO.intValue() )
        {
            assertFalse( logger.isTraceEnabled() );
            assertFalse( logger.isDebugEnabled() );
            assertFalse( logger.isInfoEnabled() );
            assertTrue( logger.isWarnEnabled() );
            assertTrue( logger.isErrorEnabled() );
        }
        else if ( level>Level.CONFIG.intValue() )
        {
            assertFalse( logger.isTraceEnabled() );
            assertFalse( logger.isDebugEnabled() );
            assertTrue( logger.isInfoEnabled() );
            assertTrue( logger.isWarnEnabled() );
            assertTrue( logger.isErrorEnabled() );
        }
        else if ( level>Level.FINE.intValue() )
        {
            assertFalse( logger.isTraceEnabled() );
            assertFalse( logger.isDebugEnabled() );
            assertTrue( logger.isInfoEnabled() );
            assertTrue( logger.isWarnEnabled() );
            assertTrue( logger.isErrorEnabled() );
        }
        else if ( level>Level.FINER.intValue() )
        {
            assertFalse( logger.isTraceEnabled() );
            assertTrue( logger.isDebugEnabled() );
            assertTrue( logger.isInfoEnabled() );
            assertTrue( logger.isWarnEnabled() );
            assertTrue( logger.isErrorEnabled() );
        }
        else if ( level>Level.FINEST.intValue() )
        {
            assertFalse( logger.isTraceEnabled() );
            assertTrue( logger.isDebugEnabled() );
            assertTrue( logger.isInfoEnabled() );
            assertTrue( logger.isWarnEnabled() );
            assertTrue( logger.isErrorEnabled() );
        }
        else
        {
            assertTrue( logger.isTraceEnabled() );
            assertTrue( logger.isDebugEnabled() );
            assertTrue( logger.isInfoEnabled() );
            assertTrue( logger.isWarnEnabled() );
            assertTrue( logger.isErrorEnabled() );
        }
    }

    @Override
    public void apply()
    {
        java.util.logging.Logger logger = Logger.getLogger( sName );
        logger.setLevel( mLevel );
    }

    @Parameterized.Parameters( name = "{index}: level={0}" )
    public static Collection<Object> levels()
    {
        List<Object> levels = new LinkedList<>();
        levels.add( null );
        levels.add( Level.OFF );
        levels.add( Level.ALL );
        levels.add( Level.FINEST );
        levels.add( Level.FINER );
        levels.add( Level.FINE );
        levels.add( Level.CONFIG );
        levels.add( Level.INFO );
        levels.add( Level.WARNING );
        levels.add( Level.SEVERE );
        levels.add( new TestLevel( "ALMOST_OFF", Level.OFF.intValue()-1 ) );
        levels.add( new TestLevel( "ABOVE_SEVERE", Level.SEVERE.intValue()+1 ) );
        levels.add( new TestLevel( "ABOVE_WARNING", Level.WARNING.intValue()+1 ) );
        levels.add( new TestLevel( "ABOVE_INFO", Level.INFO.intValue()+1 ) );
        levels.add( new TestLevel( "ABOVE_CONFIG", Level.CONFIG.intValue()+1 ) );
        levels.add( new TestLevel( "ABOVE_FINE", Level.FINE.intValue()+1 ) );
        levels.add( new TestLevel( "ABOVE_FINER", Level.FINER.intValue()+1 ) );
        levels.add( new TestLevel( "ABOVE_FINES", Level.FINEST.intValue()+1 ) );
        levels.add( new TestLevel( "ALMOST_ALL", Level.ALL.intValue()+1 ) );
        return levels;
    }

    //

    private static class TestLevel extends Level
    {
        private TestLevel( String name, int level )
        {
            super( name, level );
        }
    }
}
