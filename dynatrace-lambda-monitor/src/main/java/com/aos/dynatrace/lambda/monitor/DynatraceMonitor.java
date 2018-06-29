package com.aos.dynatrace.lambda.monitor;

import com.aos.dynatrace.lambda.monitor.exception.DynatraceMonitorException;
import com.aos.dynatrace.lambda.monitor.logger.DynatraceLogger;
import com.dynatrace.openkit.api.Action;
import com.dynatrace.openkit.api.OpenKit;
import com.dynatrace.openkit.api.RootAction;
import com.dynatrace.openkit.api.Session;

/**
 * Class for Dynatrace OpenKit Monitor.
 * 
 * @author Wagner Alves
 */
public class DynatraceMonitor {

    private OpenKit openKit;
    private Session session;
    private RootAction rootAction;
    private String clientIP;
    private String user;
    private boolean enableMonitor;

    /**
     * Constructor
     * 
     * @param openKit
     * @param clientIP
     * @param user
     * @param enableMonitor
     */
    public DynatraceMonitor(OpenKit openKit, String clientIP, String user, boolean enableMonitor) {
        super();
        this.openKit = openKit;
        this.clientIP = clientIP;
        this.user = user;
        this.enableMonitor = enableMonitor;
    }

    /**
     * It starts OpenKit
     * 
     * @return
     */
    public boolean initializeOpenKit() {
        if (openKit != null && enableMonitor) {
            return openKit.waitForInitCompletion();
        }
        return false;
    }

    /**
     * It creates OpenKit session
     * 
     * @throws DynatraceMonitorException
     */
    public void createSession() throws DynatraceMonitorException {

        if (enableMonitor && openKit == null) {
            throw new DynatraceMonitorException("OpenKit hasn't been created!");
        }
        if (enableMonitor && !openKit.isInitialized()) {
            throw new DynatraceMonitorException("OpenKit hasn't been initialized!");
        }
        if (enableMonitor && clientIP == null) {
            throw new DynatraceMonitorException("ClientIP cannot be empty!");
        }
        if (enableMonitor && user == null) {
            throw new DynatraceMonitorException("User cannot be empty!");
        }

        if (enableMonitor) {
            session = openKit.createSession(clientIP);
            session.identifyUser(user);

            if (openKit.isInitialized() && session != null) {
                DynatraceLogger.log("DynatraceMonitor-Session created!!!");
            }
        }

    }

    /**
     * It returns a session
     * 
     * @return
     * @throws DynatraceMonitorException
     */
    public Session getSesssion() throws DynatraceMonitorException {
        if (enableMonitor && session == null) {
            throw new DynatraceMonitorException("Session hasn't been created!");
        }
        return session;
    }

    /**
     * It creates a RootAction
     * 
     * @param rootActionName
     * @throws DynatraceMonitorException
     */
    public void createRootAction(String rootActionName) throws DynatraceMonitorException {
        if (enableMonitor && session == null) {
            throw new DynatraceMonitorException("Session hasn't been created!");
        }
        if (enableMonitor && rootActionName == null) {
            throw new DynatraceMonitorException("RootAction name cannot be null!");
        }
        if (enableMonitor) {
            rootAction = session.enterAction(rootActionName);
        }
    }

    /**
     * It returns a RootAction
     * 
     * @return
     * @throws DynatraceMonitorException
     */
    public RootAction getRootAction() throws DynatraceMonitorException {
        if (enableMonitor && rootAction == null) {
            throw new DynatraceMonitorException("RootAction hasn't been created!");
        }
        return rootAction;
    }

    /**
     * It leaves a RootAction
     * 
     * @throws DynatraceMonitorException
     */
    public void leaveRootAction() throws DynatraceMonitorException {
        if (rootAction != null) {
            rootAction.leaveAction();
            shutDown();
        }
    }

    /**
     * It leaves all Actions, including RootAction
     * 
     * @param actions
     */
    public void leaveAllActions(Action... actions) {
        for (Action a : actions) {
            if (a != null)
                a.leaveAction();
        }
        try {
            leaveRootAction();
        } catch (DynatraceMonitorException e) {
            DynatraceLogger.log(e);
        }
    }

    /**
     * It creates a ChildAction
     * 
     * @param childActionName
     * @return
     * @throws DynatraceMonitorException
     */
    public Action createChildAction(String childActionName) throws DynatraceMonitorException {
        if (enableMonitor && rootAction == null) {
            throw new DynatraceMonitorException("RootAction hasn't been created!");
        }
        if (childActionName == null) {
            throw new DynatraceMonitorException("ChildAction name cannot be null!");
        }
        if (enableMonitor) {
            return rootAction.enterAction(childActionName);
        }
        return null;
    }

    /**
     * It reports a crash when an exception occurs
     * 
     * @param errorName
     * @param reason
     * @param stacktrace
     */
    public void reportCrash(String errorName, String reason, String stacktrace) {
        session.reportCrash(errorName, reason, stacktrace);
    }

    /**
     * It reports an error by Action
     * 
     * @param action
     * @param errorName
     * @param errorCode
     * @param reason
     */
    public void reportError(Action action, String errorName, int errorCode, String reason) {
        action.reportError(errorName, errorCode, reason);
    }

    /**
     * It reports an event by RootAction
     * 
     * @param eventName
     */
    public void reportEvent(String eventName) {
        rootAction.reportEvent(eventName);
    }

    /**
     * It reports an event by Action
     * 
     * @param eventName
     */
    public void reportEvent(Action action, String eventName) {
        action.reportEvent(eventName);
    }

    /**
     * It shutdown the session and OpenKit
     */
    public void shutDown() {
        shutDownSession();
        shutDownOpenkit();
    }

    private void shutDownSession() {
        if (session != null) {
            session.end();
        }
    }

    private void shutDownOpenkit() {
        if (openKit != null && openKit.isInitialized()) {
            openKit.shutdown();
        }
    }

}
