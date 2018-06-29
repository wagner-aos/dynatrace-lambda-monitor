package com.aos.dynatrace.lambda.monitor.configuration;

import com.aos.dynatrace.lambda.monitor.DynatraceMonitor;
import com.aos.dynatrace.lambda.monitor.exception.DynatraceMonitorConfigurationException;
import com.dynatrace.openkit.AbstractOpenKitBuilder;
import com.dynatrace.openkit.DynatraceOpenKitBuilder;
import com.dynatrace.openkit.api.OpenKit;

/**
 * Builder Class Configuration for Dynatrace OpenKit
 * 
 * @author Wagner Alves
 *
 */
public class DynatraceOpenKitConfiguration {
		
	private boolean enableMonitor = true;
	private boolean enableVerbose;
	private String applicationName;
	private String applicationVersion;
	private String applicationID;
    private String operatingSystem;
    private String manufacturer;
    private String modelID;
	private Long deviceID;
	private String endpointURL;
	private String clientIP;
	private String user;
	
	public DynatraceOpenKitConfiguration withEnableMonitor(boolean enableMonitor) {
		this.enableMonitor = enableMonitor;
		return this;
	}
	
	public DynatraceOpenKitConfiguration withEnableVerbose(boolean enableVerbose) {
		this.enableVerbose = enableVerbose;
		return this;
	}
	
	public DynatraceOpenKitConfiguration withApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
		return this;
	}

	public DynatraceOpenKitConfiguration withOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
		return this;
	}

	public DynatraceOpenKitConfiguration withManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
		return this;
	}

	public DynatraceOpenKitConfiguration withModelID(String modelID) {
		this.modelID = modelID;
		return this;
	}

	public DynatraceOpenKitConfiguration withApplicationName(String applicationName) {
		this.applicationName = applicationName;
		return this;
	}

	public DynatraceOpenKitConfiguration withApplicationID(String applicationID) {
		this.applicationID = applicationID;
		return this;
	}

	public DynatraceOpenKitConfiguration withDeviceID(long deviceID) {
		this.deviceID = deviceID;
		return this;
	}

	public DynatraceOpenKitConfiguration withEndpointURL(String endpointURL) {
		this.endpointURL = endpointURL;
		return this;
	}

	public DynatraceOpenKitConfiguration withClientIP(String clientIP) {
		this.clientIP = clientIP;
		return this;
	}

	public DynatraceOpenKitConfiguration withUser(String user) {
		this.user = user;
		return this;
	}
	
	public DynatraceMonitor build() throws DynatraceMonitorConfigurationException{
		
		if(endpointURL == null) {
			throw new DynatraceMonitorConfigurationException("endpointURL cannot be empty!");
		}
		if(applicationID == null) {
			throw new DynatraceMonitorConfigurationException("applicationID cannot be empty!");
		}
		if(deviceID == null) {
			throw new DynatraceMonitorConfigurationException("deviceID cannot be empty!");
		}
		if(applicationName == null) {
			throw new DynatraceMonitorConfigurationException("applicationName cannot be empty!");
		}
		if(applicationVersion == null) {
			throw new DynatraceMonitorConfigurationException("applicationVersion cannot be empty!");
		}
		if(operatingSystem == null) {
			throw new DynatraceMonitorConfigurationException("operatingSystem cannot be empty!");
		}
		if(manufacturer == null) {
			throw new DynatraceMonitorConfigurationException("manufacturer cannot be empty!");
		}
		if(modelID == null) {
			throw new DynatraceMonitorConfigurationException("modelID cannot be empty!");
		}
		if(clientIP == null) {
			throw new DynatraceMonitorConfigurationException("ClientIP cannot be empty!");
		}
		
		OpenKit openKit = null;
		
		if(enableMonitor){
			 AbstractOpenKitBuilder builder  = new DynatraceOpenKitBuilder(endpointURL, applicationID, deviceID)
			    .withApplicationName(applicationName)
			    .withApplicationVersion(applicationVersion)
			    .withOperatingSystem(operatingSystem)
			    .withManufacturer(manufacturer)
			    .withModelID(modelID);
			 
			 if(enableVerbose){
				 builder.enableVerbose();
			 }
			    
			 openKit = builder.build();
			    
		}
				
		return new DynatraceMonitor(openKit, clientIP, user, enableMonitor);
		
	}

	
}
