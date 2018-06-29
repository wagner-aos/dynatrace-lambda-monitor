package com.aos.dynatrace.lambda.monitor.test;

import org.junit.Before;
import org.junit.Test;

import com.aos.dynatrace.lambda.monitor.DynatraceMonitor;
import com.aos.dynatrace.lambda.monitor.exception.DynatraceMonitorException;
import com.aos.dynatrace.lambda.monitor.starter.DynatraceMonitorStarter;
import com.aos.dynatrace.lambda.monitor.tracer.WebRequestModule;
import com.dynatrace.openkit.api.RootAction;

/**
 * Class for testing WebRequesModule
 * 
 * @author wagner-aos
 *
 */
public class ApiCallTest extends WebRequestModule {

    private static final String PROPERTIES_FILE_NAME = "app.properties";

    private DynatraceMonitor monitor;
    private RootAction rootAction;

    @Before
    public void setUp() {
        monitor = new DynatraceMonitorStarter(PROPERTIES_FILE_NAME).getMonitor();
    }

    @Test
    public void shouldCallApi() throws DynatraceMonitorException {

        monitor.createRootAction("Root Action");
        rootAction = monitor.getRootAction();

        try {
            executeAndTraceWebRequest(rootAction, "https://0s6vdb3xuh.execute-api.us-east-1.amazonaws.com/dev/v1/ceps/18047205", null);
        } catch (Exception e) {
            System.out.println("Failed to Process WebRequest");

        }

        monitor.leaveRootAction();
        monitor.shutDown();

    }

}
