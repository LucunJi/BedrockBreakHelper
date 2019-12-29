package com.lucun.bbhelper.listener;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.dimdev.rift.listener.client.KeyBindingAdder;
import org.dimdev.rift.listener.client.KeybindHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListenerKeybind implements KeybindHandler, KeyBindingAdder {
    private KeyBinding toggleKeybind;
    private boolean lastToggleKeybindState;
    private static boolean active = false;

    @Override
    public void processKeybinds() {
        if (this.toggleKeybind.isKeyDown() != this.lastToggleKeybindState) {
            if (this.toggleKeybind.isKeyDown()) {
                this.active = !this.active;
            }
            this.lastToggleKeybindState = this.toggleKeybind.isKeyDown();
        }
    }

    @Override
    public Collection<? extends KeyBinding> getKeyBindings() {
        //grave key '`'
        this.toggleKeybind = new KeyBinding("Bedrock Break Helper", 96, "key.categories.misc");
        return Lists.newArrayList(this.toggleKeybind);
    }

    public static boolean isActive() {
        return active;
    }
}
