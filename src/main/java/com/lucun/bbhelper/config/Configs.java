package com.lucun.bbhelper.config;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Configs implements IConfigHandler {
    private static final String CONFIG_FILE_NAME = "bbhelper.json";
    private static final List<ConfigBase> CONFIGS;
    public static final ConfigBoolean ACTIVE;
    public static final ConfigInteger RENDER_DISTANCE;
    public static final ConfigBoolean RENDER_MORE;
    public static final int[] RENDER_DISTANCES = new int[]{4, 6, 8, 12, 16};
    static {
        CONFIGS = new ArrayList<>();

        CONFIGS.add(ACTIVE = new ConfigBoolean("bbhelper.config.active",
                true, "bbhelper.config.active"));
        CONFIGS.add(RENDER_DISTANCE = new ConfigInteger("bbhelper.config.render_distance",
                6, 4, 16, "bbhelper.config.render_distance"));
        CONFIGS.add(RENDER_MORE = new ConfigBoolean("bbhelper.config.render_more",
                true, "bbhelper.config.render_more"));


    }
    @Override
    public void load() {
        File fileConfig = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (fileConfig.exists() && fileConfig.isFile() && fileConfig.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(fileConfig);

            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readConfigBase(root, "Generic", CONFIGS);
            }
        }
    }

    @Override
    public void save() {
        File dirConfig = FileUtils.getConfigDirectory();

        if ((dirConfig.exists() && dirConfig.isDirectory()) || dirConfig.mkdirs()) {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "Generic", CONFIGS);

            JsonUtils.writeJsonToFile(root, new File(dirConfig, CONFIG_FILE_NAME));
        }
    }
}
