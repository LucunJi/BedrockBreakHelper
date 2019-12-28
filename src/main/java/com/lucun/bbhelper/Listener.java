package com.lucun.bbhelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.dimdev.rift.listener.MinecraftStartListener;

//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;

import java.util.Random;

//@Mixin(BlockModelRenderer.class)
public class Listener implements MinecraftStartListener {
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onMinecraftStart() {
		//Minecraft has started but hasn't registered any blocks or items
		//Prime time for loading a config if you need one
		LOGGER.info("Let's break bedrocks!");
	}

//	@Inject(method = "renderModel", at = @At("HEAD"))
	private void onRenderBlock(IWorldReader worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, Random randomIn, long rand) {
		LOGGER.info("Rendering...");
	}
}
