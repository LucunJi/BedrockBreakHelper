package com.lucun.bbhelper.event;

import com.lucun.bbhelper.config.Configs;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.RenderEventHandler;

public class MalilibRegister {
    public static void register() {
        RenderEventHandler.getInstance().registerWorldLastRenderer(new WorldLastRenderHandler());
        ConfigManager.getInstance().registerConfigHandler("bbhelper", new Configs());
    }
}
