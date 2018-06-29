package com.aos.dynatrace.lambda.monitor.configuration.readers;

/**
 * Class for reading environment variables
 * 
 * @author Wagner Alves
 */
public class DynatraceEnvVariablesReader extends BaseReader {

    public BaseReader readProperties() {

        if(System.getenv("DYNATRACE_ENABLE_MONITOR") != null) {
            enableMonitor = Boolean.parseBoolean(System.getenv("DYNATRACE_ENABLE_MONITOR"));
        }

        if (System.getenv("DYNATRACE_ENABLE_VERBOSE") != null) {
            enableVerbose = Boolean.parseBoolean(System.getenv("DYNATRACE_ENABLE_VERBOSE"));
        }

        String applicationNameEnv = System.getenv("DYNATRACE_APPLICATION_NAME");
        if (applicationNameEnv != null && !applicationNameEnv.isEmpty()) {
            applicationName = applicationNameEnv;
        }

        String applicationIDEnv = System.getenv("DYNATRACE_APPLICATION_ID");
        if (applicationIDEnv != null && !applicationIDEnv.isEmpty()) {
            applicationID = applicationIDEnv;
        }

        String deviceIDEnv = System.getenv("DYNATRACE_DEVICE_ID");
        if (deviceIDEnv != null && !deviceIDEnv.isEmpty()) {
            deviceID = Long.valueOf(deviceIDEnv);
        }

        String endpointURLEnv = System.getenv("DYNATRACE_ENDPOINT_URL");
        if (endpointURLEnv != null && !endpointURLEnv.isEmpty()) {
            endpointURL = endpointURLEnv;
        }

        String clientIPEnv = System.getenv("DYNATRACE_CLIENT_IP");
        if (clientIPEnv != null && !clientIPEnv.isEmpty()) {
            clientIP = clientIPEnv;
        }

        String userEnv = System.getenv("DYNATRACE_USER");
        if (userEnv != null && !userEnv.isEmpty()) {
            user = userEnv;
        }

        String applicationVersionEnv = System.getenv("DYNATRACE_APPLICATION_VERSION");
        if (applicationVersionEnv != null && !applicationVersionEnv.isEmpty()) {
            applicationVersion = applicationVersionEnv;
        }

        String operatingSystemEnv = System.getenv("DYNATRACE_OPERATION_SYSTEM");
        if (operatingSystemEnv != null && !operatingSystemEnv.isEmpty()) {
            operatingSystem = operatingSystemEnv;
        }

        String manufacturerEnv = System.getenv("DYNATRACE_MANUFACTURER");
        if (manufacturerEnv != null && !manufacturerEnv.isEmpty()) {
            manufacturer = manufacturerEnv;
        }

        String modelIDEnv = System.getenv("DYNATRACE_MODEL_ID");
        if (modelIDEnv != null && !modelIDEnv.isEmpty()) {
            modelID = modelIDEnv;
        }

        return this;

    }

}
