package com.aos.dynatrace.lambda.monitor.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.aos.dynatrace.lambda.monitor.DynatraceMonitor;
import com.aos.dynatrace.lambda.monitor.exception.DynatraceMonitorException;
import com.aos.dynatrace.lambda.monitor.starter.DynatraceMonitorStarter;

/**
 * Class for testing Dynatrace Monitor Starter
 * 
 * @author Wagner Alves
 */
public class DynatraceMonitorStarterTest {

    private static final String PROPERTIES_FILE_NAME = "app.properties";

    private DynatraceMonitor monitor;

    @Before
    public void setUp() {
        monitor = new DynatraceMonitorStarter(PROPERTIES_FILE_NAME).getMonitor();
    }

    @Test
    public void shouldHaveADynatraceMonitorInstance() {
        assertNotNull(monitor);
    }

    @Test
    public void shouldHaveAnDynatraceMonitorSessionInstance() {
        try {
            assertNotNull(monitor.getSesssion());
        } catch (DynatraceMonitorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldShutdownAnDynatraceMonitorInstance() throws DynatraceMonitorException {
        try {
            monitor.getSesssion().end();
            monitor.shutDown();
        } catch (DynatraceMonitorException e1) {
            e1.printStackTrace();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ;

        assertNotNull(monitor.getSesssion());
        assertNotNull(monitor);
    }

}
