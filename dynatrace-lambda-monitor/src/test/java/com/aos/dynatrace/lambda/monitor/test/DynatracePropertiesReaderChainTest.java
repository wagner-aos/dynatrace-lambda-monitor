package com.aos.dynatrace.lambda.monitor.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import com.aos.dynatrace.lambda.monitor.configuration.readers.BaseReader;
import com.aos.dynatrace.lambda.monitor.configuration.readers.DynatraceEnvVariablesReader;
import com.aos.dynatrace.lambda.monitor.configuration.readers.DynatracePropertiesFileReader;
import com.aos.dynatrace.lambda.monitor.configuration.readers.DynatracePropertiesReaderChain;

/**
 * @author Wagner Alves
 */
public class DynatracePropertiesReaderChainTest {

    private static final String PROPERTIES_FILE_NAME = "app.properties";

    public BaseReader reader;

    @Before
    public void setUp() {
        reader = new BaseReader();
    }

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Test
    public void shouldReadAllPropertiesFromFile() {

        readPropertiesFromFile();

        assertEquals(reader.isEnableMonitor(), true);
        assertEquals(reader.isEnableVerbose(), true);
        assertEquals(reader.getApplicationName(), "Dynatrace Lambda Monitor LIB");
        assertEquals(reader.getApplicationID(), "82564fc9-1617-4257-b26c-9a896056d42f");
        assertEquals(reader.getDeviceID(), new Long(42));
        assertEquals(reader.getEndpointURL(), "https://xzt878.dynatrace-managed.com:9999/mbeacon/75fcdbc5-1040-45f0-a7c9-322ea8f88765");
        assertEquals(reader.getClientIP(), "8.8.8.8");
        assertEquals(reader.getUser(), "wagner.oliveira@provider-it.com.br");
        assertEquals(reader.getApplicationVersion(), "1.0.0.0");
        assertEquals(reader.getOperatingSystem(), "Windows 10");
        assertEquals(reader.getManufacturer(), "MyCompany");
        assertEquals(reader.getModelID(), "MyModelID");

    }

    @Test
    public void shouldReadAllPropertiesFromFileAndEnvironmentVariables() {

        environmentVariables.set("DYNATRACE_ENABLE_MONITOR", "true");
        environmentVariables.set("DYNATRACE_ENABLE_VERBOSE", "true");
        environmentVariables.set("DYNATRACE_APPLICATION_NAME", "Dynatrace Lambda Monitor LIB");
        environmentVariables.set("DYNATRACE_APPLICATION_ID", "82564fc9-1617-4257-b26c-9a896056d42f");

        readPropertiesFromFileAndEnvironmentVariables();

        assertEquals(reader.isEnableMonitor(), true);
        assertEquals(reader.isEnableVerbose(), true);
        assertEquals(reader.getApplicationName(), "Dynatrace Lambda Monitor LIB");
        assertEquals(reader.getApplicationID(), "82564fc9-1617-4257-b26c-9a896056d42f");
        assertEquals(reader.getDeviceID(), new Long(42));
        assertEquals(reader.getEndpointURL(), "https://xzt878.dynatrace-managed.com:9999/mbeacon/75fcdbc5-1040-45f0-a7c9-322ea8f88765");
        assertEquals(reader.getClientIP(), "8.8.8.8");
        assertEquals(reader.getUser(), "wagner.oliveira@provider-it.com.br");
        assertEquals(reader.getApplicationVersion(), "1.0.0.0");
        assertEquals(reader.getOperatingSystem(), "Windows 10");
        assertEquals(reader.getManufacturer(), "MyCompany");
        assertEquals(reader.getModelID(), "MyModelID");

    }

    private void readPropertiesFromFile() {

        reader = new DynatracePropertiesReaderChain(
                new DynatracePropertiesFileReader(PROPERTIES_FILE_NAME)).getPropertiesReader();
    }

    private void readPropertiesFromFileAndEnvironmentVariables() {

        reader = new DynatracePropertiesReaderChain(
                new DynatracePropertiesFileReader(PROPERTIES_FILE_NAME),
                new DynatraceEnvVariablesReader()).getPropertiesReader();
    }

}
