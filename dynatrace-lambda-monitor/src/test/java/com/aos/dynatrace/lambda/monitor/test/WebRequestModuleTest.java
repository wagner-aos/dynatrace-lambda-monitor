package com.aos.dynatrace.lambda.monitor.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.aos.dynatrace.lambda.monitor.DynatraceMonitor;
import com.aos.dynatrace.lambda.monitor.exception.DynatraceMonitorException;
import com.aos.dynatrace.lambda.monitor.starter.DynatraceMonitorStarter;
import com.aos.dynatrace.lambda.monitor.tracer.WebRequestModule;
import com.dynatrace.openkit.api.Action;
import com.dynatrace.openkit.api.RootAction;

/**
 * @author Wagner Alves
 */
public class WebRequestModuleTest extends WebRequestModule {

    private static final String PROPERTIES_FILE_NAME = "app.properties";
    private DynatraceMonitor monitor;
    private RootAction rootAction;

    @Before
    public void setUp() {
        monitor = new DynatraceMonitorStarter(PROPERTIES_FILE_NAME).getMonitor();
    }

    @Test
    public void shouldCreateSession() {
        try {
            monitor.createSession();
            assertNotNull(monitor.getSesssion());
            monitor.shutDown();
        } catch (DynatraceMonitorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldCreateRootAction() {
        try {
            monitor.createRootAction("Root Action Test");
            assertNotNull(monitor);
            assertNotNull(monitor.getRootAction());

            monitor.shutDown();

        } catch (DynatraceMonitorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldExecuteWebRequest() throws DynatraceMonitorException {

        monitor.createRootAction("Postman GET Users");
        rootAction = monitor.getRootAction();

        try {
            // execute a GET request to the postman echo API and trace request time and size
            executeAndTraceWebRequest(rootAction, "https://postman-echo.com/get?query=users", null);

        } catch (Exception e) {
            System.out.println("Failed to Process WebRequest");

        }

        Action childAction = monitor.createChildAction("Postman POST posts");

        try {
            // execute a POST request to the postman echo API and trace request time and size
            executeAndTraceWebRequest(rootAction, "https://postman-echo.com/post",
                    "This is content that we want to be processed by the server");
        } catch (Exception e) {
            System.out.println("Failed to Process WebRequest");

        }

        childAction.leaveAction();
        monitor.leaveRootAction();
        monitor.shutDown();

    }

}
