package com.aos.dynatrace.lambda.monitor.configuration.readers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.aos.dynatrace.lambda.monitor.logger.DynatraceLogger;

/**
 * Class for reading Properties File
 * 
 * @author Wagner Alves
 */
public class DynatracePropertiesFileReader extends BaseReader {

    private static final String PROPERTIES_FILE_NAME = "application.properties";
    private String fileName;

    public DynatracePropertiesFileReader(String fileName) {
        this.fileName = fileName;
    }

    public BaseReader readProperties() {

        String file = PROPERTIES_FILE_NAME;
        String rootPath = null;

        if (fileName != null && !fileName.isEmpty()) {
            file = fileName;
        }

        try {
            DynatraceLogger.log("File " + file + " found!");
            rootPath = Thread.currentThread().getContextClassLoader().getResource(file).getPath();
        } catch (NullPointerException npe) {
            DynatraceLogger.log("File " + file + " not found!");
            DynatraceLogger.log(npe);
        }

        try (FileInputStream fis = new FileInputStream(rootPath)) {

            final Properties props = new Properties();
            props.load(fis);

            enableMonitor = Boolean.valueOf(props.getProperty("dynatrace.enable"));
            enableVerbose = Boolean.valueOf(props.getProperty("dynatrace.enable.verbose"));
            applicationName = props.getProperty("dynatrace.application.name");
            applicationID = props.getProperty("dynatrace.application.id");
            deviceID = Long.parseLong(props.getProperty("dynatrace.device.id"));
            endpointURL = props.getProperty("dynatrace.endpoint.url");
            clientIP = props.getProperty("dynatrace.client.ip");
            user = props.getProperty("dynatrace.user");
            applicationVersion = props.getProperty("dynatrace.application.version");
            operatingSystem = props.getProperty("dynatrace.operating.system");
            manufacturer = props.getProperty("dynatrace.manufacturer");
            modelID = props.getProperty("dynatrace.model.id");

            return this;

        } catch (IOException e) {
            throw new IllegalStateException("Error when reading the properties file: " + file, e);
        }

    }

}
