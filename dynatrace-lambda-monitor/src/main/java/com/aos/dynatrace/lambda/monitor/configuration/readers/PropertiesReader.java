package com.aos.dynatrace.lambda.monitor.configuration.readers;

/**
 * 
 * @author wagner-aos
 *
 */
@FunctionalInterface
public interface PropertiesReader {

    public BaseReader readProperties();

}
