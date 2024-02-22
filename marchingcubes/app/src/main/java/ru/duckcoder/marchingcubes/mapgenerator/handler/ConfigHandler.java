package ru.duckcoder.marchingcubes.mapgenerator.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.system.AppSettings;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.marchingcubes.mapgenerator.config.Config;
import ru.duckcoder.marchingcubes.mapgenerator.util.IOHelper;

import java.io.File;
import java.nio.file.Path;

@Log4j2
public class ConfigHandler {
    private static volatile ConfigHandler instance;

    public static ConfigHandler getInstance() {
        ConfigHandler localInstance = instance;
        if (localInstance == null) {
            synchronized (ConfigHandler.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new ConfigHandler();
            }
        }
        return localInstance;
    }

    private final String configFileName = "app.config.json";
    private final String rootPath;
    private AppSettings appSettings;

    private ConfigHandler() {
        rootPath = Path.of(new File("").getAbsolutePath()).normalize().toString();

        Config config;
        String configJsonString = IOHelper.readFile(IOHelper.getFilePath(rootPath, configFileName), null);
        if (configJsonString == null) {
            config = Config.DEFAULT;
        } else {
            try {
                ObjectMapper om = new ObjectMapper();
                config = om.readValue(configJsonString, Config.class);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
                config = Config.DEFAULT;
            }
        }
        System.out.println(config.toString());
    }
}
