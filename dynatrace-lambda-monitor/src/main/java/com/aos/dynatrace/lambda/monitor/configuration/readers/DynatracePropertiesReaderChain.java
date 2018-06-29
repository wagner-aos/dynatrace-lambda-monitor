package com.aos.dynatrace.lambda.monitor.configuration.readers;

/**
 * Class for reading all Impl of BaseReader 
 * 
 * @author Wagner Alves
 */
public class DynatracePropertiesReaderChain {

    private BaseReader reader;
    private PropertiesMapper mapper;

    public DynatracePropertiesReaderChain(BaseReader... reader) {
        this.reader = new BaseReader();
        mapper = new PropertiesMapper();

        for (BaseReader pr : reader) {
            this.reader = mapper.setValues(pr.readProperties());
        }
    }

    public BaseReader getPropertiesReader() {
        return this.reader;
    }

}
