package com.fighter0ik.slf4jandroid;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.mockito.Mockito.*;

@RunWith(Parameterized.class )
public class FormatterTest extends Assert
{
    private final Formatter mFormatter;

    private final LogRecord mRecord;
    private final String mExpected;

    public FormatterTest( Formatter formatter, LogRecord record, String expected )
    {
        mFormatter = formatter;
        mRecord = record;
        mExpected = expected;
    }

    @Test
    public void test()
    {
        String actual = mFormatter.format( mRecord );
        assertEquals( dumpActualAndExpected( actual, mExpected ), actual, mExpected );
    }

    @Parameterized.Parameters ( name = "{index}: expected=[{2}]" )
    public static List<Object[]> parameters()
    {
        List<Object[]> parameters = new LinkedList<>();
        parameters.add( new Object[]{new Formatter(),createSpy( Level.ALL, "some non-formatted message" ),("ALL: 1970-01-01 "+String.format( "%tH", new Date(0) )+":00:00.000 | unknown thread. id=0 | com.fighter0ik.slf4jandroid.FormatterTest test() | some non-formatted message\n")} );
        // TODO some additional cases
        return parameters;
    }

    //

    private static LogRecord createSpy( Level level, String message )
    {
        LogRecord spy = spy( new LogRecord( level, message ) );

        when( spy.getThreadID() ).thenReturn( 0 );
        when( spy.getMillis() ).thenReturn( Long.valueOf( 0 ) );
        when( spy.getSourceClassName() ).thenReturn( FormatterTest.class.getName() );
        when( spy.getSourceMethodName() ).thenReturn( "test" );

        return spy;
    }

    private static String dumpActualAndExpected( String actual, String expected )
    {
        return "ACTUAL: "+actual+"\nACTUAL_LENGTH: "+actual.length()+"\nEXPECTED: "+expected+"\nEXPECTED_LENGTH: "+expected.length();
    }
}
