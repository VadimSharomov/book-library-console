package main;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vadim Sharomov
 */
class ReadConfig {
    private final static Logger logger = getLogger(ReadConfig.class);

    Properties getProperties() {
        String configFileName = "application.properties";
        InputStream inputStreamProperties = getClass().getClassLoader().getResourceAsStream(configFileName);

        Properties properties = new Properties();
        try {
            properties.load(inputStreamProperties);
            logger.info("Config file was read '" + configFileName + "'");
            if(inputStreamProperties != null){
                inputStreamProperties.close();
            }
        } catch (IOException e) {
            logger.error("Error to load from file properties: '" + configFileName + "'", e.getMessage());
            System.exit(1);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Unable to load jdbc Driver", e.getMessage());
            System.exit(1);
        }

        return properties;
    }
}