/**
 * @author Wagner Alves
 *
 * @date 28-06-2018
 */
package com.aos.dynatrace.lambda.monitor.test;

import java.util.ArrayList;

import org.junit.Test;

import com.aos.dynatrace.lambda.monitor.DynatraceMonitor;
import com.aos.dynatrace.lambda.monitor.exception.ExceptionUtils;
import com.aos.dynatrace.lambda.monitor.logger.DynatraceLogger;
import com.aos.dynatrace.lambda.monitor.starter.DynatraceMonitorStarter;
import com.dynatrace.openkit.api.Action;

/**
 * @author Wagner Alves
 *
 */
public class InnerExceptionTest {
    
    private static final String PROPERTIES_FILE_NAME = "app.properties";
 
    private DynatraceMonitor monitor;
    private Action childAction;
    
    @Test
    public void methodWithInnerException() {
        
        try {
            
            // Dynatrace
            monitor = new DynatraceMonitorStarter(PROPERTIES_FILE_NAME).getMonitor();
            monitor.createRootAction("HandlerStart");
            DynatraceLogger.log("Dynatrace RootAction: Handler Request - STARTED");
            
            childAction = monitor.createChildAction("getObjectFromList");
            try {
                
                returnOjectFromList();
                
            } catch (Exception e) {
                DynatraceLogger.log("Inner Exception");
                //DynatraceLogger.log(e);
                DynatraceLogger.log("Sending reportCrash to Dynatrace!");
                String errorName = e.getClass().getName();
                String reason = e.getMessage();
                String stacktrace = ExceptionUtils.getStackTrace(e);
                monitor.reportCrash(errorName, reason, stacktrace);
            }
            
        } catch (Exception e) {
            DynatraceLogger.log("Outter Exception");
        } finally {
            DynatraceLogger.log("Dynatrace RootAction: Handler Request - FINISHED");
            monitor.leaveAllActions(childAction);
        }
        
    }
    
    /**
     * This method will send an exception to principalMethod(). 
     */
    private String returnOjectFromList() {
       return new ArrayList<String>().get(0);
    }

}
