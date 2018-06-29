/**
 * @author Wagner Alves
 *
 * @date 28-06-2018
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

/**
 * @author wagner-aos
 *
 */
public class ExceptionTest {
    
    private static final String PROPERTIES_FILE_NAME = "app.properties";

    private DynatraceMonitor monitor;

    @Before
    public void setUp() throws DynatraceMonitorException {
        monitor = new DynatraceMonitorStarter(PROPERTIES_FILE_NAME).getMonitor();
        monitor.createRootAction("RootActionForTesting");
    }
    
    @Test
    public void principalMethod() {
        
        try {
            
            processDivision();
            
        } catch (Exception e) {
            
            DynatraceLogger.log("Received Exception from processMethod");
            
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
    public void anotherPrincipalMethod() {
        
        try {
            
            returnOjectFromList();
            
        } catch (Exception e) {
            
            DynatraceLogger.log("Received Exception from processMethod");
            
            assertEquals(e.getMessage(), "/ by zero");

            DynatraceLogger.log("Sending Crash Report");
            String errorName = e.getClass().getName();
            String reason = e.getMessage();
            String stacktrace = ExceptionUtils.getStackTrace(e);

            // report the application crash via the session
            monitor.reportCrash(errorName, reason, stacktrace);
            
        }
           
    }
        
    @After
    public void shutdown() {
        monitor.shutDown();
    }
        
    /**
     * This method gonna send an exception to principalMethod() in order to
     * reportCrash to Dynatrace. 
     */
    private void processDivision() {
        div(5, 0);
    }
    
    /**
     * This method won't send an exception to principalMethod(). 
     */
    private String returnOjectFromList() {
        try {
            return new ArrayList<String>().get(0);
        } catch (Exception e) {
            DynatraceLogger.log("This method ends here!");
            e.printStackTrace();
        }
        return null;
    }
    
    private int div(int numerator, int denominator) {
        return numerator / denominator;
    }

}
