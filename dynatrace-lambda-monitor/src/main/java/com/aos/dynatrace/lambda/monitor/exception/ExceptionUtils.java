package com.aos.dynatrace.lambda.monitor.exception;

/**
 * @author Wagner Alves
 */
public class ExceptionUtils {

    /**
     * It returs stackTrace as a string
     * 
     * @param e
     * @return
     */
    public static String getStackTrace(Exception e) {
        StringBuilder str = new StringBuilder();
        StackTraceElement[] stackTrace = e.getStackTrace();

        for (int i = 0; i < stackTrace.length; i++) {
            str.append(stackTrace[i]);
            str.append(" ");
        }

        return str.toString();
    }

}
