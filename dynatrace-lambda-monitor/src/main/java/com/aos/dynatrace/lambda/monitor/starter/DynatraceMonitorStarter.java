package com.aos.dynatrace.lambda.monitor.starter;

import com.aos.dynatrace.lambda.monitor.DynatraceMonitor;
import com.aos.dynatrace.lambda.monitor.configuration.DynatraceOpenKitConfiguration;
import com.aos.dynatrace.lambda.monitor.configuration.readers.BaseReader;
import com.aos.dynatrace.lambda.monitor.configuration.readers.DynatraceEnvVariablesReader;
import com.aos.dynatrace.lambda.monitor.configuration.readers.DynatracePropertiesFileReader;
import com.aos.dynatrace.lambda.monitor.configuration.readers.DynatracePropertiesReaderChain;
import com.aos.dynatrace.lambda.monitor.exception.DynatraceMonitorConfigurationException;
import com.aos.dynatrace.lambda.monitor.exception.DynatraceMonitorException;
import com.aos.dynatrace.lambda.monitor.logger.DynatraceLogger;

/**
 * Class for Dynatrace Monitor starting.
 * 
 * @author Wagner Alves
 */
public class DynatraceMonitorStarter {

    private DynatraceMonitor monitor;
    private BaseReader reader;

    public DynatraceMonitorStarter() {
        readProperties(null);
        startMonitor();
    }

    public DynatraceMonitorStarter(String propertiesFileName) {
        readProperties(propertiesFileName);
        startMonitor();
    }

    private void readProperties(String fileName) {
        reader = new DynatracePropertiesReaderChain(
                new DynatracePropertiesFileReader(fileName),
                new DynatraceEnvVariablesReader()).getPropertiesReader();
    }

    /**
     * It starts Dynatrace Monitor
     */
    private void startMonitor() {
        try {
            monitor = new DynatraceOpenKitConfiguration()
                    .withEnableMonitor(reader.isEnableMonitor())
                    .withEnableVerbose(reader.isEnableVerbose())
                    .withApplicationID(reader.getApplicationID())
                    .withApplicationName(reader.getApplicationName())
                    .withApplicationVersion(reader.getApplicationVersion())
                    .withClientIP(reader.getClientIP())
                    .withDeviceID(reader.getDeviceID())
                    .withEndpointURL(reader.getEndpointURL())
                    .withManufacturer(reader.getManufacturer())
                    .withModelID(reader.getModelID())
                    .withOperatingSystem(reader.getOperatingSystem())
                    .withUser(reader.getUser())
                    .build();

            if (reader.isEnableMonitor()) {
                monitor.initializeOpenKit();
                monitor.createSession();
            }

        } catch (DynatraceMonitorConfigurationException mce) {
            DynatraceLogger.log(mce);
        } catch (DynatraceMonitorException me) {
            DynatraceLogger.log(me);
        }
    }

    /**
     * It returns a Dynatrace Monitor
     * 
     * @return DynatraceMonitor
     */
    public DynatraceMonitor getMonitor() {
        if (!reader.isEnableMonitor()) {
            DynatraceLogger.log("Dynatrace Monitor is not enabled");
        }
        return monitor;
    }

}
