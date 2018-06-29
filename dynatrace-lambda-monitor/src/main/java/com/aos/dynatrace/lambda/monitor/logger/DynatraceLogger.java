package com.aos.dynatrace.lambda.monitor.logger;

/**
 * Logger class for Dynatrace operations based on Java SysOut.
 * 
 * @author Wagner Alves
 *
 */
public class DynatraceLogger{
	
	private DynatraceLogger(){}
	
	public static void log(String log) {
		System.out.println(log);
	}
	public static void log(Exception e) {
		e.printStackTrace();
	}

}
