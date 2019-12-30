package com.lucun.bbhelper.listener;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.TextComponentString;
import org.dimdev.rift.listener.client.KeyBindingAdder;
import org.dimdev.rift.listener.client.KeybindHandler;

import java.util.Collection;

public class ListenerKeybind implements KeybindHandler, KeyBindingAdder {
    private KeyBinding toggleKeybind;
    private boolean lastToggleKeybindState;
    private static boolean active = false;
    private static final int[] RENDER_DISTANCES = new int[]{4, 6, 8, 12, 16};
    private static int renderDistanceIndex = 2;
    private static final Minecraft mc = Minecraft.getInstance();
    private static boolean renderMore = false;

    @Override
    public void processKeybinds() {
        if (this.toggleKeybind.isKeyDown() != this.lastToggleKeybindState) {
            if (this.toggleKeybind.isKeyDown()) {
                if (InputMappings.isKeyDown(341)) { // check if control is pressed
                    renderDistanceIndex = ++renderDistanceIndex % RENDER_DISTANCES.length;
                    mc.player.sendStatusMessage(new TextComponentString(
                            String.format("Render distance is changed to %s", getRenderDistance())), true);
                } else if (InputMappings.isKeyDown(342)) { // alt
                    renderMore = !renderMore;
                    mc.player.sendStatusMessage(new TextComponentString(
                            String.format("Render amount: %s", renderMore ? "increased" : "decreased")), true);
                } else {
                    active = !active;
                    mc.player.sendStatusMessage(new TextComponentString(
                            String.format("Bedrock Break Helper is now %s", active ? "active" : "inactive")), true);
                }
            }
            this.lastToggleKeybindState = this.toggleKeybind.isKeyDown();
        }
    }

    @Override
    public Collection<? extends KeyBinding> getKeyBindings() {
        // key grave '`'
        this.toggleKeybind = new KeyBinding("Bedrock Break Helper", 96, "key.categories.misc");
        return Lists.newArrayList(this.toggleKeybind);
    }

    public static boolean isActive() {
        return active;
    }

    public static int getRenderDistance() {
        return RENDER_DISTANCES[renderDistanceIndex];
    }

    public static boolean isRenderMore() {
        return renderMore;
    }
}
