/**
 * @author Wagner Alves
 * @date 27-06-2018
 */
package com.aos.dynatrace.lambda.monitor.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aos.dynatrace.lambda.monitor.DynatraceMonitor;
import com.aos.dynatrace.lambda.monitor.exception.DynatraceMonitorException;
import com.aos.dynatrace.lambda.monitor.exception.ExceptionUtils;
import com.aos.dynatrace.lambda.monitor.logger.DynatraceLogger;
import com.aos.dynatrace.lambda.monitor.starter.DynatraceMonitorStarter;
import com.dynatrace.openkit.api.Action;

/**
 * Class for testing Error and Crash Report
 * 
 * @author wagner-aos
 */
public class DynatraceReportErrorAndCrashTest {

    private static final String PROPERTIES_FILE_NAME = "app.properties";

    private DynatraceMonitor monitor;

    @Before
    public void setUp() throws DynatraceMonitorException {
        monitor = new DynatraceMonitorStarter(PROPERTIES_FILE_NAME).getMonitor();
        monitor.createRootAction("RootActionForTesting");
    }

    @Test
    public void shouldReportSomethingAfterError() throws DynatraceMonitorException {

        Action childActionForTesting = null;
        try {
            childActionForTesting = monitor.createChildAction("ChildActionForTesting");

            new ArrayList<>().get(0);

        } catch (Exception e) {
            assertEquals(e.getMessage(), "Index: 0, Size: 0");

            DynatraceLogger.log("Sending Error Report");
            monitor.reportError(childActionForTesting, "ErrorTest", 42, e.getMessage());
        }

    }

    @Test
    public void shouldReportSomethingAfterCrash() {

        int numerator = 5;
        int denominator = 0;

        try {

            System.out.println("Got: " + div(numerator, denominator));

        } catch (Exception e) {
            assertEquals(e.getMessage(), "/ by zero");

            DynatraceLogger.log("Sending Crash Report");
            String errorName = e.getClass().getName();
            String reason = e.getMessage();
            String stacktrace = ExceptionUtils.getStackTrace(e);

            // report the application crash via the session
            monitor.reportCrash(errorName, reason, stacktrace);

        }
    }

    @Test
    public void shouldReportEventByRootAction() {
        monitor.reportEvent("EventFromRootAction");
    }

    @Test
    public void shouldReportEventByAction() throws DynatraceMonitorException {
        Action childActionForTesting = monitor.createChildAction("ChildActionForTesting");
        monitor.reportEvent(childActionForTesting, "EventFromAction");
    }

    @After
    public void shutdown() {
        monitor.shutDown();
    }

    private int div(int numerator, int denominator) {
        return numerator / denominator;
    }

}
