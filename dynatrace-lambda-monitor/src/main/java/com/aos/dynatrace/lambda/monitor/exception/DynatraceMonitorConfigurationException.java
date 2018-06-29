package com.aos.dynatrace.lambda.monitor.exception;

/**
 * Exception Class for Dynatrace Configuration
 * 
 * @author Wagner Alves
 *
 */
public class DynatraceMonitorConfigurationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public DynatraceMonitorConfigurationException(String message) {
        super(message);
    }

}
