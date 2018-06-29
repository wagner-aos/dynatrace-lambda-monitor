package com.aos.dynatrace.lambda.monitor.exception;

/**
 * Exception Class for Dynatrace Monitor
 * 
 * @author Wagner Alves
 *
 */
public class DynatraceMonitorException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public DynatraceMonitorException(String message) {
        super(message);
    }

}
