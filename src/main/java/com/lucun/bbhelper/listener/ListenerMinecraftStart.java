package com.lucun.bbhelper.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dimdev.rift.listener.MinecraftStartListener;
import org.dimdev.rift.listener.client.ClientTickable;

public class ListenerMinecraftStart implements MinecraftStartListener, ClientTickable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Minecraft mc = Minecraft.getInstance();

    private boolean warn = false;

    @Override
    public void onMinecraftStart() {
        LOGGER.info("Let's break bedrocks!");
        boolean hasMalilib = true;

        //Use reflection for weak dependencies, you don't REALLY need malilib!
        try {
            Class classRenderEventHandler = Class.forName("fi.dy.masa.malilib.event.RenderEventHandler");
            Class classIRenderer = Class.forName("fi.dy.masa.malilib.interfaces.IRenderer");
            Class classWorldLastRenderHandler = Class.forName("com.lucun.bbhelper.event.WorldLastRenderHandler");

            Object staticInstanceObject = classRenderEventHandler.getMethod("getInstance").invoke(null);
            classRenderEventHandler.getMethod("registerWorldLastRenderer", classIRenderer)
                    .invoke(staticInstanceObject, classWorldLastRenderHandler.newInstance());
        } catch (ReflectiveOperationException e) {
            LOGGER.error(e);
            for (StackTraceElement element : e.getStackTrace()) {
                LOGGER.error("\t" + element);
            }
            LOGGER.info("Install malilib to break even more bedrocks!");
            warn = true;
        }
    }

    @Override
    public void clientTick(Minecraft client) {
        if (warn) {
            warn = false;
            GuiNewChat guinewchat = this.mc.ingameGUI.getChatGUI();
            guinewchat.printChatMessage(new TextComponentString("[BedrockBreakHelper] Install malilib to break even more bedrocks!"));
        }
    }
}
