package com.fighter0ik.slf4jandroid;

import org.junit.Test;

/**
 * Created by DS on 26.05.2016.
 */
public abstract class ConfigurationTest extends Assert implements Configuration
{
    @Test
    public void test()
    {
        apply();
        check();
    }

    public abstract void check();
}
