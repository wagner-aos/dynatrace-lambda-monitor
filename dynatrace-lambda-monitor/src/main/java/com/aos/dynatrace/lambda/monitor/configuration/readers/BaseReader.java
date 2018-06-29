package com.aos.dynatrace.lambda.monitor.configuration.readers;

/**
 * Base Class for Properties Reader
 * 
 * @author Wagner Alves
 */
public class BaseReader implements PropertiesReader {

    protected Boolean enableMonitor;
    protected Boolean enableVerbose;
    protected String applicationName;
    protected String applicationID;
    protected Long deviceID;
    protected String endpointURL;
    protected String clientIP;
    protected String user;
    protected String applicationVersion;
    protected String operatingSystem;
    protected String manufacturer;
    protected String modelID;

    public Boolean isEnableMonitor() {
        return enableMonitor;
    }

    public void setEnableMonitor(Boolean enableMonitor) {
        this.enableMonitor = enableMonitor;
    }

    public Boolean isEnableVerbose() {
        return enableVerbose;
    }

    public void setEnableVerbose(Boolean enableVerbose) {
        this.enableVerbose = enableVerbose;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public Long getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Long deviceID) {
        this.deviceID = deviceID;
    }

    public String getEndpointURL() {
        return endpointURL;
    }

    public void setEndpointURL(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModelID() {
        return modelID;
    }

    public void setModelID(String modelID) {
        this.modelID = modelID;
    }

    @Override
    public BaseReader readProperties() {
        return this;
    }

}
