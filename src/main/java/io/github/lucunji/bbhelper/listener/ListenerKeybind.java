package io.github.lucunji.bbhelper.listener;

import com.google.common.collect.Lists;
import io.github.lucunji.bbhelper.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.TextComponentTranslation;
import org.dimdev.rift.listener.client.KeyBindingAdder;
import org.dimdev.rift.listener.client.KeybindHandler;

import java.util.Collection;

public class ListenerKeybind implements KeybindHandler, KeyBindingAdder {
    private KeyBinding toggleKeybind;
    private boolean lastToggleKeybindState;
    private static final Minecraft mc = Minecraft.getInstance();

    @Override
    public void processKeybinds() {
        if (this.toggleKeybind.isKeyDown() != this.lastToggleKeybindState) {
            if (this.toggleKeybind.isKeyDown()) {
                if (ListenerMinecraftStart.MALILIB_INSTALLED) {
                    if (InputMappings.isKeyDown(341)) { // check if control is pressed
                        toggleRenderDistance();
                    } else if (InputMappings.isKeyDown(342)) { // alt
                        toggleRenderMore();
                    } else {
                        this.toggleActive();
                    }
                } else {
                    this.toggleActive();
                }
            }
            this.lastToggleKeybindState = this.toggleKeybind.isKeyDown();
        }
    }

    private void toggleRenderDistance() {
        for (int i = 0; i < Configs.RENDER_DISTANCES.length; i++) {
            if (Configs.RENDER_DISTANCES[i] >= Configs.RENDER_DISTANCE.getIntegerValue()) {
                Configs.RENDER_DISTANCE.setIntegerValue(Configs.RENDER_DISTANCES[(i + 1) % Configs.RENDER_DISTANCES.length]);
                break;
            }
        }
        mc.player.sendStatusMessage(new TextComponentTranslation("bbhelper.message.render_distance", Configs.RENDER_DISTANCE.getIntegerValue()), true);
    }

    private void toggleActive() {
        Configs.ACTIVE.setBooleanValue(!Configs.ACTIVE.getBooleanValue());
        mc.player.sendStatusMessage(new TextComponentTranslation("bbhelper.message.toggle_" + (Configs.ACTIVE.getBooleanValue() ? "active" : "inactive")), true);
    }

    private void toggleRenderMore() {
        Configs.RENDER_MORE.setBooleanValue(!Configs.RENDER_MORE.getBooleanValue());
        mc.player.sendStatusMessage(new TextComponentTranslation("bbhelper.message.render_amount_" + (Configs.RENDER_MORE.getBooleanValue() ? "inc" : "dec")), true);
    }

    @Override
    public Collection<? extends KeyBinding> getKeyBindings() {
        // key grave '`'
        this.toggleKeybind = new KeyBinding("bbhelper.key.name", 96, "key.categories.misc");
        return Lists.newArrayList(this.toggleKeybind);
    }
}
