package io.github.lucunji.bbhelper.listener;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.RenderEventHandler;
import io.github.lucunji.bbhelper.config.Configs;
import io.github.lucunji.bbhelper.event.WorldLastRenderHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dimdev.rift.listener.MinecraftStartListener;
import org.dimdev.rift.listener.client.ClientTickable;

public class ListenerMinecraftStart implements MinecraftStartListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Minecraft mc = Minecraft.getInstance();

    @Override
    public void onMinecraftStart() {
        LOGGER.info("Let's break bedrocks!");
        RenderEventHandler.getInstance().registerWorldLastRenderer(new WorldLastRenderHandler());
        ConfigManager.getInstance().registerConfigHandler("bbhelper", new Configs());
    }
}
