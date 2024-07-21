package auth.base.user.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import java.io.File;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigurationService extends PropertiesConfiguration {
    static final String PROPS_RESOURCE = "config.properties";
    static ConfigurationService instance;

    private ConfigurationService() {
        try {
            setFile(new File(PROPS_RESOURCE));
            setReloadingStrategy(new FileChangedReloadingStrategy());
            setAutoSave(true);
            load();
            log.info("Loaded engine properties from {}", PROPS_RESOURCE);
        } catch (ConfigurationException e) {
            log.error("Failed to load properties from {}", PROPS_RESOURCE, e);
        }
    }

    public static ConfigurationService getInstance() {
        if (instance == null) {
            instance = new ConfigurationService();
        }
        return instance;
    }

    @Override
    public void configurationChanged() {
        super.configurationChanged();
        log.info("Configuration changed in {}", PROPS_RESOURCE);
    }
}

