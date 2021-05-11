package io.github.lucunji.bbhelper;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.RenderEventHandler;
import io.github.lucunji.bbhelper.config.Configs;
import io.github.lucunji.bbhelper.handler.KeybindHandler;
import io.github.lucunji.bbhelper.render.WorldLastRenderer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BedrockBreakHelper implements ModInitializer {
	private static Logger LOGGER = LogManager.getLogger();
	public static KeyBinding HOTKEY;

	@Override
	public void onInitialize() {
		LOGGER.info("Let's break bedrocks!");
		RenderEventHandler.getInstance().registerWorldLastRenderer(new WorldLastRenderer());
		ConfigManager.getInstance().registerConfigHandler("bbhelper", new Configs());
		KeyBindingRegistryImpl.registerKeyBinding(HOTKEY = new KeyBinding("key.bbhelper.main", InputUtil.Type.KEYSYM, 96, "key.categories.misc"));

		ClientTickEvents.END_CLIENT_TICK.register(new KeybindHandler());
	}
}
