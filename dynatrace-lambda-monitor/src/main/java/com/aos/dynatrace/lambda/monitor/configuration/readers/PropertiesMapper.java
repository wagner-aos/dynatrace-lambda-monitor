package com.aos.dynatrace.lambda.monitor.configuration.readers;

/**
 * Mapper Class for setting variables
 *  
 * @author wagner-aos
 *
 */
public class PropertiesMapper {

    private BaseReader reader;

    /**
     * Constructor
     */
    public PropertiesMapper() {
        reader = new BaseReader();
    }

    /**
     * It sets all values according to parameter received and, returns BaseReader
     *  
     * @param reader
     * @return
     */
    public BaseReader setValues(BaseReader reader) {

        
        if (reader.isEnableMonitor() != null) {
            this.reader.setEnableMonitor(reader.isEnableMonitor());
        }
        
        if (reader.isEnableVerbose() != null) {
            this.reader.setEnableVerbose(reader.isEnableVerbose());
        }

        if (reader.getApplicationName() != null && !reader.getApplicationName().isEmpty()) {
            this.reader.setApplicationName(reader.getApplicationName());
        }

        if (reader.getApplicationID() != null && !reader.getApplicationID().isEmpty()) {
            this.reader.setApplicationID(reader.getApplicationID());
        }

        if (reader.getDeviceID() != null) {
            this.reader.setDeviceID(reader.getDeviceID());
        }

        if (reader.getEndpointURL() != null && !reader.getEndpointURL().isEmpty()) {
            this.reader.setEndpointURL(reader.getEndpointURL());
        }

        if (reader.getClientIP() != null && !reader.getClientIP().isEmpty()) {
            this.reader.setClientIP(reader.getClientIP());
        }

        if (reader.getUser() != null && !reader.getUser().isEmpty()) {
            this.reader.setUser(reader.getUser());
        }
        if (reader.getApplicationVersion() != null && !reader.getApplicationVersion().isEmpty()) {
            this.reader.setApplicationVersion(reader.getApplicationVersion());
        }

        if (reader.getOperatingSystem() != null && !reader.getOperatingSystem().isEmpty()) {
            this.reader.setOperatingSystem(reader.getOperatingSystem());
        }

        if (reader.getManufacturer() != null && !reader.getManufacturer().isEmpty()) {
            this.reader.setManufacturer(reader.getManufacturer());
        }

        if (reader.getModelID() != null && !reader.getModelID().isEmpty()) {
            this.reader.setModelID(reader.getModelID());
        }

        return this.reader;
    }

}
