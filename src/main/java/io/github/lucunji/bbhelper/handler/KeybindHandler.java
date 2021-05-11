package io.github.lucunji.bbhelper.handler;

import io.github.lucunji.bbhelper.BedrockBreakHelper;
import io.github.lucunji.bbhelper.config.Configs;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;

public class KeybindHandler implements ClientTickEvents.EndTick {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static boolean lastToggleKeybindState;
    
    @Override
    public void onEndTick(MinecraftClient minecraftClient) {
        if (BedrockBreakHelper.HOTKEY.isPressed() != lastToggleKeybindState) {
            if (BedrockBreakHelper.HOTKEY.isPressed()) {
                if (InputUtil.isKeyPressed(mc.window.getHandle(), 341)) { // check if control is pressed
                    toggleRenderDistance();
                } else if (InputUtil.isKeyPressed(mc.window.getHandle(), 342)) { // alt
                    toggleRenderMore();
                } else {
                    toggleActive();
                }
            }
            lastToggleKeybindState = BedrockBreakHelper.HOTKEY.isPressed();
        }
    }

    private static void toggleRenderDistance() {
        for (int i = 0; i < Configs.RENDER_DISTANCES.length; i++) {
            if (Configs.RENDER_DISTANCES[i] >= Configs.RENDER_DISTANCE.getIntegerValue()) {
                Configs.RENDER_DISTANCE.setIntegerValue(Configs.RENDER_DISTANCES[(i + 1) % Configs.RENDER_DISTANCES.length]);
                break;
            }
        }
        mc.player.addChatMessage(new TranslatableText("bbhelper.message.render_distance", Configs.RENDER_DISTANCE.getIntegerValue()), true);
    }

    private static void toggleActive() {
        Configs.ACTIVE.setBooleanValue(!Configs.ACTIVE.getBooleanValue());
        mc.player.addChatMessage(new TranslatableText("bbhelper.message.toggle_" + (Configs.ACTIVE.getBooleanValue() ? "active" : "inactive")), true);
    }

    private static void toggleRenderMore() {
        Configs.RENDER_MORE.setBooleanValue(!Configs.RENDER_MORE.getBooleanValue());
        mc.player.addChatMessage(new TranslatableText("bbhelper.message.render_amount_" + (Configs.RENDER_MORE.getBooleanValue() ? "inc" : "dec")), true);
    }
}
