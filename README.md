# Bedrock Break Helper

[中文](./README_zh-CN.md)

![img](./imgs/cover@0.5x.png)

* Helps you breaking bedrocks using Myren Eario's [method](https://youtu.be/BL98BDMwyWM).
* **REQUIRES** [malilib](https://www.curseforge.com/minecraft/mc-mods/malilib) and its dependency [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api).
    
    
* Shows valid positions to place redstone signal sources.
* Suggestion box turns red/blue when occupied by a non-singal-source/signal-source block.
* Avoid use flicking boxes, they are unstable.


# How to use
1. Place a piston with its back to a bedrock, and a TNT nearby.
2. Put a signal source block in a green box to extend the piston.
3. Ignite the TNT, keep trying to place an opposite piston with an auto-clicker(recommend tweakeroo).
* Use **grave(\`) key**(default) to toggle on/off;
    - **Ctrl + \`** to change rendering distance;
    - **Alt + \`** to toggle if positions two-blocks far from pistons will also be rendered.

# Thanks
Use the code from Fallen-Breath's [calculator](https://github.com/Fallen-Breath/IronHeadHelper) to calculate possible positions.

[Malilib](https://github.com/maruohon/malilib/tree/rift_1.13.2/), I had a hard time setting up Mixins, and using Malilib is much easier.
