package com.aos.dynatrace.lambda.monitor.tracer;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.aos.dynatrace.lambda.monitor.exception.DynatraceMonitorException;
import com.dynatrace.openkit.api.Action;
import com.dynatrace.openkit.api.WebRequestTracer;

/**
 * Class for WebRequest Module
 * 
 * @author Wagner Alves
 *
 */
public class WebRequestModule {
	
	 /**
     * Performs a web request and traces the execution time using a {@code WebRequestTracer}. The result is reported
     * as a child of the provided {@code Action}.
     *
     * If the payload is null or empty a GET request is performed. Otherwise, a POST request is performed
     *
     * @param action the parent action for the web request tracer
     * @param endpoint the entpoint
     * @param payload the payload
     * @throws Exception
     */
    public void executeAndTraceWebRequest(Action action, String endpoint, String payload) throws Exception {
    	if(action == null) {
			throw new DynatraceMonitorException("Action hasn't been created!");
		}
    	if(endpoint == null){
    		throw new DynatraceMonitorException("Endpoint cannot be null");
    	}
    	
        // prepare web request
        URL url = new URL(endpoint);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        // write output if available
        if (payload != null && !payload.isEmpty()) {
            urlConnection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(payload);
            wr.flush();
        }

        // get the tracer
        WebRequestTracer tracer = action.traceWebRequest(urlConnection);

        // start timing for web request
        tracer.start();

        // process the result and store the bytes received
        int bytesReceived = processRequestResult(urlConnection);

        // set bytesSent, bytesReceived and response code
        tracer.setBytesSent(payload != null ? payload.getBytes().length : 0)    // we assume default encoding here
              .setBytesReceived(bytesReceived)                                  // bytes processed
              .setResponseCode(urlConnection.getResponseCode());

        // stop the tracer
        tracer.stop();
    }

    /**
     * Helper method that reads the response and returns the number of bytes read
     *
     * @param urlConnection
     * @return returns the number of bytes read
     * @throws IOException
     */
    private static int processRequestResult(URLConnection urlConnection) throws IOException {
        byte[] buffer = new byte[4096];
        int numBytes;
        int totalBytes = 0;
        while ( (numBytes = urlConnection.getInputStream().read(buffer)) > 0 ) {
            totalBytes += numBytes;
            // we should do something meaningful with the read bytes ...
        }
        return totalBytes;
    }

}
