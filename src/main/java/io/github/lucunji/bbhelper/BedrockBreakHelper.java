package io.github.lucunji.bbhelper;

import fi.dy.masa.malilib.config.ConfigManager;
import io.github.lucunji.bbhelper.config.Configs;
import io.github.lucunji.bbhelper.handler.KeybindHandler;
import io.github.lucunji.bbhelper.render.WorldLastRenderer;
import fi.dy.masa.malilib.event.RenderEventHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BedrockBreakHelper implements ModInitializer {
	private static Logger LOGGER = LogManager.getLogger();
	public static FabricKeyBinding HOTKEY;

	@Override
	public void onInitialize() {
		LOGGER.info("Let's break bedrocks!");
		RenderEventHandler.getInstance().registerWorldLastRenderer(new WorldLastRenderer());
		ConfigManager.getInstance().registerConfigHandler("bbhelper", new Configs());
		KeyBindingRegistryImpl.INSTANCE.register(HOTKEY = FabricKeyBinding.Builder.create(Identifier.splitOn("bbhelper.main", '.'), InputUtil.Type.KEYSYM, 96, "key.categories.misc").build());

		ClientTickCallback.EVENT.register(new KeybindHandler());
	}
}
